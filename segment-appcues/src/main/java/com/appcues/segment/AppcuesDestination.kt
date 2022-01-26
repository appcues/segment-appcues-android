package com.appcues.segment

import android.content.Context
import com.appcues.Appcues
import com.segment.analytics.kotlin.android.plugins.AndroidLifecycle
import com.segment.analytics.kotlin.core.platform.DestinationPlugin
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.*
import com.segment.analytics.kotlin.core.platform.plugins.logger.*
import com.segment.analytics.kotlin.core.utilities.toContent
import kotlinx.serialization.*
import kotlinx.serialization.json.JsonObject

@Serializable
data class AppcuesSettings(
    var appcuesId: String,
    // var applicationId: String // working with Segment to get this added
)

class AppcuesDestination(private val context: Context) : DestinationPlugin(), AndroidLifecycle {
    override val key: String = "Appcues"

    var appcues: Appcues? = null

    override fun update(settings: Settings, type: Plugin.UpdateType) {
        super.update(settings, type)
        if (settings.hasIntegrationSettings(this)) {
            analytics.log("Appcues Destination is enabled")
            val appcuesSettings: AppcuesSettings? = settings.destinationSettings(key)
            if (appcuesSettings != null) {
                appcues = Appcues.Builder(context, appcuesSettings.appcuesId, "").logging(Appcues.LoggingLevel.BASIC).build()
                analytics.log("Appcues Destination loaded")
            }
        } else {
            analytics.log("Appcues destination is disabled via settings")
        }
    }

    override fun identify(payload: IdentifyEvent): BaseEvent {
        appcues?.identify(payload.userId, payload.traits?.mapToAppcues())
        return payload
    }

    override fun track(payload: TrackEvent): BaseEvent {
        appcues?.track(payload.event, payload.properties?.mapToAppcues())
        return payload
    }

    override fun screen(payload: ScreenEvent): BaseEvent {
        appcues?.screen(payload.name, payload.properties?.mapToAppcues())
        return payload
    }

    override fun group(payload: GroupEvent): BaseEvent {
        appcues?.group(payload.groupId, payload.traits?.mapToAppcues())
        return payload
    }

    private fun JsonObject.mapToAppcues(): HashMap<String, Any>? { // = this.mapValues { (_, value) ->
        val map: HashMap<String, Any> = hashMapOf()
        this.forEach { (key, value) ->
            val newValue = value.toContent()
            if (newValue != null && isAllowedProperty(newValue)) {
                map[key] = newValue
            }
        }
        if (map.size == 0) return null
        return map
    }

    private fun isAllowedProperty(value: Any): Boolean {
        // TODO: will need to evolve as we see what primitive types the underlying SDK
        // will support in the analytics network traffic - ex: URLs or Dates?
        return value is String || value is Boolean || value is Int || value is Double
    }
}