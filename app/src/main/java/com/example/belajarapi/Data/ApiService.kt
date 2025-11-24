package com.example.belajarapi.Data

import retrofit2.http.GET

interface ApiService{
    @GET ("free-api-hero/pahlawan")
    suspend fun getPahlawan(): List<PahlawanResponseItem>
}