// --- BAGIAN 1: PLUGIN (ALAT BANTU) ---
plugins {
    // Plugin agar ini dianggap sebagai Aplikasi Android
    alias(libs.plugins.android.application)
    // Plugin agar kita bisa menulis pakai bahasa Kotlin
    alias(libs.plugins.kotlin.android)
    // Plugin khusus agar fitur Jetpack Compose (UI modern) bisa jalan
    alias(libs.plugins.kotlin.compose)
}

// --- BAGIAN 2: KONFIGURASI ANDROID ---
android {
    // ID unik aplikasi kamu (seperti KTP aplikasi)
    namespace = "com.example.belajarapi"

    // Versi Android SDK yang dipakai untuk menyusun (compile) kodingan ini
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        // ID yang dipakai saat upload ke PlayStore (harus unik sedunia)
        applicationId = "com.example.belajarapi"

        // Min SDK 24 = Aplikasi ini bisa jalan minimal di Android 7.0 (Nougat)
        // HP di bawah Android 7 tidak bisa install aplikasi ini
        minSdk = 24

        // Target SDK = Aplikasi ini dioptimalkan untuk fitur Android terbaru
        targetSdk = 36

        // Versi kode (untuk komputer/update sistem)
        versionCode = 1
        // Versi nama (yang dilihat user, misal "1.0 Beta")
        versionName = "1.0"

        // Alat untuk testing otomatis (abaikan dulu saat belajar awal)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Pengaturan build (Debug vs Release)
    buildTypes {
        release {
            // isMinifyEnabled = false artinya kode tidak dipadatkan (biar mudah dibaca errornya)
            // Kalau sudah mau rilis ke PlayStore, biasanya ini diubah jadi true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Versi Java yang dipakai (Kotlin berjalan di atas Java Virtual Machine)
    // Kita pakai versi 11 agar kompatibel dengan library modern
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    // Mengaktifkan fitur Jetpack Compose (UI tanpa XML)
    buildFeatures {
        compose = true
    }
}

// --- BAGIAN 3: DEPENDENCIES (BAHAN BAKU / LIBRARY) ---
// Di sini kita "belanja" alat-alat yang dibuat orang lain untuk dipakai di aplikasi kita
dependencies {

    // --- A. LIBRARY WAJIB ANDROID (CORE) ---
    // Jangan dihapus, ini pondasi agar HP Android mengerti kode kita
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom)) // BOM = Mengatur versi compose biar seragam
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview) // Untuk Preview di kanan layar
    implementation(libs.androidx.compose.material3) // Desain Material 3 (Google style)

    // --- B. LIBRARY TESTING (PENGUJIAN) ---
    // Dipakai untuk mengetes aplikasi secara otomatis (bukan prioritas saat belajar API)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ==========================================================
    // C. PERALATAN TEMPUR REST API (INI YANG PENTING UNTUKMU)
    // ==========================================================

    // 1. RETROFIT (Si Tukang Pos)
    // Bertugas membuat koneksi ke Internet (HTTP Client)
    // Dia yang mengetuk pintu server dan meminta data
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // 2. GSON CONVERTER (Si Penerjemah)
    // Data dari server bentuknya teks JSON (susah dibaca Kotlin)
    // Alat ini otomatis mengubah JSON menjadi "Data Class" Kotlin (Objek)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 3. COIL (Si Penampil Gambar)
    // Bertugas mendownload gambar dari URL dan menampilkannya di layar
    // Kita pakai Versi 2.6.0 (Versi Stabil untuk belajar)
    implementation("io.coil-kt:coil-compose:2.6.0")
    // CATATAN: Saya hapus baris "coil3-network" karena itu bentrok versi. 
    // Cukup satu baris di atas, Coil 2 sudah pintar cari internet sendiri.

    // 4. VIEWMODEL & LIFECYCLE (Si Manajer Data)
    // Bertugas menjaga data (List Pahlawan) agar tidak hilang saat HP diputar (Rotate)
    // Juga memisahkan logika bisnis dari tampilan UI (Clean Code)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}