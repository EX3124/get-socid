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
    signingConfigs {
        create("release") {
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
