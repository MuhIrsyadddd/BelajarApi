package com.example.belajarapi.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Object: Artinya SINGLETON.
// Android cuma akan membuat object ini SATU KALI saja selama aplikasi jalan.
// Hemat memori, gak perlu bikin objek baru tiap kali mau request.
object RetrofitClient {

    // Alamat utama server.
    // PENTING: Wajib diakhiri dengan garis miring (/)
    private const val BASE_URL = "https://my-json-server.typicode.com/fadhlialfarisi46/"

    // by lazy: Fitur pintar Kotlin.
    // Variabel 'instance' ini TIDAK AKAN DIBUAT saat aplikasi baru dibuka.
    // Dia baru dibuat saat PERTAMA KALI dipanggil/dipakai.
    // Tujuannya agar aplikasi cepat terbuka (startup time ngebut).
    val instance: ApiService by lazy {

        // Membangun mesin Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Set alamat tujuan

            // .addConverterFactory: Menambahkan "Penerjemah".
            // Server ngomong bahasa JSON -> Gson mengubahnya jadi bahasa Kotlin (Object).
            .addConverterFactory(GsonConverterFactory.create())

            .build() // Rakit mesinnya!

        // Menghubungkan mesin Retrofit dengan Buku Menu (Interface) kita.
        retrofit.create(ApiService::class.java)
    }
}