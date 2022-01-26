package com.appcues.segment.examples.kotlin.signin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.appcues.segment.examples.kotlin.ExampleApplication
import com.appcues.segment.examples.kotlin.R.id
import com.appcues.segment.examples.kotlin.R.string
import com.appcues.segment.examples.kotlin.databinding.ActivitySigninBinding
import com.appcues.segment.examples.kotlin.main.MainActivity
import com.appcues.segment.examples.kotlin.R

class SignInActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySigninBinding.inflate(layoutInflater) }
    private val analytics = ExampleApplication.analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setTitle(string.title_activity_signin)

        setContentView(binding.root)

        binding.editTextUserName.setText(ExampleApplication.currentUserID)

        binding.buttonSignIn.setOnClickListener {
            val userInput = binding.editTextUserName.text.toString()
            val userID = if (userInput.isNotEmpty()) userInput else ExampleApplication.currentUserID
            analytics.identify(userID)
            ExampleApplication.currentUserID = userID
            completeSignIn()
        }

        binding.buttonAnonymous.setOnClickListener {
            // Segment does not have a matching concept to the Appcues
            // anonymous() function - so we can use the Appcues SDK
            // directly if that functionality is required
            //analytics.anonymous()
            ExampleApplication.appcuesDestination.appcues?.anonymous()
            completeSignIn()
        }
    }

    override fun onResume() {
        super.onResume()
        analytics.screen("Sign In")
    }

    private fun completeSignIn() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sign_in_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            id.skip -> {
                completeSignIn()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
