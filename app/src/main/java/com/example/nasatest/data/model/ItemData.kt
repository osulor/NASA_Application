package com.example.nasatest.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemData(
    @SerializedName("date_created")
    @Expose
    val createdDate:String,
    @SerializedName("photographer")
    @Expose
    val photographer:String,
    @SerializedName("title")
    @Expose
    val title:String
):Parcelable