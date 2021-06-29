package com.marekabaffy.analytik.encoder

import com.marekabaffy.analytik.AnalytikData

interface AnalytikEncoder {
    fun encode(data: AnalytikData): String

    fun decode(json: String): AnalytikData
}
