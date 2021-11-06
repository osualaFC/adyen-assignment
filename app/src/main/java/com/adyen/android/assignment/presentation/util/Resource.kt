package com.adyen.android.assignment.presentation.util

class Resource<Any> private constructor(val status: Status, val data: Any?, val failure: Throwable?) {
    companion object {

        fun <Any> success(data: Any): Resource<Any> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <Any> error(failure: Throwable): Resource<Any> {
            return Resource(Status.ERROR, null, failure)
        }

        fun <Any> loading(): Resource<Any> {
            return Resource(Status.LOADING, null, null)
        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
