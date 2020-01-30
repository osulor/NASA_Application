package com.example.nasatest.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasatest.data.repository.NasaRepository
import com.example.nasatest.viewmodel.MainViewModel
import io.reactivex.disposables.CompositeDisposable

class MainViewModelFactory constructor(private val nasaRepository: NasaRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(nasaRepository, CompositeDisposable()) as T
    }

}