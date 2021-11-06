package com.adyen.android.assignment.data.api.model

data class Response<T>(
    val meta: Meta,
    val response: T
)