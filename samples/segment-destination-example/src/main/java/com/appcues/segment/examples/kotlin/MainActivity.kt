package com.appcues.segment.examples.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.json.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        MainApplication.analytics.screen("Main Activity", buildJsonObject {
            put("string_prop", "value")
            put("one", 1)
        })
    }
}