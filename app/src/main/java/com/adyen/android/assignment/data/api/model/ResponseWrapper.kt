package com.adyen.android.assignment.data.api.model

data class ResponseWrapper<T>(
    val meta: Meta,
    val response: T
)
