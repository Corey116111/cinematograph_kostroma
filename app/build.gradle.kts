plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.intensiv2"
    compileSdk = 36

    buildFeatures {
        buildConfig = true  
    }

    defaultConfig {
        applicationId = "com.example.intensiv2"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val yandexMapsApiKey = project.properties["YANDEX_MAPS_API_KEY"] as? String ?: ""

        manifestPlaceholders["YANDEX_MAPS_API_KEY"] = yandexMapsApiKey
        buildConfigField("String", "YANDEX_MAPS_API_KEY", "\"$yandexMapsApiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.yandex.android:maps.mobile:4.5.1-full")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.appcompat:appcompat:1.6.1")
}