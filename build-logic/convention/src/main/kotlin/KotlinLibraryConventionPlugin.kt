import com.diffplug.gradle.spotless.SpotlessExtension
import com.marekabaffy.androidtemplate.configureKotlin
import com.marekabaffy.androidtemplate.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply {
            apply("java-library")
            apply("org.jetbrains.kotlin.jvm")
            apply("com.diffplug.spotless")
        }

        extensions.configure<JavaPluginExtension> {
            configureKotlin(this)
        }

        extensions.configure<SpotlessExtension> {
            configureSpotless(this)
        }
    }
}
