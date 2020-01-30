package com.example.nasatest.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemLink(
    @SerializedName("href")
    @Expose
    val image: String
) : Parcelable

