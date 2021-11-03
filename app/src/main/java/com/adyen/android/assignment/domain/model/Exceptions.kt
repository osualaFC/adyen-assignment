package com.adyen.android.assignment.domain.model

import java.io.IOException

class VenueNotFoundException(message: String): Exception(message)

class CategoryNotFoundException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

class NetworkException(message: String): Exception(message)