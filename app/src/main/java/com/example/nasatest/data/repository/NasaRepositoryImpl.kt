package com.example.nasatest.data.repository

import com.example.nasatest.data.model.SearchResponse
import com.example.nasatest.data.remote.Webservices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class NasaRepositoryImpl(private val webservices: Webservices) : NasaRepository {
    override fun fetchItems(): Single<SearchResponse> {
        return webservices.fetchItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}