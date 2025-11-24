plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}



android {
    namespace = "com.example.belajarapi"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.belajarapi"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    // --- BAWAAN ANDROID (JANGAN DIHAPUS) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- TESTING ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ============================================
    // BAHAN BAKU UTAMA (PASTIKAN INI ADA)
    // ============================================

    // 1. NETWORKING (Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 2. GAMBAR (Coil 3 - Versi Stabil 3.0.4)
    // HANYA PAKAI 2 BARIS INI UNTUK COIL, JANGAN DITAMBAH YANG LAIN
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

    // 3. LIFECYCLE (Agar ViewModel Berjalan)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}
