package com.marekabaffy.analytik

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import java.io.File

private object ParameterName {
    const val TRACKING_ID = "trackingId"
    const val EVENT_NAME = "name"
    const val EVENT_PARAMS = "params"
}

class AnalytikGenerator {
    fun generate(
        data: AnalytikData,
        path: String,
        className: String,
        packageName: String,
    ) {
        val rootClassName = ClassName(packageName, className)

        val rootTypeSpec =
            TypeSpec.classBuilder(rootClassName)
                .addModifiers(KModifier.SEALED)
                .addKdoc("Version: ${data.version}")
                .primaryConstructor(rootConstructor())
                .addProperties(rootProperties())
                .addTypes(groupsAndEvents(data.events, rootClassName))
                .build()

        FileSpec.builder(packageName, className)
            .addType(rootTypeSpec)
            .build()
            .writeTo(File(path))
    }

    private fun rootConstructor(): FunSpec =
        FunSpec.constructorBuilder()
            .addParameter(ParameterName.EVENT_NAME, String::class)
            .addParameter(
                ParameterSpec.builder(
                    ParameterName.EVENT_PARAMS,
                    Map::class.parameterizedBy(String::class, String::class),
                )
                    .defaultValue(buildCodeBlock { add("emptyMap()") })
                    .build(),
            ).build()

    private fun rootProperties(): List<PropertySpec> =
        listOf(
            PropertySpec.builder(ParameterName.EVENT_NAME, String::class)
                .initializer(ParameterName.EVENT_NAME)
                .build(),
            PropertySpec.builder(
                ParameterName.EVENT_PARAMS,
                Map::class.parameterizedBy(String::class, String::class),
            )
                .initializer(ParameterName.EVENT_PARAMS)
                .build(),
        )

    private fun groupsAndEvents(
        events: List<AnalytikEvent>,
        rootClassName: ClassName,
    ): List<TypeSpec> =
        eventsWithGroup(
            events = events.filter { it.group != null },
            rootClassName = rootClassName,
        ) +
            events(
                events = events.filter { it.group == null },
                rootClassName = rootClassName,
            )

    private fun eventsWithGroup(
        events: List<AnalytikEvent>,
        rootClassName: ClassName,
    ): List<TypeSpec> =
        events
            .groupBy { it.group }
            .map { (group, events) ->
                group(
                    group = group!!,
                    events = events,
                    rootClassName = rootClassName,
                )
            }

    private fun group(
        group: Group,
        events: List<AnalytikEvent>,
        rootClassName: ClassName,
    ) = TypeSpec.interfaceBuilder(group.name)
        .addModifiers(KModifier.SEALED)
        .addTypes(events(events, rootClassName))
        .build()

    private fun events(
        events: List<AnalytikEvent>,
        rootClassName: ClassName,
    ) = events.map { event ->
        if (event.params.isEmpty()) {
            eventObject(event, rootClassName)
        } else {
            eventClass(event, rootClassName)
        }
    }

    private fun eventObject(
        event: AnalytikEvent,
        rootClassName: ClassName,
    ) = TypeSpec.objectBuilder(event.trackingId.toClassName())
        .superclass(rootClassName)
        .addModifiers(KModifier.DATA)
        .apply { event.description?.let(::addKdoc) }
        .addSuperclassConstructorParameter("%S", event.trackingId.id)
        .build()

    private fun eventClass(
        event: AnalytikEvent,
        rootClassName: ClassName,
    ) = TypeSpec.classBuilder(event.trackingId.toClassName())
        .superclass(rootClassName)
        .apply { event.description?.let(::addKdoc) }
        .addSuperclassConstructorParameter("%S", event.trackingId.id)
        .addSuperclassConstructorParameter(
            buildCodeBlock {
                val paramsString =
                    event.params
                        .map { param ->
                            "\"${param.trackingId.id}\"" to
                                when (param.type) {
                                    ParameterType.ParamBoolean -> "${param.name}.toString()"
                                    is ParameterType.ParamEnum -> "${param.name}.${ParameterName.TRACKING_ID}"
                                    ParameterType.ParamInt -> "${param.name}.toString()"
                                    ParameterType.ParamString -> param.name
                                }
                        }
                        .joinToString(separator = ", ") { (first, second) -> "$first to $second" }
                add("mapOf($paramsString)")
            },
        )
        .addTypes(eventClassParams(event))
        .primaryConstructor(eventClassConstructor(event))
        .build()

    private fun eventClassConstructor(event: AnalytikEvent) =
        FunSpec.constructorBuilder()
            .apply {
                event.params.map { param ->
                    addParameter(
                        param.name,
                        when (param.type) {
                            ParameterType.ParamBoolean -> Boolean::class.asClassName()
                            is ParameterType.ParamEnum ->
                                ClassName(
                                    "",
                                    param.trackingId.toClassName(),
                                )

                            ParameterType.ParamInt -> Int::class.asClassName()
                            ParameterType.ParamString -> String::class.asClassName()
                        },
                    )
                }
            }
            .build()

    private fun eventClassParams(event: AnalytikEvent) =
        event.params
            .filter { param -> param.type is ParameterType.ParamEnum }
            .map { enumParam ->
                TypeSpec.enumBuilder(enumParam.trackingId.toClassName())
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter(ParameterName.TRACKING_ID, String::class)
                            .build(),
                    )
                    .addProperty(
                        PropertySpec.builder(ParameterName.TRACKING_ID, String::class)
                            .initializer(ParameterName.TRACKING_ID)
                            .build(),
                    )
                    .apply {
                        (enumParam.type as ParameterType.ParamEnum).values.forEach { constant ->
                            addEnumConstant(
                                constant.snakeToPascalCase(),
                                TypeSpec.anonymousClassBuilder()
                                    .addSuperclassConstructorParameter("%S", constant)
                                    .build(),
                            )
                        }
                    }.build()
            }
}
