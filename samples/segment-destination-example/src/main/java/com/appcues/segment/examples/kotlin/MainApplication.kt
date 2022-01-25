package com.appcues.segment.examples.kotlin

import android.app.Application
import com.appcues.segment.AppcuesDestination
import com.segment.analytics.kotlin.android.Analytics
import com.segment.analytics.kotlin.core.Analytics

class MainApplication: Application() {
    companion object {
        lateinit var analytics: Analytics
    }

    override fun onCreate() {
        super.onCreate()

        analytics = Analytics(BuildConfig.SEGMENT_WRITE_KEY, applicationContext) {
            this.trackApplicationLifecycleEvents = true
        }

        analytics.add(AppcuesDestination(applicationContext))
    }
}