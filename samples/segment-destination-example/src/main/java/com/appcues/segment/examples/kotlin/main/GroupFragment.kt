package com.appcues.segment.examples.kotlin.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appcues.segment.examples.kotlin.ExampleApplication
import com.appcues.segment.examples.kotlin.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val analytics = ExampleApplication.analytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.buttonSaveGroup.setOnClickListener {
            val groupID = binding.editTextGroup.text.toString()
            analytics.group(groupID, hashMapOf("test_user" to true))
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        analytics.screen("Update Group")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
