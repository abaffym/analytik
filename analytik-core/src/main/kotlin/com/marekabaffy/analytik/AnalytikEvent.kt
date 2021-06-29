package com.marekabaffy.analytik

@JvmInline
value class TrackingId(val id: String)

@JvmInline
value class Group(val name: String)

data class AnalytikData(
    val version: String,
    val events: List<AnalytikEvent>,
)

data class AnalytikEvent(
    val trackingId: TrackingId,
    val description: String?,
    val group: Group?,
    val params: List<AnalytikParam>,
)

data class AnalytikParam(
    val trackingId: TrackingId,
    val type: ParameterType,
)

sealed interface ParameterType {
    data object ParamInt : ParameterType

    data object ParamString : ParameterType

    data object ParamBoolean : ParameterType

    data class ParamEnum(val values: List<String>) : ParameterType
}
