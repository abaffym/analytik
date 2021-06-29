@file:Suppress("unused")

package com.marekabaffy.analytik.sample.android

val noParamsEvent = AndroidDemoAnalyticsEvent.NoParamsEvent

val paramsEvent = AndroidDemoAnalyticsEvent.ParamsEvent(
    paramString = "string",
    paramBoolean = false,
    paramInt = 0,
    paramEnum = AndroidDemoAnalyticsEvent.ParamsEvent.ParamEnum.ValueOne
)
