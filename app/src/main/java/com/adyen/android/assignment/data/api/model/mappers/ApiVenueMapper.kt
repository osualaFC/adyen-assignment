package com.adyen.android.assignment.data.api.model.mappers

import com.adyen.android.assignment.data.api.model.RecommendedItem
import com.adyen.android.assignment.domain.model.Venue
import javax.inject.Inject

class ApiVenueMapper @Inject constructor() : ApiMapper<RecommendedItem, Venue> {

    override fun mapData(apiEntity: RecommendedItem): Venue {
        return Venue(
            id = apiEntity.venue.id,
            name = apiEntity.venue.name,
            distance = apiEntity.venue.location.distance,
            longitude = apiEntity.venue.location.lng,
            latitude = apiEntity.venue.location.lat,
            address = apiEntity.venue.location.formattedAddress
        )
    }
}