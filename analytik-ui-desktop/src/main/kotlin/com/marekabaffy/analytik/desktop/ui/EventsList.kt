package com.marekabaffy.analytik.desktop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marekabaffy.analytik.AnalytikEvent
import com.marekabaffy.analytik.TrackingId

@Composable
fun EventsList(
    events: List<AnalytikEvent>,
    onRemoveEvent: (trackingId: TrackingId) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        items(events) { event ->
            EventItem(
                modifier =
                    Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                event = event,
                onRemove = { onRemoveEvent(event.trackingId) },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun EventItem(
    event: AnalytikEvent,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        text = {
            Text(
                text = event.trackingId.id,
            )
        },
        trailing = {
            Row {
                IconButton(
                    onClick = onRemove,
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                        )
                    },
                )
            }
        },
    )
}
