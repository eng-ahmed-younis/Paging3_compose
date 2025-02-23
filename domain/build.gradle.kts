plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias (libs.plugins.hilt)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.images.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Add Paging Compose from the version catalog

    // Add Hilt dependencies from the version catalog
    implementation(libs.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    implementation (libs.androidx.paging.compose)  // Paging 3 Compose
    implementation(libs.androidx.paging.runtime.ktx)
    // Other necessary dependencies
    implementation(libs.google.gson)
}
