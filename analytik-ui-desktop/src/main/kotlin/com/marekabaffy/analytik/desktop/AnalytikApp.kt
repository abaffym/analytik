package com.marekabaffy.analytik.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.marekabaffy.analytik.AnalytikEvent
import com.marekabaffy.analytik.TrackingId
import com.marekabaffy.analytik.desktop.ui.AnalytikTopAppBar
import com.marekabaffy.analytik.desktop.ui.EventsList

@Composable
fun AnalytikApp(
    modifier: Modifier = Modifier,
    events: List<AnalytikEvent>,
    onCreateOrUpdateEvent: (event: AnalytikEvent) -> Unit,
    onRemoveEvent: (trackingId: TrackingId) -> Unit,
    onImport: (filePath: String) -> Unit,
    export: (directoryPath: String) -> Unit,
) {
    var createEventDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            AnalytikTopAppBar(
                onImport = onImport,
                export = export,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    createEventDialogVisible = true
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                    )
                },
            )
        },
        content = {
            EventsList(
                modifier =
                    Modifier
                        .fillMaxSize(),
                onRemoveEvent = onRemoveEvent,
                events = events,
            )
        },
    )

    if (createEventDialogVisible) {
        CreateEventDialog(
            onCreateEvent = { newEvent ->
                onCreateOrUpdateEvent(newEvent)
                createEventDialogVisible = false
            },
            onDismissRequest = {
                createEventDialogVisible = false
            },
        )
    }
}

@Composable
fun CreateEventDialog(
    onCreateEvent: (event: AnalytikEvent) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var eventName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            Column(
                modifier =
                    Modifier
                        .background(Color.White)
                        .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextField(
                    label = { Text("Tracking id") },
                    maxLines = 1,
                    value = eventName,
                    onValueChange = { eventName = it },
                )

                Button(
                    onClick = {
                        onCreateEvent(
                            AnalytikEvent(
                                trackingId = TrackingId(eventName),
                                params = emptyList(),
                            ),
                        )
                    },
                    content = {
                        Text("SAVE")
                    },
                )
            }
        },
    )
}
