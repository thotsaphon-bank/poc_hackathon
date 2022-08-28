package com.example.pochackathon.screen.cart

import com.airbnb.epoxy.Typed2EpoxyController
import com.example.pochackathon.model.Product
import com.example.pochackathon.product

class CartController : Typed2EpoxyController<List<Product>, CartController.Listener>() {
    interface Listener {
        fun removeProduct(product: Product)
    }

    override fun buildModels(products: List<Product>?, listener: Listener?) {
        products
            ?.groupBy { it.id }
            ?.forEach { (productId, products) ->
                product {
                    val product = products.first()
                    val productsFinalPrice = products.sumOf { it.price.final }

                    id(productId)
                    product(product)
                    productCount(products.count().toString() + " ชิ้น")
                    productOriginalPrice("ราคาเดิม ${product.price.original} บาท/ชิ้น")
                    productDiscountPrice("ส่วนลด ${product.price.discount} บาท/ชิ้น")
                    productsFinalPrice("รวมทั้งหมด $productsFinalPrice บาท")
                    onClickRemove { _ -> listener?.removeProduct(product) }
                }
            }
    }
}
