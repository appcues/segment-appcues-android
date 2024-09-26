package com.appcues.segment.examples.kotlin.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.appcues.segment.examples.kotlin.ExampleApplication
import com.appcues.segment.examples.kotlin.R.id
import com.appcues.segment.examples.kotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(id.nav_host_fragment_activity_example)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                id.navigation_events, id.navigation_profile, id.navigation_group
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        handleLinkIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleLinkIntent(intent)
    }

    private fun handleLinkIntent(intent: Intent?) {
        val appcuesHandled = ExampleApplication.appcuesDestination.appcues?.onNewIntent(this, intent) ?: false

        if (appcuesHandled) return

        // otherwise, it was not an Appcues link, the application should handle
    }
}
