package com.carmarket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.carmarket.R
import com.carmarket.databinding.ActivityMainBinding
import com.carmarket.ui.allAds.AllAdsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding?.toolbar?.toolbarLayout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding?.setUpDrawerLayout()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.allAdsFragment) {
                binding?.toolbar?.sortSpinner?.visibility = View.VISIBLE
            } else {
                binding?.toolbar?.sortSpinner?.visibility = View.GONE
            }
        }

    }

    private fun ActivityMainBinding.setUpDrawerLayout() {
        toolbar.menuButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        navigationView.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.nav_all_ads -> {
                    navController.navigate(R.id.allAdsFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

//                R.id.nav_air_pollution -> {
//                    navController.navigate(R.id.airPollutionFragment)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                    true
//                }
//
//                R.id.nav_five_days -> {
//                    navController.navigate(R.id.fiveDaysForecastFragment)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                    true
//                }
//
//                R.id.nav_about_creator -> {
//                    navController.navigate(R.id.aboutCreatorFragment)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                    true
//                }

                else -> false
            }
        }
    }
}