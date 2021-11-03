package com.adyen.android.assignment.data.cache

import com.adyen.android.assignment.data.cache.dao.VenueDao
import com.adyen.android.assignment.data.cache.model.CacheVenue
import io.reactivex.Flowable
import javax.inject.Inject

class RoomCache @Inject constructor(
    private val dao : VenueDao
) : Cache {
    override fun getVenues(): Flowable<List<CacheVenue>> {
        return dao.getVenues()
    }

    override suspend fun storeVenues(venues: List<CacheVenue>) {
       return dao.insertVenues(venues)
    }
}