package com.example.cashnest

import retrofit2.Response
import retrofit2.http.GET

interface ApiService2 {
    @GET("/predict") //바꾸기
    suspend fun getPrediction(): Response<PredictionData>
}