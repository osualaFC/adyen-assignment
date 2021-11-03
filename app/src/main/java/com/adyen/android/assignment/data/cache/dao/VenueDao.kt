package com.adyen.android.assignment.data.cache.dao

import androidx.room.OnConflictStrategy
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import androidx.room.Transaction
import com.adyen.android.assignment.data.cache.model.CacheVenue
import io.reactivex.Flowable

@Dao
interface VenueDao {

    @Transaction
    @Query("SELECT * FROM venue")
    fun getVenues(): Flowable<List<CacheVenue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenues(venues: List<CacheVenue>)
}