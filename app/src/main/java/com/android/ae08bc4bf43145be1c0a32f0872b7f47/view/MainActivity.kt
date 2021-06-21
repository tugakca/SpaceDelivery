package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.SharedPrefUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var selectedItem = 0
    private lateinit var sharedPref: SharedPrefUtil
    private var navOptions = NavOptions.Builder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = SharedPrefUtil(this.applicationContext)
        selectedItem = R.id.stationFragment
        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigation.setItemIconTintList(null);

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> bottomNavigation.visibility = View.GONE
                R.id.stationFragment -> bottomNavigation.visibility = View.VISIBLE
                R.id.favoritesFragment -> bottomNavigation.visibility = View.VISIBLE

            }
        }

        if(sharedPref.isFirstRun(this)){

            navController.navigate(R.id.detailFragment)
            selectedItem=R.id.detailFragment

        }else{
            navController.navigate(R.id.stationFragment)
            selectedItem=R.id.stationFragment
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.detailFragment -> {
                    navController.navigate(R.id.detailFragment)
                    selectedItem = R.id.detailFragment
                }
                R.id.stationFragment -> {
                    navController.navigate(R.id.stationFragment)
                    selectedItem = R.id.stationFragment
                }
                R.id.favoritesFragment -> {
                    navController.navigate(R.id.favoritesFragment)
                    selectedItem = R.id.favoritesFragment
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    override fun onBackPressed() {

    }
}