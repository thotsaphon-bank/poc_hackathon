package com.example.pochackathon.preset

import com.example.pochackathon.model.Price
import com.example.pochackathon.model.Product
import com.example.pochackathon.model.User

object AppData {
    private val coke = Product(id = "11468", sku = "16576918", name = "COKE ZERO 325 ML.", imageUrl = "https://publish-p33706-e156582.adobeaemcloud.com/content/dam/aem-cplotusonlinecommerce-project/th/images/magento/catalog/product/074/8851959132074/ShotType1_540x540.jpg", Price(15.0, 0.0, 15.0))
    private val handSoap = Product(id = "125744", sku = "20471904", name = "KIREI KIREI FOAMING HAND SOAP 250 ML.BOT", imageUrl = "https://publish-p33706-e156582.adobeaemcloud.com/content/dam/aem-cplotusonlinecommerce-project/th/images/magento/catalog/product/238/8850002020238/ShotType1_540x540.jpg", Price(75.0, 10.0, 65.0))

    var users = listOf(
        User(1.toString(), true),
        User(2.toString(), false),
        User(3.toString(), false),
        User(4.toString(), false),
        User(5.toString(), false)
    )
    var carts = listOf(
        Pair("1", listOf(coke, coke, handSoap, handSoap, coke)),
        Pair("2", listOf(handSoap, coke)),
        Pair("5", listOf(coke, handSoap, coke))
    ).toMap()

    private val currentUser get() = users.first { it.isSelected }
    val cart get() = carts[currentUser.id] ?: emptyList()

    fun changeSelectUser(user: User) {
        users = users.map { it.copy(isSelected = user.id == it.id) }
    }

    fun removeProduct(product: Product) {
        carts = carts.map { (userId, products) ->
            if (userId != currentUser.id) Pair(userId, products)
            else Pair(userId, products.filterOut(product))
        }.toMap()
    }

    private fun <T> List<T>.filterOut(element: T): List<T> {
        return toMutableList().also { it.remove(element) }.toList()
    }

    fun checkout() {
        carts = carts.map { (userId, products) ->
            if (userId != currentUser.id) Pair(userId, products)
            else Pair(userId, emptyList())
        }.toMap()
    }
}