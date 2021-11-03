package com.adyen.android.assignment.data.cache

import com.adyen.android.assignment.data.cache.model.CacheVenue
import io.reactivex.Flowable

interface Cache {
    fun getVenues(): Flowable<List<CacheVenue>>

    suspend fun storeVenues(venues : List<CacheVenue>)
}