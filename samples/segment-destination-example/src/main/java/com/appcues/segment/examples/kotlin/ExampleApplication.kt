package com.appcues.segment.examples.kotlin

import android.app.Application
import com.appcues.LoggingLevel
import com.appcues.segment.AppcuesDestination
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics

class ExampleApplication : Application() {

    companion object {
        lateinit var analytics: Analytics
        lateinit var appcuesDestination: AppcuesDestination
        var currentUserID = "default-0000"
        private const val SESSION_TIMEOUT = 3600
    }

    override fun onCreate() {
        super.onCreate()

        analytics = Analytics(BuildConfig.SEGMENT_WRITE_KEY, applicationContext) {
            this.trackApplicationLifecycleEvents = true
        }

        // Creating a reference to this destination will allow us to access the underlying
        // Appcues SDK in other areas of the code.  This enables access to any additional SDK
        // functionality desired, like the Debugger
        appcuesDestination = AppcuesDestination(applicationContext) {
            // optionally apply customizations using the AppcuesConfig here
            loggingLevel = LoggingLevel.DEBUG
            sessionTimeout = SESSION_TIMEOUT
        }
        analytics.add(appcuesDestination)
    }
}
