package com.marekabaffy.analytik.encoder

import com.marekabaffy.analytik.AnalytikData
import com.marekabaffy.analytik.AnalytikEvent
import com.marekabaffy.analytik.AnalytikParam
import com.marekabaffy.analytik.Group
import com.marekabaffy.analytik.ParameterType
import com.marekabaffy.analytik.TrackingId
import com.marekabaffy.analytik.encoder.json.JsonAnalytikEncoder
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonAnalytikEncoderTest {
    private val jsonAnalytikEncoder = JsonAnalytikEncoder()

    @Test
    fun `decode event no params`() {
        val jsonString =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "no_params_event",
                  "description": "Event with no parameters"
                }
              ]
            }
            """.trimIndent()

        val expectedData =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("no_params_event"),
                            description = "Event with no parameters",
                            group = null,
                            params = emptyList(),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedData,
            actual = jsonAnalytikEncoder.decode(jsonString),
        )
    }

    @Test
    fun `decode event all params`() {
        val jsonString =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "params_event",
                  "description": "Event with all parameters",
                  "params": [
                    {
                      "trackingId": "param_string",
                      "type": "STRING"
                    },
                    {
                      "trackingId": "param_boolean",
                      "type": "BOOLEAN"
                    },
                    {
                      "trackingId": "param_int",
                      "type": "INT"
                    },
                    {
                      "trackingId": "param_enum",
                      "type": "ENUM",
                      "values": [
                        "value_one",
                        "value_two",
                        "value_three"
                      ]
                    }
                  ]
                }
              ]
            }
            """.trimIndent()

        val expectedData =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("params_event"),
                            description = "Event with all parameters",
                            group = null,
                            params =
                                listOf(
                                    AnalytikParam(
                                        trackingId = TrackingId("param_string"),
                                        type = ParameterType.ParamString,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_boolean"),
                                        type = ParameterType.ParamBoolean,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_int"),
                                        type = ParameterType.ParamInt,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_enum"),
                                        type =
                                            ParameterType.ParamEnum(
                                                values =
                                                    listOf(
                                                        "value_one",
                                                        "value_two",
                                                        "value_three",
                                                    ),
                                            ),
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedData,
            actual = jsonAnalytikEncoder.decode(jsonString),
        )
    }

    @Test
    fun `decode event group`() {
        val jsonString =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "no_params_event",
                  "group": "TestGroup"
                }
              ]
            }
            """.trimIndent()

        val expectedData =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("no_params_event"),
                            description = null,
                            group = Group("TestGroup"),
                            params = emptyList(),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedData,
            actual = jsonAnalytikEncoder.decode(jsonString),
        )
    }

    @Test
    fun `encode event no params`() {
        val expectedJson =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "no_params_event",
                  "description": "Event with no parameters"
                }
              ]
            }
            """.trimIndent()

        val data =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("no_params_event"),
                            description = "Event with no parameters",
                            group = null,
                            params = emptyList(),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedJson,
            actual = jsonAnalytikEncoder.encode(data),
        )
    }

    @Test
    fun `encode event all params`() {
        val expectedJson =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "params_event",
                  "description": "Event with all parameters",
                  "params": [
                    {
                      "trackingId": "param_string",
                      "type": "STRING"
                    },
                    {
                      "trackingId": "param_boolean",
                      "type": "BOOLEAN"
                    },
                    {
                      "trackingId": "param_int",
                      "type": "INT"
                    },
                    {
                      "trackingId": "param_enum",
                      "type": "ENUM",
                      "values": [
                        "value_one",
                        "value_two",
                        "value_three"
                      ]
                    }
                  ]
                }
              ]
            }
            """.trimIndent()

        val data =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("params_event"),
                            description = "Event with all parameters",
                            group = null,
                            params =
                                listOf(
                                    AnalytikParam(
                                        trackingId = TrackingId("param_string"),
                                        type = ParameterType.ParamString,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_boolean"),
                                        type = ParameterType.ParamBoolean,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_int"),
                                        type = ParameterType.ParamInt,
                                    ),
                                    AnalytikParam(
                                        trackingId = TrackingId("param_enum"),
                                        type =
                                            ParameterType.ParamEnum(
                                                values =
                                                    listOf(
                                                        "value_one",
                                                        "value_two",
                                                        "value_three",
                                                    ),
                                            ),
                                    ),
                                ),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedJson.trimIndent(),
            actual = jsonAnalytikEncoder.encode(data),
        )
    }

    @Test
    fun `encode event group`() {
        val expectedJson =
            """     
            {
              "version": "0.0.1",
              "events": [
                {
                  "trackingId": "no_params_event",
                  "group": "TestGroup"
                }
              ]
            }
            """.trimIndent()

        val data =
            AnalytikData(
                version = "0.0.1",
                events =
                    listOf(
                        AnalytikEvent(
                            trackingId = TrackingId("no_params_event"),
                            description = null,
                            group = Group("TestGroup"),
                            params = emptyList(),
                        ),
                    ),
            )

        assertEquals(
            expected = expectedJson,
            actual = jsonAnalytikEncoder.encode(data),
        )
    }
}
