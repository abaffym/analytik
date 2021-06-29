package com.marekabaffy.analytik.desktop.ui.util

import androidx.compose.ui.awt.ComposeWindow
import java.awt.FileDialog
import java.io.FilenameFilter

fun importFile(onFileImported: (String) -> Unit) {
    val dialog =
        FileDialog(ComposeWindow()).apply {
            file = null
            mode = FileDialog.LOAD
            isVisible = true
            filenameFilter =
                FilenameFilter { _, name -> name.endsWith(".json") }
        }
    if (dialog.file != null) {
        val result = dialog.directory + "/" + dialog.file
        onFileImported(result)
    }
    dialog.file = null
}

fun exportFile(
    defaultFileName: String,
    export: (String) -> Unit,
) {
    val dialog =
        FileDialog(ComposeWindow()).apply {
            file = defaultFileName
            mode = FileDialog.SAVE
            isVisible = true
            filenameFilter =
                FilenameFilter { _, name -> name.endsWith(".json") }
        }
    if (dialog.file != null) {
        val result = dialog.directory + "/" + dialog.file
        export(result)
    }
    dialog.file = null
}
