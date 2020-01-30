package com.example.nasatest.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchCollection(
    @SerializedName("items")
    @Expose
    val items: List<Item>
) : Parcelable
