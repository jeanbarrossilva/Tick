import org.gradle.api.JavaVersion

object Versions {
    val java = JavaVersion.VERSION_11

    object Tick {
        const val MIN_SDK = 24
        const val TARGET_SDK = 33
    }
}
