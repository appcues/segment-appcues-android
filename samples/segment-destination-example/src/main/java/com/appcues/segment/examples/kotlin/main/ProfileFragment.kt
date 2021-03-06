package com.appcues.segment.examples.kotlin.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appcues.segment.examples.kotlin.ExampleApplication
import com.appcues.segment.examples.kotlin.R
import com.appcues.segment.examples.kotlin.databinding.FragmentProfileBinding
import com.appcues.segment.examples.kotlin.signin.SignInActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val analytics = ExampleApplication.analytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonSaveProfile.setOnClickListener {
            val properties: HashMap<String, Any> = hashMapOf()

            val givenName = binding.editTextGivenName.text.toString()
            if (givenName.isNotEmpty()) {
                properties["givenName"] = givenName
            }

            val familyName = binding.editTextFamilyName.text.toString()
            if (familyName.isNotEmpty()) {
                properties["familyName"] = familyName
            }

            analytics.identify(ExampleApplication.currentUserID, properties)

            binding.editTextGivenName.text = null
            binding.editTextFamilyName.text = null
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        analytics.screen("Update Profile")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out -> {
                analytics.reset()
                ExampleApplication.currentUserID = ""
                val intent = Intent(activity, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
                startActivity(intent)
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
