package com.avgust.final_task.data.model


import  android.os.Parcelable
import com.avgust.final_task.data.model.Rating
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProductsItem(
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: @RawValue Rating?,
    @SerializedName("title")
    val title: String?
): Parcelable