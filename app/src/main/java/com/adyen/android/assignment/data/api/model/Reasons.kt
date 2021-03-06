package com.adyen.android.assignment.data.api.model

data class Reasons(
    val count: Int,
    val items: List<Item>
) {
    data class Item(
        val reasonName: String,
        val summary: String,
        val type: String
    )
}
