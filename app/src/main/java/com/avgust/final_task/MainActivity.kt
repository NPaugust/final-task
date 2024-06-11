package com.avgust.final_task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.avgust.final_task.databinding.ActivityMainBinding

import dagger.hilt.android  .AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.findFragmentById(R.id.ContainerView) as NavHostFragment

        navController = findNavController(R.id.ContainerView)
        val appBarConfig: AppBarConfiguration = AppBarConfiguration(setOf(
            R.id.productsFragment,
            R.id.searchFragment,
            R.id.profileFragment
        ))

        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBarConfig)


        navController.addOnDestinationChangedListener { _, destination, _ ->

            when(destination.id){
                R.id.splashFragment -> supportActionBar?.hide()
                R.id.viewPagerFragment -> supportActionBar?.hide()
                R.id.searchFragment -> supportActionBar?.show()
                R.id.basketFragment -> hideBottomNavigation()
                R.id.productsFragment -> showBottomNavigation()
                R.id.detailsFragment -> hideBottomNavigation()
            }

        }



    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    fun showBottomNavigation()
    {
        binding.bottomNav.visibility = View.VISIBLE
    }

    fun hideBottomNavigation()
    {
        binding.bottomNav.visibility = View.GONE
    }


}




