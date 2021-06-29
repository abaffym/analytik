@file:Suppress("unused")

package com.marekabaffy.analytik.sample.kotlinjvm

val noParamsEvent = KotlinJvmDemoAnalyticsEvent.NoParamsEvent

val paramsEvent =
    KotlinJvmDemoAnalyticsEvent.ParamsEvent(
        paramString = "string",
        paramBoolean = false,
        paramInt = 0,
        paramEnum = KotlinJvmDemoAnalyticsEvent.ParamsEvent.ParamEnum.ValueOne,
    )

val groupEvent = KotlinJvmDemoAnalyticsEvent.TestGroup.TestGroupNoParamsEvent

val groupParamsEvent =
    KotlinJvmDemoAnalyticsEvent.TestGroup.TestGroupParamsEvent(
        paramString = "string",
    )
