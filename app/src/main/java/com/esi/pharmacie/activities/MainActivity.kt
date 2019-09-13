package com.esi.pharmacie.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.esi.pharmacie.R
import com.esi.pharmacie.fragments.CommandFragment
import com.esi.pharmacie.fragments.MapFragment
import com.esi.pharmacie.fragments.PharmaciesFragment
import com.esi.pharmacie.fragments.ProfileFragment
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private val mapFragment = MapFragment.newInstance()
    private val pharmaciesFragment = PharmaciesFragment.newInstance()
    private val commandFragment = CommandFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    private var active : Fragment = mapFragment


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction().hide(active).show(mapFragment).commit()
                active = mapFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                supportFragmentManager.beginTransaction().hide(active).show(pharmaciesFragment).commit()
                active = pharmaciesFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                supportFragmentManager.beginTransaction().hide(active).show(commandFragment).commit()
                active = commandFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                supportFragmentManager.beginTransaction().hide(active).show(profileFragment).commit()
                active = profileFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().add(R.id.fragment,mapFragment,"Map").commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment,pharmaciesFragment,"Pharmacie").hide(pharmaciesFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment,commandFragment,"Command").hide(commandFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment,profileFragment,"Profile").hide(profileFragment).commit()
    }
}
