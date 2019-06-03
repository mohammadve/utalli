package com.utalli.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utalli.R
import com.utalli.fragment.*


class HomeActivity : AppCompatActivity(){

    lateinit var mBottomNavigationView : BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()

    }

    private fun initViews() {
        loadNearMeFragment()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        mBottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        mBottomNavigationView.setOnNavigationItemSelectedListener(object:BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem):Boolean {
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



}