package com.appcues.segment

import android.content.Context
import com.appcues.Appcues
import com.segment.analytics.kotlin.android.plugins.AndroidLifecycle
import com.segment.analytics.kotlin.core.platform.DestinationPlugin
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.*
import com.segment.analytics.kotlin.core.platform.plugins.logger.*
import kotlinx.serialization.*

@Serializable
data class AppcuesSettings(
    var appcuesId: String,
    // var applicationId: String // working with Segment to get this added
)

class AppcuesDestination(private val context: Context) : DestinationPlugin(), AndroidLifecycle {
    override val key: String = "Appcues"

    private var appcues: Appcues? = null

    override fun update(settings: Settings, type: Plugin.UpdateType) {
        super.update(settings, type)
        if (settings.hasIntegrationSettings(this)) {
            analytics.log("Appcues Destination is enabled")
            val appcuesSettings: AppcuesSettings? = settings.destinationSettings(key)
            if (appcuesSettings != null) {
                appcues = Appcues.Builder(context, appcuesSettings.appcuesId, "").build()
                analytics.log("Appcues Destination loaded")
            }
        } else {
            analytics.log("Appcues destination is disabled via settings")
        }
    }

    override fun identify(payload: IdentifyEvent): BaseEvent {
        appcues?.identify(payload.userId, null) //TODO: map props
        return payload
    }

    override fun track(payload: TrackEvent): BaseEvent {
        appcues?.track(payload.event, null) //TODO: map props
        return payload
    }

    override fun screen(payload: ScreenEvent): BaseEvent {
        appcues?.screen(payload.name, null) //TODO: map props
        return payload
    }

    override fun group(payload: GroupEvent): BaseEvent {
        appcues?.group(payload.groupId, null) //TODO: map props
        return payload
    }
}