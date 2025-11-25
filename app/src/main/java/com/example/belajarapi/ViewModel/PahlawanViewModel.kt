package com.example.belajarapi.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belajarapi.Data.PahlawanResponseItem
import com.example.belajarapi.Data.RetrofitClient
import kotlinx.coroutines.launch

// --- 1. STATE (STATUS LAYAR) ---
// Sealed Interface: Mirip seperti pilihan ganda (Multiple Choice).
// Status aplikasi HANYA BOLEH salah satu dari 3 ini, tidak bisa yang lain.
sealed interface PahlawanUiState {

    // Kondisi 1: Sedang memuat (Loading).
    // Nanti di UI, kalau statusnya ini -> Tampilkan Spinner berputar.
    object Loading : PahlawanUiState

    // Kondisi 2: Berhasil (Success).
    // Membawa 'kado' berupa data (List Pahlawan) dari server.
    // Nanti di UI, kalau statusnya ini -> Tampilkan Daftar Pahlawan.
    data class Success(val data: List<PahlawanResponseItem>) : PahlawanUiState

    // Kondisi 3: Gagal (Error).
    // Nanti di UI, kalau statusnya ini -> Tampilkan pesan "Cek Internet".
    object Error : PahlawanUiState
}

// --- 2. VIEWMODEL (OTAK APLIKASI) ---
// Mewarisi (extends) ViewModel() agar data selamat saat layar diputar (Rotate).
class PahlawanViewModel : ViewModel() {

    // --- VARIABEL PEMANTAU (STATE HOLDER) ---
    // Ini adalah variabel SAKTI.
    // Kenapa sakti? Karena pakai 'mutableStateOf'.
    // Artinya: Setiap kali nilai variabel ini berubah, Layar (UI) akan
    // OTOMATIS digambar ulang (Recomposition) menyesuaikan status baru.
    var pahlawanUiState: PahlawanUiState by mutableStateOf(PahlawanUiState.Loading)
        private set // UI boleh BACA, tapi cuma ViewModel yang boleh TULIS/GANTI nilai.

    // --- BLOK INIT (STARTER) ---
    // Kode di dalam sini akan jalan OTOMATIS begitu aplikasi dibuka
    // (saat ViewModel pertama kali dibuat).
    init {
        getPahlawan() // Langsung panggil fungsi ambil data.
    }

    // --- FUNGSI AMBIL DATA ---
    fun getPahlawan() {
        // viewModelScope.launch: Membuka "Dunia Lain" (Background Thread).
        // Kita tidak boleh download data di jalan utama (Main Thread) karena bikin HP macet.
        // Coroutine ini akan otomatis mati kalau aplikasi ditutup (aman dari memory leak).
        viewModelScope.launch {

            // 1. Ubah status jadi Loading dulu (biar spinner muncul)
            pahlawanUiState = PahlawanUiState.Loading

            try {
                // 2. COBA (Try) ambil data ke server lewat Retrofit
                // Kode ini akan 'pause' sebentar sampai data dari internet datang (suspend).
                val result = RetrofitClient.instance.getPahlawan()

                // 3. Jika berhasil, masukkan data ke status Success
                pahlawanUiState = PahlawanUiState.Success(result)

            } catch (e: Exception) {
                // 4. TANGKAP (Catch) jika ada error
                // Misal: HP gak ada sinyal, Server mati, atau kuota habis.
                // Jangan biarkan aplikasi Crash, tapi ubah status jadi Error.
                pahlawanUiState = PahlawanUiState.Error

                // Cetak error di Logcat buat programmer ngecek (Debugging)
                e.printStackTrace()
            }
        }
    }
}