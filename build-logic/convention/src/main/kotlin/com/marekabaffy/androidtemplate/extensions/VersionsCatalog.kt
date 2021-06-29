package com.marekabaffy.androidtemplate.extensions

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.library(alias: String) = findLibrary(alias).get()

internal fun VersionCatalog.version(alias: String) = findVersion(alias).get().toString()
