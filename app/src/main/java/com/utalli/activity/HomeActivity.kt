package com.utalli.activity

import android.Manifest
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
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import android.os.Build
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.LocationListener
import com.utalli.helpers.Utils
import java.security.Permission
import android.widget.Toast
import android.content.DialogInterface
import android.location.Geocoder
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.utalli.helpers.AppPreference
import com.utalli.models.UserModel
import com.utalli.viewModels.HomeViewModel
import java.util.*


class HomeActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {

    override fun onConnected(p0: Bundle?) {

        Utils.showLog("Google API client connected")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }


        Utils.showProgressDialog(this)


        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {


            var geocoder = Geocoder(this, Locale.getDefault());

            var addresses = geocoder.getFromLocation(
                location!!.latitude,
                location!!.longitude,
                1
            )

            var state = addresses.get(0).getAdminArea()
            var country = addresses.get(0).getCountryName()


            AppPreference.getInstance(this).setUserLastLocation("" + state + ", " + country)

            if(isUserLocationEmpty)
            {
                loadNearMeFragment()
                isUserLocationEmpty=false
            }


            val intent = Intent()
            intent.putExtra("location", location)
            intent.action = "LOCATION_UPDATED"
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

            Utils.showLog("Location Latitude : " + location!!.latitude + " Longitude : " + location!!.longitude)
        }
        startLocationUpdates();
    }

    override fun onConnectionSuspended(p0: Int) {

        Utils.showLog("Google API client suspended")

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

        Utils.showLog("Google API client failed")
    }

    override fun onLocationChanged(mLocation: Location?) {
        Utils.showLog("Google API client onLocationchange called")
        if (location != null) {

            Utils.hideProgressDialog()


            var geocoder = Geocoder(this, Locale.getDefault());

            var addresses = geocoder.getFromLocation(
                location!!.latitude,
                location!!.longitude,
                1
            )

            var state = addresses.get(0).getAdminArea()
            var country = addresses.get(0).getCountryName()


            AppPreference.getInstance(this).setUserLastLocation("" + state + ", " + country)

            if (isUserLocationEmpty)
                loadNearMeFragment()

            isUserLocationEmpty = false


            val intent = Intent()
            intent.putExtra("location", location)
            intent.action = "LOCATION_UPDATED"
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

            Utils.showLog("Location Latitude : " + location!!.latitude + " Longitude : " + location!!.longitude)
        }

    }

    lateinit var mBottomNavigationView: BottomNavigationView
    var location: Location? = null
    var googleApiClient: GoogleApiClient? = null
    private var PLAY_SERVICES_RESOLUTION_REQUEST = 9000
    private var locationRequest: LocationRequest? = null
    private var UPDATE_INTERVAL: Long = 5000
    val FASTEST_INTERVAL: Long = 5000 // = 5 seconds
    // lists for permissions
    private var permissionsToRequest: ArrayList<String>? = null
    private var permissionsRejected = ArrayList<String>()
    private var permissions = ArrayList<String>()
    // integer for permissions results request
    private var ALL_PERMISSIONS_RESULT = 1011
    var homeViewModel: HomeViewModel? = null
    var isUserLocationEmpty = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()

    }

    private fun initViews() {


        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

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





        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = permissionsToRequest(permissions)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest!!.size > 0) {
                requestPermissions(
                    permissionsToRequest!!.toArray(
                        arrayOfNulls(permissionsToRequest!!.size)
                    ), ALL_PERMISSIONS_RESULT
                )
            }
        }

        // we build google api client
        googleApiClient = GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this).build()



        if (!AppPreference.getInstance(this).getUserLastLocation().equals(""))
            isUserLocationEmpty = false


        if (!isUserLocationEmpty)
            loadNearMeFragment()


        setupBottomNavigation()
    }


    override fun onStart() {

        if (googleApiClient != null) {
            googleApiClient!!.connect();
        }
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
        if (googleApiClient != null && googleApiClient!!.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

            googleApiClient!!.disconnect();
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS_RESULT -> {
                for (permission in permissionsToRequest!!) {
                    if (!hasPermission(permission)) {
                        permissionsRejected.add(permission)
                    }

                }

                if (permissionsRejected.size > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            AlertDialog.Builder(this@HomeActivity)
                                .setMessage("These permissions are mandatory to get your location. You need to allow them.")
                                .setPositiveButton("OK",
                                    DialogInterface.OnClickListener { dialogInterface, i ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(
                                                permissionsRejected.toArray(
                                                    arrayOfNulls(
                                                        permissionsRejected.size
                                                    )
                                                ), ALL_PERMISSIONS_RESULT
                                            )
                                        }
                                    }).setNegativeButton("Cancel", null).create().show()

                            return

                        }
                    }


                } else {
                    if (googleApiClient != null) {

                        googleApiClient!!.connect()
                    }
                }


            }
        }


    }

    private fun startLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest!!.setInterval(UPDATE_INTERVAL)
        locationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show()
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)


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


    private fun setupBottomNavigation() {

        mBottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        mBottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.action_near_me -> {
                        loadNearMeFragment()
                        return true
                    }
                    R.id.action_my_tour -> {
                        loadMyTourFragment()
                        return true
                    }
                    R.id.action_message -> {
                        loadMessageFragment()
                        return true
                    }
                }
                return false
            }
        })


    }


    private fun loadNearMeFragment() {

        val nearMefragment = NearMeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, nearMefragment)
        transaction.commit()

        /*NearMeFragment fragment = NearMeFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        ft.commit();*/

        /*   val demonearFragment = DemoNearMe()
           val transaction123 = supportFragmentManager.beginTransaction()
           transaction123.replace(R.id.frame_container, demonearFragment)
           transaction123.commit()*/


    }


    private fun loadMyTourFragment() {
        val myToursfragment = MyToursFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, myToursfragment)
        transaction.commit()
    }


    private fun loadMessageFragment() {
        val messagefragment = ChatMessageFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, messagefragment)
        transaction.commit()
    }

    private fun sendTokenToServer(token: String) {

        var user = AppPreference.getInstance(this).getUserData() as UserModel
        homeViewModel!!.updateDeviceToken(AppPreference.getInstance(this).getAuthToken(), user.id.toString(), token)
            .observe(this, androidx.lifecycle.Observer {

                Utils.showLog(it.toString())

            })

    }

}