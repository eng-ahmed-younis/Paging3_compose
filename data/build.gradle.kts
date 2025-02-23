plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.images.data"
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
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://newsapi.org/v2/\""
            )

            buildConfigField(
                "String",
                "API_KEY",
                "\"b94ca5c7a9974abbbd1ea53b619c9c89\""
            )
        }
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://newsapi.org/v2/\""
            )
            buildConfigField(
                "String",
                "API_KEY",
                "\"b94ca5c7a9974abbbd1ea53b619c9c89\""
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
        buildConfig = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Core Android libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")


    // Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Local project dependency
    implementation(project(":domain"))

    // Retrofit and Gson for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit 2.9.0
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Retrofit Gson converter
    implementation("com.google.code.gson:gson:2.10.1") // Gson library
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2") // Retrofit Kotlin Coroutines adapter

    // OkHttp for HTTP client
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // OkHttp Logging Interceptor
    implementation("com.squareup.okhttp3:okhttp:4.9.0") // OkHttp 4.x version
    implementation (libs.okhttp3.logging.interceptor)
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
    implementation (libs.google.gson)



    // Hilt for Dependency Injection
    implementation(libs.hilt.android.get())
    kapt(libs.androidx.hilt.compiler.get())

    implementation (libs.androidx.paging.compose)  // Paging 3 Compose


    implementation ("com.github.chuckerteam.chucker:library:4.0.0")



    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")


    kapt ("androidx.lifecycle:lifecycle-compiler:2.8.7")

}
