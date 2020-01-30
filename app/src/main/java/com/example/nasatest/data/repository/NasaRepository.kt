package com.example.nasatest.data.repository

import com.example.nasatest.data.model.SearchResponse
import io.reactivex.Single

interface NasaRepository {
    fun fetchItems():Single<SearchResponse>

}
