package com.example.beautynetwork.data.model

data class Product(
    val id: String? = null,
    val brand: String? = null,
    val name: String? = null,
    val price: String? = null,
    val price_sign: Any? = null,
    val currency: Any? = null,
    val image_link: String? = null,
    val product_link: String? = null,
    val website_link: String? = null,
    val description: String? = null,
    val rating: Double? = null,
    val category: String? = null,
    val product_type: String? = null,
    val tag_list: List<Any>? = null,
    val product_colors: ArrayList<ProductColor>? = null
)
