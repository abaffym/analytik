package com.marekabaffy.analytik

val AnalytikParam.name
    get() = trackingId.toParamName()

fun TrackingId.toClassName() = id.snakeToPascalCase()

fun TrackingId.toParamName() = id.snakeToCamelCase()
