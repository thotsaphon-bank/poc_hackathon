package com.example.pochackathon.preset

import android.content.Context
import com.example.pochackathon.R
import com.example.pochackathon.model.Product
import com.example.pochackathon.model.ProductSku
import com.example.pochackathon.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type


object AppData {
    var users = listOf(
        User(1.toString(), true),
        User(2.toString(), false),
        User(3.toString(), false),
        User(4.toString(), false),
        User(5.toString(), false)
    )

    private var carts = users.associate { Pair(it.id, listOf<Product>()) }
    private val currentUser get() = users.first { it.isSelected }
    val cart get() = carts[currentUser.id] ?: emptyList()

    fun changeSelectUser(user: User) {
        users = users.map { it.copy(isSelected = user.id == it.id) }
    }

    fun removeProduct(product: Product) {
        carts = carts.map { (userId, products) ->
            if (userId != currentUser.id) Pair(userId, products)
            else Pair(userId, products.filterOutLast(product))
        }.toMap()
    }

    private fun <T> List<T>.filterOutLast(element: T): List<T> {
        return reversed().toMutableList().also { it.remove(element) }.reversed()
    }

    fun checkout() {
        carts = carts.map { (userId, products) ->
            if (userId != currentUser.id) Pair(userId, products)
            else Pair(userId, emptyList())
        }.toMap()
    }

    fun getSku(context: Context, barcode: String): String? {
        val listProductSkuType: Type = object : TypeToken<List<ProductSku>>() {}.type
        val rawResource = InputStreamReader(context.resources.openRawResource(R.raw.product_sku))
        val products = Gson().fromJson<List<ProductSku>>(rawResource, listProductSkuType)

        return products.find { it.barcode == barcode }?.sku
    }

    fun addProduct(product: Product) {
        carts = carts.map { (userId, products) ->
            if (userId != currentUser.id) Pair(userId, products)
            else Pair(userId, products.reversed().toMutableList().also { it.add(product) }.reversed())
        }.toMap()
    }
}
