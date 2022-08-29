package com.example.pochackathon.model

import com.google.gson.annotations.SerializedName

data class GetProductResponse(
    @SerializedName("data") val data: ProductResponse
) {
    data class ProductResponse(
        @SerializedName("id") val id: String,
        @SerializedName("sku") val sku: String,
        @SerializedName("name") val name: String,
        @SerializedName("mediaGallery") val images: List<MediaGallery>,
        @SerializedName("priceRange") val price: PriceRange
    ) {
        data class MediaGallery(@SerializedName("url") val url: String)

        data class PriceRange(@SerializedName("minimumPrice") val minimumPrice: MinimumPrice) {
            data class MinimumPrice(
                @SerializedName("regularPrice") val regularPrice: RegularPrice,
                @SerializedName("finalPrice") val finalPrice: FinalPrice,
                @SerializedName("discount") val discount: Discount
            ) {
                data class RegularPrice(@SerializedName("value") val value: Double)

                data class FinalPrice(@SerializedName("value") val value: Double)

                data class Discount(@SerializedName("amountOff") val value: Double)
            }
        }
    }
}
