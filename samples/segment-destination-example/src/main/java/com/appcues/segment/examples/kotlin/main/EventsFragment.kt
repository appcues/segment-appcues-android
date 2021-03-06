package com.appcues.segment.examples.kotlin.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appcues.segment.examples.kotlin.ExampleApplication
import com.appcues.segment.examples.kotlin.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val analytics = ExampleApplication.analytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)

        binding.buttonEvent1.setOnClickListener {
            analytics.track("event1")
        }

        binding.buttonEvent2.setOnClickListener {
            analytics.track("event2")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        analytics.screen("Trigger Events")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
