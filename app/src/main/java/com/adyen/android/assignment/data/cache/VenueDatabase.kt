package com.adyen.android.assignment.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adyen.android.assignment.data.cache.dao.VenueDao
import com.adyen.android.assignment.data.cache.model.CacheVenue
import com.adyen.android.assignment.data.cache.model.Converters

@Database(
    entities = [CacheVenue::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class VenueDatabase : RoomDatabase() {
    abstract fun venueDao() : VenueDao
}