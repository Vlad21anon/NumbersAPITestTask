package com.ukadovlad21.numbersapitesttask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ukadovlad21.numbersapitesttask.R
import com.ukadovlad21.numbersapitesttask.repository.NumbersRepository
import com.ukadovlad21.numbersapitesttask.usecase.CheckInternetStateUseCase
import com.ukadovlad21.numbersapitesttask.vm.MainViewModel
import com.ukadovlad21.numbersapitesttask.vm.MainViewModelProvider

class MainActivity : AppCompatActivity() {

    private val checkInternetStateUseCase: CheckInternetStateUseCase by lazy {
        CheckInternetStateUseCase(application)
    }

    private var repository: NumbersRepository? = null

    val mainViewModel: MainViewModel by viewModels {
        MainViewModelProvider(repository!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = NumbersRepository(checkInternetStateUseCase)
        setContentView(R.layout.activity_main)
    }
}