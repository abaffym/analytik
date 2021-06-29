package com.marekabaffy.analytik.gradle

import org.gradle.api.provider.Property

abstract class AnalytikExtension {
    abstract val filePath: Property<String>
    abstract val packageName: Property<String>
    abstract val className: Property<String>
}
