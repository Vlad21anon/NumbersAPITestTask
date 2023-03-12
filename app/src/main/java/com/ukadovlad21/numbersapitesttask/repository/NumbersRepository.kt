package com.ukadovlad21.numbersapitesttask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ukadovlad21.numbersapitesttask.db.NumbersDao
import com.ukadovlad21.numbersapitesttask.db.NumbersDatabase
import com.ukadovlad21.numbersapitesttask.model.NumberData
import com.ukadovlad21.numberstesttask.api.RetrofitInstance
import com.ukadovlad21.numbersapitesttask.usecase.CheckInternetStateUseCase
import com.ukadovlad21.numbersapitesttask.utils.Resource
import retrofit2.Response
import java.io.IOException

class NumbersRepository(
    private val checkInternetStateUseCase: CheckInternetStateUseCase,
    private val db: NumbersDatabase
) {

    fun getAllNumbersDataLiveData(): LiveData<List<NumberData>> =
        db.getNumbersDao().getAllNumbersDataLiveData()


    suspend fun upsert(item: NumberData) {
        db.getNumbersDao().upsert(item)
    }

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