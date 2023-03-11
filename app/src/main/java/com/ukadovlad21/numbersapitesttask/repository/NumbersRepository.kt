package com.ukadovlad21.numbersapitesttask.repository

import androidx.lifecycle.MutableLiveData
import com.ukadovlad21.numbersapitesttask.model.NumberData
import com.ukadovlad21.numberstesttask.api.RetrofitInstance
import com.ukadovlad21.numbersapitesttask.usecase.CheckInternetStateUseCase
import com.ukadovlad21.numbersapitesttask.utils.Resource
import retrofit2.Response
import java.io.IOException

class NumbersRepository(
    private val checkInternetStateUseCase: CheckInternetStateUseCase,
) {


    suspend fun getByNumber(string: String): Response<NumberData> {
        return RetrofitInstance.api.getByNumber(string)
    }

    suspend fun getRandom(): Response<NumberData> {
        return RetrofitInstance.api.getRandom()
    }


    suspend fun <T> call(
        liveData: MutableLiveData<Resource<T>>,
        getResponse: suspend () -> Response<T>,
    ) {
        liveData.postValue(Resource.Loading)
        try {
            if (checkInternetStateUseCase.isInternetAvailable()) {
                liveData.postValue(handleResponse(getResponse()))
            } else {
                liveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> liveData.postValue(Resource.Error("Network failure"))
                else -> liveData.postValue(Resource.Error("Conversion Error: $t"))
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let { currencyResponse ->
                return Resource.Success(currencyResponse)
            }
        }

        return Resource.Error(response.message())
    }


}