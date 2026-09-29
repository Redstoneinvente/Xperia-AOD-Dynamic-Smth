plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.redstoneinvente.xperiaaod"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.redstoneinvente.xperiaaod"
        minSdk = 26
        // Keep the runtime behavior opt-in level unchanged; only compileSdk is raised.
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}
