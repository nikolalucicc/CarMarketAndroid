package com.carmarket.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.carmarket.R
import com.carmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar.toolbarLayout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.setUpDrawerLayout()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.allAdsFragment) {
                binding.toolbar.sortSpinner.visibility = View.VISIBLE
            } else {
                binding.toolbar.sortSpinner.visibility = View.GONE
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.adDetailsFragment -> {
                    val previousBackStackEntry = navController.previousBackStackEntry
                    val previousFragmentId = previousBackStackEntry?.destination?.id
                    when (previousFragmentId) {
                        R.id.allAdsFragment -> {
                            binding.toolbar.followAdButton.visibility = View.VISIBLE
                            binding.toolbar.changeAdButton.visibility = View.GONE
                            binding.toolbar.removeAdButton.visibility = View.GONE
                        }
                        R.id.adsByUserFragment -> {
                            binding.toolbar.followAdButton.visibility = View.GONE
                            binding.toolbar.changeAdButton.visibility = View.VISIBLE
                            binding.toolbar.removeAdButton.visibility = View.VISIBLE
                        }
                        R.id.searchResultFragment -> {
                            binding.toolbar.followAdButton.visibility = View.VISIBLE
                            binding.toolbar.changeAdButton.visibility = View.GONE
                            binding.toolbar.removeAdButton.visibility = View.GONE
                        }
                        else -> {
                            binding.toolbar.followAdButton.visibility = View.GONE
                            binding.toolbar.changeAdButton.visibility = View.GONE
                            binding.toolbar.removeAdButton.visibility = View.GONE
                        }
                    }
                }
                else -> {
                    binding.toolbar.followAdButton.visibility = View.GONE
                    binding.toolbar.changeAdButton.visibility = View.GONE
                    binding.toolbar.removeAdButton.visibility = View.GONE
                }
            }
        }
    }

    private fun ActivityMainBinding.setUpDrawerLayout() {
        toolbar.menuButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        val accessToken = getAccessToken()
        val isUserLoggedIn = accessToken.isNotEmpty()

        navigationView.menu.apply {
            findItem(R.id.nav_login).isVisible = !isUserLoggedIn
            findItem(R.id.nav_registration).isVisible = !isUserLoggedIn
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_all_ads -> {
                    navController.navigate(R.id.allAdsFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_registration -> {
                    navController.navigate(R.id.registrationFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_login -> {
                    navController.navigate(R.id.loginFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_create_ad -> {
                    val accessToken = getAccessToken()
                    val bundle = Bundle().apply {
                        putString("accessToken", accessToken)
                    }
                    navController.navigate(R.id.createAdFragment, bundle)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_users_ads -> {
                    val accessToken = getAccessToken()
                    val bundle = Bundle().apply {
                        putString("accessToken", accessToken)
                    }
                    navController.navigate(R.id.adsByUserFragment, bundle)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_profile -> {
                    val accessToken = getAccessToken()
                    val bundle = Bundle().apply {
                        putString("accessToken", accessToken)
                    }
                    navController.navigate(R.id.userProfileFragment, bundle)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_following_ads -> {
                    val accessToken = getAccessToken()
                    val bundle = Bundle().apply {
                        putString("accessToken", accessToken)
                    }
                    navController.navigate(R.id.followAdFragment, bundle)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_search -> {
                    val accessToken = getAccessToken()
                    val bundle = Bundle().apply {
                        putString("accessToken", accessToken)
                    }
                    navController.navigate(R.id.searchAdFragment, bundle)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }
    }

    private fun getAccessToken(): String {
        val sharedPreferences = applicationContext.getSharedPreferences("com.carmarket", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", "")
        if (accessToken.isNullOrEmpty()) {
            Log.e("MainActivity", "Access token not found in shared preferences.")
        }
        return accessToken ?: ""
    }
}
