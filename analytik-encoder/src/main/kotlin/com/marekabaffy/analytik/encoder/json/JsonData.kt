package com.marekabaffy.analytik.encoder.json

import kotlinx.serialization.Serializable

@Serializable
internal data class JsonAnalytikData(
    val version: String,
    val events: List<JsonAnalytikEvent>,
)

@Serializable
internal data class JsonAnalytikEvent(
    val trackingId: String,
    val description: String? = null,
    val group: String? = null,
    val params: List<JsonAnalytikParam>? = null,
)

@Serializable
internal data class JsonAnalytikParam(
    val trackingId: String,
    val type: String,
    val values: List<String>? = null,
)
