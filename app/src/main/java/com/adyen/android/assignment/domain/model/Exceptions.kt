package com.adyen.android.assignment.domain.model

import java.io.IOException

class VenueNotFoundException: Exception()

class NetworkUnavailableException : IOException()

class NetworkException: Exception()