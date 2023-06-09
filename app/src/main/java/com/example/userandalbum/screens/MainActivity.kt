package com.example.userandalbum.screens

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.userandalbum.R
import com.example.userandalbum.databinding.ActivityMainBinding
import com.example.userandalbum.viewModels.UserDetailsViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userDetailsViewModel: UserDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        init()
    }

    private fun init() {
        userDetailsViewModel = viewModelFactory.getUserDetailsViewModel()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.userAndAlbumNavHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when (destination.label) {
                "userDetails" -> {
                    binding.collapsingToolbar.apply {
                        title = "User Details"
                        setCollapsedTitleTypeface(Typeface.DEFAULT_BOLD)
                        setExpandedTitleTypeface(Typeface.DEFAULT_BOLD)
                    }
                }
                "albumDetails" -> {
                    binding.collapsingToolbar.apply {
                        title = "Albums"
                        setCollapsedTitleTypeface(Typeface.DEFAULT_BOLD)
                        setExpandedTitleTypeface(Typeface.DEFAULT_BOLD)
                    }
                    //openAlbumListFragment()
                }
            }
        }


        binding.bottomNavigationView.setupWithNavController(navController)
    }
}