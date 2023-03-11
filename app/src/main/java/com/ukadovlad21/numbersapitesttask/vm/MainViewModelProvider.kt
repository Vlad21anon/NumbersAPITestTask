package com.ukadovlad21.numbersapitesttask.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ukadovlad21.numbersapitesttask.repository.NumbersRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelProvider(
    private val repository: NumbersRepository,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}