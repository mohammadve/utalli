        package com.utalli.activity

        import android.Manifest
        import android.annotation.SuppressLint
        import android.app.Activity
        import android.content.Context
        import android.content.Intent
        import android.content.pm.PackageManager
        import android.location.Location
        import android.os.Bundle
        import android.view.MenuItem
        import androidx.annotation.NonNull
        import androidx.appcompat.app.AppCompatActivity
        import com.google.android.gms.common.api.GoogleApiClient
        import com.google.android.material.bottomnavigation.BottomNavigationView
        import com.utalli.R
        import com.utalli.fragment.*
        import android.os.Build
        import androidx.core.app.ActivityCompat
        import com.google.android.gms.common.ConnectionResult
        import com.utalli.helpers.Utils
        import android.content.DialogInterface
        import android.content.IntentSender
        import android.location.Geocoder
        import android.location.LocationManager
        import android.os.Looper
        import android.provider.Settings
        import androidx.appcompat.app.AlertDialog
        import androidx.core.content.PermissionChecker
        import androidx.fragment.app.Fragment
        import androidx.fragment.app.FragmentManager
        import androidx.fragment.app.FragmentTransaction
        import androidx.lifecycle.ViewModelProviders
        import androidx.localbroadcastmanager.content.LocalBroadcastManager
        import com.google.android.gms.common.GoogleApiAvailability
        import com.google.android.gms.common.api.ApiException
        import com.google.android.gms.common.api.ResolvableApiException
        import com.google.android.gms.common.api.ResultCallback
        import com.google.android.gms.location.*
        import com.google.android.gms.tasks.*
        import com.google.firebase.FirebaseApp
        import com.google.firebase.iid.FirebaseInstanceId
        import com.utalli.helpers.AppPreference
        import com.utalli.models.UserModel
        import com.utalli.viewModels.HomeViewModel
        import java.util.*


        class HomeActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {

            /* integer for permissions results request*/
            private val REQUEST_CHECK_SETTINGS = 214
            private val REQUEST_ENABLE_GPS = 516
            private val REQUEST_LOCATION_PERMISSION = 214
            private val REQUEST_GOOGLE_PLAY_SERVICE = 988
            /* integer for permissions results request*/

            /* For Google Fused API */
            protected lateinit var mGoogleApiClient: GoogleApiClient
            protected lateinit var mLocationSettingsRequest: LocationSettingsRequest
            private var mFusedLocationClient: FusedLocationProviderClient? = null
            private var mSettingsClient: SettingsClient? = null
            private var mLocationCallback: LocationCallback? = null
            private var mLocationRequest: LocationRequest? = null
            private var mCurrentLocation: Location? = null
            private var UPDATE_INTERVAL: Long = 5000
            val FASTEST_INTERVAL: Long = 5000 // = 5 seconds
            /* For Google Fused API */

            // lists for permissions
            private var permissionsToRequest: ArrayList<String>? = null
            private var permissionsRejected = ArrayList<String>()
            private var permissions = ArrayList<String>()

            private var mContext: Context? = null
            private var mManager: FragmentManager? = null
            private var mTransaction: FragmentTransaction? = null
            var homeViewModel: HomeViewModel? = null
            var isUserLocationEmpty = true
            lateinit var mBottomNavigationView: BottomNavigationView


            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_home)
                initViews()
            }

            private fun initViews() {

                mContext = this@HomeActivity
                homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

                Utils.showProgressDialog(this)
                FirebaseApp.initializeApp(applicationContext)
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Utils.showLog(task.exception!!.message!!)
                            return@OnCompleteListener
                        }

                        // Get new Instance ID token
                        val token = task.result?.token
                        if (token != null) {
                            Utils.showLog("Device token :" + token)
                            if (Utils.isInternetAvailable(this))
                                sendTokenToServer(token)
                        }
                    })

                try {
                    val permissionCheck =
                        PermissionChecker.checkCallingOrSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient()
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            REQUEST_LOCATION_PERMISSION
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                setupBottomNavigation()
            }


            @Synchronized
            protected fun buildGoogleApiClient() {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                mSettingsClient = LocationServices.getSettingsClient(this)

                mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()

                connectGoogleClient()

                mLocationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        mCurrentLocation = locationResult!!.lastLocation
                        onLocationChanged(mCurrentLocation)
                    }
                }
            }

            private fun connectGoogleClient() {
                val googleAPI = GoogleApiAvailability.getInstance()
                val resultCode = googleAPI.isGooglePlayServicesAvailable(this)
                if (resultCode == ConnectionResult.SUCCESS) {
                    mGoogleApiClient.connect()
                } else {
                    Utils.hideProgressDialog()
                    googleAPI.getErrorDialog(this, resultCode, REQUEST_GOOGLE_PLAY_SERVICE)
                }
            }

            override fun onConnected(p0: Bundle?) {

                mLocationRequest = LocationRequest()
                mLocationRequest!!.setInterval(UPDATE_INTERVAL)
                mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)
                mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

                val builder = LocationSettingsRequest.Builder()
                builder.addLocationRequest(mLocationRequest!!)
                builder.setAlwaysShow(true)
                mLocationSettingsRequest = builder.build()

                mSettingsClient!!
                    .checkLocationSettings(mLocationSettingsRequest)
                    .addOnSuccessListener { requestLocationUpdate() }.addOnFailureListener { e ->
                        val statusCode = (e as ApiException).statusCode
                        when (statusCode) {
                            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(this@HomeActivity, REQUEST_CHECK_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                            }
                        }
                    }.addOnCanceledListener { }
            }

            override fun onConnectionSuspended(p0: Int) {
                connectGoogleClient()
            }

            override fun onConnectionFailed(p0: ConnectionResult) {
                buildGoogleApiClient()
            }

            override fun onLocationChanged(mLocation: Location?) {
                val latitude = mLocation!!.getLatitude().toString()
                val longitude = mLocation!!.getLongitude().toString()

                if (latitude.equals("0.0", ignoreCase = true) && longitude.equals("0.0", ignoreCase = true)) {
                    requestLocationUpdate()
                } else {
                    locationUpdate(mLocation);
                }
            }

            override fun onResult(p0: LocationSettingsResult) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            @SuppressLint("MissingPermission")
            private fun requestLocationUpdate() {
                mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
            }

            private fun locationUpdate(mLocation: Location?)
            {
                Utils.showLog("Google API client onLocationchange called")
                if (mLocation != null)
                {
                    Utils.hideProgressDialog()

                    var geocoder = Geocoder(this, Locale.getDefault());

                    var addresses = geocoder.getFromLocation(
                        mLocation!!.latitude,
                        mLocation!!.longitude,
                        1
                    )

                    var countryCode = addresses.get(0).countryCode
                    var state = addresses.get(0).getAdminArea()
                    var country = addresses.get(0).getCountryName()


                    AppPreference.getInstance(this).setUserLastLocation("" + state + ", " + country)
                    AppPreference.getInstance(this).setCountryCode(countryCode)
                    AppPreference.getInstance(this).setCountryName(country)

                    if (isUserLocationEmpty)
                    {
                        setReceivedLocation(mLocation)
                        var fragment = NearMeFragment()
                        replaceFragment(fragment)
                        isUserLocationEmpty = false
                    }

                    val intent = Intent()
                    intent.putExtra("location", mLocation)
                    intent.action = "LOCATION_UPDATED"

                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    Utils.showLog("Location Latitude : " + mLocation!!.latitude + " Longitude : " + mLocation!!.longitude)
                }
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)

                when(requestCode)
                {
                    REQUEST_CHECK_SETTINGS ->
                    {
                        when (resultCode) {
                            Activity.RESULT_OK -> requestLocationUpdate()
                            Activity.RESULT_CANCELED -> openGpsEnableSetting()
                        }
                    }
                    REQUEST_ENABLE_GPS ->
                    {
                        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                        if (!isGpsEnabled) {
                            openGpsEnableSetting()
                        } else {
                            requestLocationUpdate()
                        }
                    }
                }
            }

            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

                if (requestCode == REQUEST_LOCATION_PERMISSION) {
                    val permissionCheck =
                        PermissionChecker.checkCallingOrSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        Utils.showProgressDialog(this)
                        buildGoogleApiClient()
                    } else {
                        AlertDialog.Builder(this@HomeActivity)
                                        .setMessage("These permissions are mandatory to get your location. You need to allow them.")
                                        .setPositiveButton("OK",
                                            DialogInterface.OnClickListener { dialogInterface, i ->
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(
                                                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                                        REQUEST_LOCATION_PERMISSION
                                                        )
                                                }
                                            }).setNegativeButton("Cancel", null).create().show()
                    }
                }
            }

            /**
             * The Purpose of this method is use to Open GPS
             */
            private fun openGpsEnableSetting() {
                Utils.hideProgressDialog()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, REQUEST_ENABLE_GPS)
            }

            override fun onDestroy() {
                //Remove location update callback here
                mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
                super.onDestroy()
            }
            private fun permissionsToRequest(requiredPermission: ArrayList<String>): ArrayList<String> {

                var result = ArrayList<String>()

                for (permission: String in requiredPermission) {
                    if (!hasPermission(permission))
                        result.add(permission)
                }
                return result
            }

            private fun hasPermission(permission: String): Boolean {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
                }
                return true
            }


            /**
             * The purpose of this method is use to setup Bottom Navigation View
             */
            private fun setupBottomNavigation() {

                mBottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
                mBottomNavigationView.setOnNavigationItemSelectedListener(object :
                    BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                        when (item.getItemId()) {
                            R.id.action_near_me -> {
                                var fragment = NearMeFragment()
                                replaceFragment(fragment)
                                return true
                            }
                            R.id.action_my_tour -> {
                                var fragment = MyToursFragment()
                                replaceFragment(fragment)
                                return true
                            }
                            R.id.action_message -> {
                                var fragment = ChatMessageFragment()
                                replaceFragment(fragment)
                                return true
                            }
                        }
                        return false
                    }
                })
            }

            /**
             * The pupose of this method is use to Show fragment
             */
