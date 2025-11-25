package com.example.belajarapi.Data

// Data Class: Jenis class khusus di Kotlin yang tujuannya CUMA buat nampung data.
// Kita gak perlu tulis getter/setter manual (sudah otomatis).

// Class Pembungkus (Wrapper)
// Kadang JSON ada pembungkus luarnya sebelum masuk ke daftar isinya.
data class PahlawanResponse(
	// Tanda tanya (?) artinya NULLABLE.
	// Jaga-jaga kalau server error dan tidak kirim data, aplikasi GAK CRASH,
	// tapi cuma dianggap null (kosong).
	val pahlawanResponse: List<PahlawanResponseItem?>? = null
)

// Ini adalah ITEM utamanya (Satu Pahlawan = Satu Object ini)
// PENTING: Nama variabel (val) HARUS SAMA PERSIS dengan Key di JSON server.
data class PahlawanResponseItem(

	// Misal di JSON: "urlWiki": "...", maka di sini harus val urlWiki
	val urlWiki: String? = null,

	val name: String? = null,

	val id: Int? = null, // Tipe data Int untuk angka bulat

	val deskripsi: String? = null, // String untuk teks panjang

	val imageAsset: String? = null // Ini link gambar yang nanti dibaca Coil
)