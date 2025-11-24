package com.example.belajarapi.Data

data class PahlawanResponse(
	val pahlawanResponse: List<PahlawanResponseItem?>? = null
)

data class PahlawanResponseItem(
	val urlWiki: String? = null,
	val name: String? = null,
	val id: Int? = null,
	val deskripsi: String? = null,
	val imageAsset: String? = null
)

