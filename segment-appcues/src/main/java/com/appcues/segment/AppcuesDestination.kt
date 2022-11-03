package com.appcues.segment

import android.content.Context
import com.appcues.Appcues
import com.appcues.AppcuesConfig
import com.segment.analytics.kotlin.android.plugins.AndroidLifecycle
import com.segment.analytics.kotlin.core.BaseEvent
import com.segment.analytics.kotlin.core.GroupEvent
import com.segment.analytics.kotlin.core.IdentifyEvent
import com.segment.analytics.kotlin.core.ScreenEvent
import com.segment.analytics.kotlin.core.Settings
import com.segment.analytics.kotlin.core.TrackEvent
import com.segment.analytics.kotlin.core.platform.DestinationPlugin
import com.segment.analytics.kotlin.core.platform.Plugin
import com.segment.analytics.kotlin.core.platform.plugins.logger.log
import com.segment.analytics.kotlin.core.utilities.toContent
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonObject

@Serializable
data class AppcuesSettings(
    var accountId: String,
    var applicationId: String
)

class AppcuesDestination(
    private val context: Context,
    private val config: (AppcuesConfig.() -> Unit)? = null,
) : DestinationPlugin(), AndroidLifecycle {

    companion object {
        private const val APPCUES_EVENT_PREFIX = "appcues:"
    }

    override val key: String = "Appcues Mobile"

    val version: String
        get() = "${BuildConfig.PLUGIN_VERSION}-${BuildConfig.BUILD_TYPE}"

    var appcues: Appcues? = null

    override fun update(settings: Settings, type: Plugin.UpdateType) {
        super.update(settings, type)

        if (settings.hasIntegrationSettings(this)) {
            try {
                val appcuesSettings: AppcuesSettings? = settings.destinationSettings(key)
                if (appcuesSettings != null) {
                    appcues = Appcues(context, appcuesSettings.accountId, appcuesSettings.applicationId) {
                        config?.invoke(this)
                        this.additionalAutoProperties = this.additionalAutoProperties.toMutableMap().apply {
                            this["_segmentVersion"] = analytics.version()
                        }
                    }
                    analytics.log("$key destination loaded")
                }
            } catch (exception: SerializationException) {
                analytics.log("$key destination failed to load. ${exception.message}")
            }
        } else {
            analytics.log("$key destination is disabled via settings")
        }
    }

    override fun identify(payload: IdentifyEvent): BaseEvent {
        appcues?.identify(payload.userId, payload.traits.mapToAppcues())
        return payload
    }

    override fun track(payload: TrackEvent): BaseEvent {
        if (payload.isValid) {
            appcues?.track(payload.event, payload.properties.mapToAppcues())
        }
        return payload
    }

    override fun screen(payload: ScreenEvent): BaseEvent {
        if (payload.isValid) {
            appcues?.screen(payload.name, payload.properties.mapToAppcues())
        }
        return payload
    }

    override fun group(payload: GroupEvent): BaseEvent {
        appcues?.group(payload.groupId, payload.traits.mapToAppcues())
        return payload
    }

    private fun JsonObject.mapToAppcues(): HashMap<String, Any>? {
        val map: HashMap<String, Any> = hashMapOf()
        this.forEach { (key, value) ->
            val newValue = value.toContent()
            if (newValue != null && newValue.isAllowedPropertyType) {
                map[key] = newValue
            }
        }
        if (map.size == 0) return null
        return map
    }

    private val TrackEvent.isValid: Boolean
        get() = this.event.isNotEmpty() && !this.event.lowercase().startsWith(APPCUES_EVENT_PREFIX)

    private val ScreenEvent.isValid: Boolean
        get() = this.name.isNotEmpty() && !this.name.lowercase().startsWith(APPCUES_EVENT_PREFIX)

    private val Any.isAllowedPropertyType: Boolean
        get() {
            // The segment library requires passing properties as a JsonObject,
            // and Appcues supports the following primitive types, not nested arrays
            // or nested objects. It is not possible for a Date or Uri to be passed
            // to this plugin, for example, as a property type - only Json types
            // like String, Boolean or Number.
            return this is String ||
                this is Boolean ||
                this is Double ||
                this is Int ||
                this is Long
        }
}
