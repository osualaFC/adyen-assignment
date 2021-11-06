package com.adyen.android.assignment.domain.model

data class Venue(
    val id: String,
    val name: String,
    val distance: Int,
    val longitude: Double,
    val latitude: Double,
    val address: List<String>?
)
