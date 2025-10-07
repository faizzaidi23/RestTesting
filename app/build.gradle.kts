plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)

    //Adding the kotlinx serialization plugin to handle the json data
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

android {
    namespace = "com.example.authorizationtesting"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.authorizationtesting"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
}

dependencies {


    //AndroidX and Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    //For the jetPackCompose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    //For Navigation
    implementation(libs.androidx.navigation.compose)

    //Room Database
    implementation(libs.bundles.androidx.room)
    ksp(libs.androidx.room.compiler)

    //----Additions for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // The core library for making network requests to your backend
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // The library for converting Kotlin objects to and from JSON.
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0") // A converter that allows retrofit to use kotlinx Serialization
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // A utility to log network request and response details, which is very helpful for debugging


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}