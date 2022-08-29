package com.example.pochackathon.source

import com.example.pochackathon.model.Product
import com.example.pochackathon.model.GetProductResponse
import com.example.pochackathon.model.toProduct
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Api {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ppe-api.lotuss.com/proc/product/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient().newBuilder().addInterceptor {
            val originalRequest = it.request()
            val newRequestBuilder = originalRequest.newBuilder()
                .header("X-Correlation-Id", "9235a8a0-a7c8-11eb-9a0d-38f9d3e90b55")
                .header("accept-language", "en")
                .header("Authorization", "Basic ZDgzNWI1OTMyY2QyNGEyNTg5ZTAzZWVjZmNkYTg5NjA6YzUxMEVEMEJkZDNjNDBhMkFDMzViOGI1ODY4OGIyMTg=")
                .build()

            return@addInterceptor it.proceed(newRequestBuilder)
        }.build())
        .build()

    private val api get() = retrofit.create(ApiService::class.java)

    suspend fun getProduct(sku: String): Product {
        return api.getProduct("thailand_hy", sku).await().toProduct()
    }
}

interface ApiService {
    @GET("products/details")
    fun getProduct(
        @Query("websiteCode") websiteCode: String,
        @Query("sku") sku: String
    ): Call<GetProductResponse>
}
