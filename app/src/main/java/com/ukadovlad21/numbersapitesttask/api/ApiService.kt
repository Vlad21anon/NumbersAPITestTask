package com.ukadovlad21.numbersapitesttask.api

import com.ukadovlad21.numbersapitesttask.model.NumberData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{number}?json")
    suspend fun getByNumber(
        @Path("number") string: String,
    ): Response<NumberData>

    @GET("random/math?json")
    suspend fun getRandom(): Response<NumberData>

}