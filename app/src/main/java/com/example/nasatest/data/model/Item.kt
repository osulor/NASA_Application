package com.example.nasatest.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    @SerializedName("data")
    @Expose
    val data: List<ItemData>,
    @SerializedName("links")
    @Expose
    val links: List<ItemLink>
) : Parcelable
