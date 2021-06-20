package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var selectedItem=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectedItem=R.id.stationFragment
        navController= Navigation.findNavController(this,R.id.fragment)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.stationFragment->{
                    navController.navigate(R.id.stationFragment)
                    selectedItem=R.id.stationFragment
                }
                R.id.favoritesFragment->{
                    navController.navigate(R.id.favoritesFragment)
                    selectedItem=R.id.favoritesFragment
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

    }
}