package com.adyen.android.assignment.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.adyen.android.assignment.domain.model.Venue

@Entity(tableName = "venue")
data class CacheVenue(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val distance: Int,
    val longitude: Double,
    val latitude: Double,
    val address: List<String>
) {
    fun fromDomain(venue: Venue): CacheVenue {
        return CacheVenue(
            id = venue.id,
            name = venue.name,
            distance = venue.distance,
            longitude = venue.longitude,
            latitude = venue.latitude,
            address = venue.address
        )
    }

    fun toDomain(venue: CacheVenue): Venue {
        return Venue(
            id = venue.id,
            name = venue.name,
            distance = venue.distance,
            longitude = venue.longitude,
            latitude = venue.latitude,
            address = venue.address
        )
    }
}