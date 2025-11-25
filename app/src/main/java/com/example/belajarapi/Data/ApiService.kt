package com.example.belajarapi.Data

import retrofit2.http.GET

// Interface: Kita cuma mendefinisikan fungsi, tanpa isinya.
// Retrofit yang akan otomatis membuat isinya nanti.
interface ApiService {

    // @GET: Metode HTTP. Artinya kita mau MENGAMBIL data (Read).
    // ("..."): Ini adalah ENDPOINT. Alamat spesifik di dalam server.
    // Jadi kalau Base URL ibarat "Nama Mall", Endpoint adalah "Nama Toko"-nya.
    @GET("free-api-hero/pahlawan")

    // suspend: Wajib pakai ini jika menggunakan Coroutines.
    // Artinya: "Fungsi ini berat (ambil data internet), jadi tolong jalankan
    // di latar belakang (background) dan jangan bikin layar HP macet (freeze)."
    suspend fun getPahlawan(): List<PahlawanResponseItem>
    // Outputnya berupa LIST (Daftar) dari PahlawanResponseItem.
}