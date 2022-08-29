package com.example.pochackathon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val sku: String,
    val name: String,
    val imageUrl: String,
    val price: Price
) : Parcelable

@Parcelize
data class Price(
    val original: Double,
    val discount: Double,
    val final: Double
) : Parcelable

fun GetProductResponse.toProduct() = with(data) {
    Product(
        id = id,
        sku = sku,
        name = name,
        imageUrl = images.firstOrNull()?.url ?: "",
        price = with(price.minimumPrice) {
            Price(regularPrice.value, discount.value, finalPrice.value)
        }
    )
}
