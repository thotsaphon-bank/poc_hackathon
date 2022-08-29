package com.example.pochackathon.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductSku(
    @SerializedName("Item_number") val sku: String,
    @SerializedName("UPC") val barcode: String
) : Parcelable
