object Project {
    val android = AndroidProject(
        compileSdk = 33,
        minSdk = 24,
        targetSdk = 33,

        versionCode = 7,
        versionName = "1.3",
    )
}

data class AndroidProject(
    val compileSdk: Int,
    val minSdk: Int,
    val targetSdk: Int,

    val versionCode: Int,
    val versionName: String,
)