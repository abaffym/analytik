package com.marekabaffy.analytik

import java.util.Locale

private val snakeRegex = "_[a-zA-Z]".toRegex()

fun String.snakeToCamelCase(): String =
    snakeRegex.replace(this) {
        it.value
            .replace("_", "")
            .uppercase()
    }

fun String.snakeToPascalCase(): String = snakeToCamelCase().capitalizeWithLocal()

private fun String.capitalizeWithLocal() = replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
