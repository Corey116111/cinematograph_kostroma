plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.intensiv2"
    compileSdk = 36

    defaultConfig {
        val yandexMapsApiKey = project.properties["YANDEX_MAPS_API_KEY"] as? String ?: ""
        applicationId = "com.example.intensiv2"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["YANDEX_MAPS_API_KEY"] = yandexMapsApiKey
        buildConfigField("String", "YANDEX_MAPS_API_KEY", "\"$yandexMapsApiKey\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    //noinspection Aligned16KB
    implementation("com.yandex.android:maps.mobile:4.17.0-full")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}