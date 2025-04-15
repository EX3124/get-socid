plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "sudo.get.socid"
    compileSdk = 36

    defaultConfig {
        applicationId = "sudo.get.socid"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "1.2"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.material)
}