//            fun setDisplayFragment(id: Int) {
//                var mFragment: Fragment? = null
//                when (id) {
//                    AppConstants.NEAR_ME ->
//                    {
//                        mFragment = NearMeFragment()
//                        replaceFragment(mFragment, FragmentNameConstants.NEAR_ME_FRAGMENT)
//                    }
//                    AppConstants.MY_TOURS ->
//                    {
//                        mFragment = MyToursFragment()
//                        replaceFragment(mFragment, FragmentNameConstants.MY_TOURS_FRAGMENT)
//                    }
//                    AppConstants.MESSAGE ->
//                    {
//                        mFragment = ChatMessageFragment()
//                        replaceFragment(mFragment, FragmentNameConstants.MY_TOURS_FRAGMENT)
//                    }
//                    AppConstants.MY_PROFILE ->
//                    {
//
//                    }
//                    AppConstants.PAYMENT ->
//                    {
//
//                    }
//                    AppConstants.NOTIFICATION ->
//                    {
//
//                    }
//                }
//            }

            /**
             * The purpose of this method is use to Replace fragment
             */
//            private fun replaceFragment(fragment: Fragment, fragmentName: String) {
//                mManager = supportFragmentManager
//                mTransaction = mManager!!.beginTransaction()
//                mTransaction!!.replace(R.id.frame_container, fragment, fragmentName)
//                mTransaction!!.addToBackStack(null)
//                mTransaction!!.commit()
//            }

            /**
             * The Purpose of this method is use to Add Fragment
             */
