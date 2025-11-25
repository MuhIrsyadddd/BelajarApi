package com.example.belajarapi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Import Coil 2 (Versi Stabil)
import coil.compose.AsyncImage
import coil.request.ImageRequest

import com.example.belajarapi.Data.PahlawanResponseItem
import com.example.belajarapi.ViewModel.PahlawanUiState
import com.example.belajarapi.ViewModel.PahlawanViewModel
import com.example.belajarapi.ui.theme.BelajarApiTheme

// ComponentActivity adalah kelas dasar untuk layar Android modern.
class MainActivity : ComponentActivity() {

    // onCreate: Fungsi yang PERTAMA KALI dijalankan saat aplikasi dibuka.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: Titik mulai Jetpack Compose.
        // Di sini kita mengubah kode Kotlin menjadi tampilan layar (UI).
        setContent {

            // BelajarApiTheme: Membungkus aplikasi dengan tema (Warna, Font) yang sudah ditentukan.
            BelajarApiTheme {

                // Surface: Ibarat "Kanvas" atau latar belakang kertas putih.
                // Modifier.fillMaxSize(): Memenuhi seluruh layar HP.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Panggil fungsi layar utama kita di sini
                    MainScreen()
                }
            }
        }
    }
}
//```
//
//---
//
//### Bagian 2: Main Screen (Logic & State Management)
//Di sini terjadi "Logika Berpikir" aplikasi. Apakah data sedang dimuat? Sudah sukses? Atau Error?
//
//```kotlin
// @Composable: Tanda bahwa fungsi ini adalah UI (Tampilan), bukan fungsi matematika biasa.
@Composable
fun MainScreen(
    // Injeksi ViewModel: Kita panggil "Otak" aplikasi (PahlawanViewModel) ke sini.
    // viewModel() otomatis membuatkan atau mengambil instance ViewModel yang sudah ada.
    viewModel: PahlawanViewModel = viewModel()
) {
    // Observasi State: Kita mengintip variabel 'pahlawanUiState' yang ada di ViewModel.
    // Setiap kali variabel ini berubah (Loading -> Success), layar akan digambar ulang (Recomposition).
    val uiState = viewModel.pahlawanUiState

    // when: Pengganti if-else yang lebih rapi (Switch Case).
    // Kita cek: "Sekarang statusnya apa ya?"
    when (uiState) {

        // KASUS 1: SEDANG LOADING
        is PahlawanUiState.Loading -> {
            // Box dengan contentAlignment Center = Menaruh benda tepat di tengah layar.
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Tampilkan animasi lingkaran berputar
                CircularProgressIndicator()
            }
        }

        // KASUS 2: SUKSES (Data Berhasil Didapat)
        is PahlawanUiState.Success -> {
            // LazyColumn: Pengganti RecyclerView.
            // Ini adalah List yang "Malas" (Lazy), dia hanya merender item yang terlihat di layar saja.
            // Sangat hemat memori untuk data yang banyak (ribuan).
            LazyColumn {
                // items(uiState.data): Mengambil List Pahlawan dari server.
                // Untuk setiap 'pahlawan' di dalam list, buatkan satu 'PahlawanItem'.
                items(uiState.data) { pahlawan ->
                    PahlawanItem(pahlawan = pahlawan)
                }
            }
        }

        // KASUS 3: ERROR (Internet Mati / Server Down)
        is PahlawanUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Gagal memuat data. Cek internet kamu!")
            }
        }
    }
}
//```
//
//---
//
//### Bagian 3: Pahlawan Item (Desain Kartu)
//Bagian ini mengatur bagaimana **SATU** kotak pahlawan terlihat.
//
//```kotlin
@Composable
fun PahlawanItem(pahlawan: PahlawanResponseItem) {
    // Card: Kotak dengan bayangan (elevation) dan sudut melengkung.
    Card(
        modifier = Modifier
            .fillMaxWidth() // Lebar memenuhi layar samping ke samping
            .padding(8.dp), // Jarak antar kartu
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Row: Menyusun elemen secara HORIZONTAL (Kiri ke Kanan).
        // [ Gambar ] --jarak-- [ Teks ]
        Row(
            modifier = Modifier.padding(16.dp), // Jarak isi kartu dari pinggir kartu
            verticalAlignment = Alignment.CenterVertically // Biar teks pas di tengah tinggi gambar
        ) {

            // Mengambil Context (Dibutuhkan Coil untuk akses file system/internet)
            val context = LocalContext.current

            // 1. KOMPONEN GAMBAR (COIL 2)
            AsyncImage(
                // model: Konfigurasi request gambar
                model = ImageRequest.Builder(context)
                    .data(pahlawan.imageAsset) // URL Gambar dari API

                    // --- HEADER "KTP PALSU" ---
                    // Server sering memblokir akses dari aplikasi (Bot).
                    // Header ini menipu server agar mengira kita adalah Browser Chrome di Windows.
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")

                    .crossfade(true) // Efek memudar halus saat gambar muncul
                    .build(),

                contentDescription = null, // Deskripsi untuk tunanetra (bisa diisi nama pahlawan)
                modifier = Modifier.size(80.dp), // Ukuran gambar fix 80x80 dp
                contentScale = ContentScale.Crop, // Gambar dipotong biar pas kotak (tidak gepeng)

                // Debugging: Melihat log di Logcat kalau loading/error
                onLoading = { Log.d("CekGambar", "Loading: ${pahlawan.name}") },
                onError = { Log.e("CekGambar", "Error: ${pahlawan.name}") }
            )

            // Spacer: Memberi jarak kosong horizontal (16dp) antara Gambar dan Teks
            Spacer(modifier = Modifier.width(16.dp))

            // 2. KOMPONEN TEKS
            // Column: Menyusun elemen secara VERTIKAL (Atas ke Bawah).
            // [ Nama Pahlawan ]
            // [ Deskripsi.... ]
            Column {
                Text(
                    text = pahlawan.name ?: "Tanpa Nama", // Elvis Operator (?:) kalau null, pakai teks default
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = pahlawan.deskripsi ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2 // Membatasi cuma boleh 2 baris, sisanya titik-titik (...)
                )
            }
        }
    }
}
//```
//
//---
//
//### Bagian 4: Preview (Alat Bantu Developer)
//Kode ini tidak akan tampil di HP pengguna, cuma buat kita melihat desain di Android Studio tanpa harus Run Emulator.
//
//```kotlin
@Preview(showBackground = true)
@Composable
fun PahlawanItemPreview() {
    // Kita buat data palsu (Dummy) karena Preview tidak bisa akses internet.
    val pahlawanContoh = PahlawanResponseItem(
        name = "Pahlawan Pratinjau",
        deskripsi = "Ini adalah deskripsi singkat untuk keperluan pratinjau.",
        imageAsset = ""
    )

    BelajarApiTheme {
        PahlawanItem(pahlawan = pahlawanContoh)
    }
}