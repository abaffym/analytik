package com.marekabaffy.analytik.encoder.json

import com.marekabaffy.analytik.AnalytikData
import com.marekabaffy.analytik.AnalytikEvent
import com.marekabaffy.analytik.AnalytikParam
import com.marekabaffy.analytik.Group
import com.marekabaffy.analytik.ParameterType
import com.marekabaffy.analytik.TrackingId
import com.marekabaffy.analytik.encoder.AnalytikEncoder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonAnalytikEncoder : AnalytikEncoder {
    @OptIn(ExperimentalSerializationApi::class)
    override fun encode(data: AnalytikData): String {
        val jsonData =
            JsonAnalytikData(
                version = data.version,
                events =
                    data.events.map { event ->
                        JsonAnalytikEvent(
                            trackingId = event.trackingId.id,
                            description = event.description,
                            group = event.group?.name,
                            params = if (event.params.isEmpty()) null else event.params.map { it.encode() },
                        )
                    },
            )
        val format =
            Json {
                prettyPrint = true
                prettyPrintIndent = "  "
            }
        return format.encodeToString(jsonData)
    }

    override fun decode(json: String): AnalytikData {
        val parsedStructure = Json.decodeFromString<JsonAnalytikData>(json)
        return AnalytikData(
            version = parsedStructure.version,
            events =
                parsedStructure.events.map { eventData ->
                    AnalytikEvent(
                        trackingId = TrackingId(eventData.trackingId),
                        group = eventData.group?.let(::Group),
                        description = eventData.description,
                        params =
                            eventData.params.orEmpty().map { param ->
                                AnalytikParam(
                                    trackingId = TrackingId(param.trackingId),
                                    type = param.decode(),
                                )
                            },
                    )
                },
        )
    }
}

private fun JsonAnalytikParam.decode(): ParameterType =
    when (this.type) {
        "BOOLEAN" -> ParameterType.ParamBoolean
        "INT" -> ParameterType.ParamInt
        "STRING" -> ParameterType.ParamString
        "ENUM" -> ParameterType.ParamEnum(values = values!!)
        else -> throw IllegalArgumentException("Invalid parameter type: $this")
    }

private fun AnalytikParam.encode(): JsonAnalytikParam =
    when (val type = this.type) {
        is ParameterType.ParamBoolean ->
            JsonAnalytikParam(
                trackingId = trackingId.id,
                type = "BOOLEAN",
            )

        is ParameterType.ParamInt ->
            JsonAnalytikParam(
                trackingId = trackingId.id,
                type = "INT",
            )

        is ParameterType.ParamString ->
            JsonAnalytikParam(
                trackingId = trackingId.id,
                type = "STRING",
            )

        is ParameterType.ParamEnum ->
            JsonAnalytikParam(
                trackingId = trackingId.id,
                type = "ENUM",
                values = type.values,
            )

        else -> throw IllegalArgumentException("Invalid parameter type: $this")
    }