//            private fun addFragment(fragment: Fragment, fragmentName: String) {
//                mManager = supportFragmentManager
//                mTransaction = mManager!!.beginTransaction()
//                mTransaction!!.add(R.id.frame_container, fragment, fragmentName)
//                mTransaction!!.addToBackStack(fragmentName)
//                mTransaction!!.commit()
//            }

            /**
             * The Purpose of this method is use to Handle Back button press
             */
//            private fun handleBackPress() {
//                if (mManager!!.findFragmentById(R.id.frame_container) is NearMeFragment) run {
//                    showExitConfirmDialog();
//                }
//                else if (mManager!!.findFragmentById(R.id.frame_container) is MyToursFragment) run {
//                    mManager!!.popBackStackImmediate()
//                }
//                else if (mManager!!.findFragmentById(R.id.frame_container) is ChatMessageFragment) run {
//                    mManager!!.popBackStackImmediate()
//                }
//                else run {
//                    mManager!!.popBackStackImmediate()
//                }
//            }

            /**
             * The Purpose of this method is use to Handle Back button press
             */
//            override fun onBackPressed() {
//                handleBackPress()
//            }

            /**
             * The Purpose of this method is use to Show Confirm exit dialog
             */
//            private fun showExitConfirmDialog()
//            {
//                AlertDialog.Builder(this@HomeActivity)
//                    .setMessage("Are you sure want to exit from app!")
//                    .setPositiveButton("OK",
//                        DialogInterface.OnClickListener { dialogInterface, i ->
//                            val intent = Intent(Intent.ACTION_MAIN)
//                            intent.addCategory(Intent.CATEGORY_HOME)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP//***Change Here***
//                            startActivity(intent)
//                            finish()
//                        }).setNegativeButton("Cancel", null).create().show()
//            }

//            private fun loadNearMeFragment() {
//                val nearMeFragment = NearMeFragment()
//
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.frame_container, nearMeFragment)
//                transaction.commit()
//            }


//            private fun loadMyTourFragment() {
//                val myToursfragment = MyToursFragment()
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.frame_container, myToursfragment)
//                transaction.commit()
//            }
//
//            private fun loadMessageFragment() {
//                val messagefragment = ChatMessageFragment()
//                val transaction = supportFragmentManager.beginTransaction()
//                transaction.replace(R.id.frame_container, messagefragment)
//                transaction.commit()
//            }

            private fun replaceFragment(meFragment: Fragment)
            {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_container, meFragment)
                transaction.commitAllowingStateLoss();
            }

            /**
             * The purpose of this method is use to Update FCM Token to server
             */
            private fun sendTokenToServer(token: String) {

                var user = AppPreference.getInstance(this).getUserData() as UserModel
                homeViewModel!!.updateDeviceToken(AppPreference.getInstance(this).getAuthToken(), user.id.toString(), token)
                    .observe(this, androidx.lifecycle.Observer {

                        Utils.showLog(it.toString())
                    })
            }

            fun setReceivedLocation(location: Location)
            {
                mCurrentLocation = location
            }

            fun receivedLocation(): Location?
            {
                return mCurrentLocation;
            }
        }