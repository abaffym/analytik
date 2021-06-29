package com.marekabaffy.analytik.desktop

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.marekabaffy.analytik.AnalytikEvent
import com.marekabaffy.analytik.TrackingId
import com.marekabaffy.analytik.encoder.json.JsonAnalytikEncoder
import java.io.File

private val encoder = JsonAnalytikEncoder()

fun main() =
    application {
        Window(
            title = "AnalytiK",
            onCloseRequest = ::exitApplication,
        ) {
            MaterialTheme {
                var eventsMap by remember { mutableStateOf(emptyMap<TrackingId, AnalytikEvent>()) }

                AnalytikApp(
                    modifier = Modifier.fillMaxSize(),
                    events = eventsMap.values.toList(),
                    onCreateOrUpdateEvent = { newEvent ->
                        eventsMap += newEvent.trackingId to newEvent
                    },
                    onRemoveEvent = { trackingId ->
                        eventsMap = eventsMap.minus(trackingId)
                    },
                    onImport = { filePath ->
                        val file = File(filePath)
                        eventsMap = encoder.decode(file.readText()).associateBy { it.trackingId }
                    },
                    export = { filePath ->
                        val encodedJson = encoder.encode(eventsMap.values.toList())
                        val file = File(filePath)
                        file.writeText(encodedJson)
                    },
                )
            }
        }
    }
