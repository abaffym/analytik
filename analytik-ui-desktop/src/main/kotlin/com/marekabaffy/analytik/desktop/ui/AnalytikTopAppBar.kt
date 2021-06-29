package com.marekabaffy.analytik.desktop.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.marekabaffy.analytik.desktop.ui.util.exportFile
import com.marekabaffy.analytik.desktop.ui.util.importFile

@Composable
fun AnalytikTopAppBar(
    onImport: (filePath: String) -> Unit,
    export: (directoryPath: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text("AnalytiK") },
        actions = {
            Button(
                onClick = { importFile(onImport) },
            ) { Text("Import") }

            Button(
                onClick = {
                    exportFile(
                        defaultFileName = "export.json",
                        export = export,
                    )
                },
            ) { Text("Export") }
        },
    )
}
