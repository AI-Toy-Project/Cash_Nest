package com.example.cashnest

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/amount")
    fun getPrediction(): Call<Map<String, Int>>
}