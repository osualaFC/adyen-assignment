package com.adyen.android.assignment.data.repository

import com.adyen.android.assignment.domain.model.Venue
import io.reactivex.Flowable

interface VenueRepository {
    fun getVenues(): Flowable<List<Venue>>

    suspend fun storeVenues(venues : List<Venue>)
}