package com.adyen.android.assignment.presentation.util

import com.adyen.android.assignment.domain.model.NetworkException
import com.adyen.android.assignment.domain.model.NetworkUnavailableException
import com.adyen.android.assignment.domain.model.VenueNotFoundException
import java.io.IOException
import kotlin.Exception

object ExceptionHelper {

    fun getException(
        throwable: Throwable
    ) : Exception {
        var exception : Exception = NetworkException()
        if (throwable is NetworkException) {
           exception =  NetworkException()
        }
        else if (throwable is IOException) {
            exception = NetworkUnavailableException()
        }
        else if (throwable is VenueNotFoundException) {
            exception = VenueNotFoundException()
        }

        return exception
    }
}