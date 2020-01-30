package com.example.nasatest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasatest.data.model.Item
import com.example.nasatest.data.repository.NasaRepository
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

class MainViewModel constructor(
    private val nasaRepository: NasaRepository,
    private val disposable: CompositeDisposable
) : ViewModel() {

    fun fetchItems() {
        loadingState.value = LoadingState.LOADING
        disposable.add(
            nasaRepository.fetchItems().subscribe({
                //In case of empty list of items show error message
                if (it.collection.items.isEmpty()) {
                    errorMessage.value = "No Photo Found"
                    loadingState.value = LoadingState.ERROR
                } else {
                    items.value = it.collection.items
                    loadingState.value = LoadingState.SUCCESS
                }
            }, {
                when (it) {
                    is UnknownHostException -> errorMessage.value = "Network Error Occurred"
                    else -> errorMessage.value = it.localizedMessage
                }

                loadingState.value = LoadingState.ERROR
            }
            )
        )
    }

    val items: MutableLiveData<List<Item>> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val loadingState = MutableLiveData<LoadingState>()

    enum class LoadingState {
        LOADING,
        SUCCESS,
        ERROR
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}