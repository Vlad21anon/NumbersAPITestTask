package com.ukadovlad21.numberstesttask.api

import com.ukadovlad21.numbersapitesttask.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(
                GsonConverterFactory
                    .create())
            .build()
    }
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
const val BASEURL = "http://numbersapi.com/"