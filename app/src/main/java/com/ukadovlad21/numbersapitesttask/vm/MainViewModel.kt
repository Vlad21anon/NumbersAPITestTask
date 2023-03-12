package com.ukadovlad21.numbersapitesttask.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukadovlad21.numbersapitesttask.model.NumberData
import com.ukadovlad21.numbersapitesttask.repository.NumbersRepository
import com.ukadovlad21.numbersapitesttask.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NumbersRepository,
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<NumberData>> = MutableLiveData()


    fun getFactByNumber(int: Int): MutableLiveData<Resource<NumberData>> {
        viewModelScope.launch {
            repository.call(liveData) {
                repository.getByNumber(int.toString())
            }
        }
        return liveData
    }

    fun getRandomFact(): MutableLiveData<Resource<NumberData>> {
        viewModelScope.launch {
            repository.call(liveData) {
                repository.getRandom()
            }
        }
        return liveData
    }


    fun getAllNumbersDataLiveData(): LiveData<List<NumberData>> =
        repository.getAllNumbersDataLiveData()


    fun saveNumberData(item: NumberData): Job = viewModelScope.launch {
        repository.upsert(item)
    }
}