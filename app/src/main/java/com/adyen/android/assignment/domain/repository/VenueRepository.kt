package com.adyen.android.assignment.domain.repository

import com.adyen.android.assignment.data.api.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.data.api.VenueService
import com.adyen.android.assignment.data.api.model.Response
import com.adyen.android.assignment.data.api.model.VenueRecommendationsResponse
import io.reactivex.Single
import javax.inject.Inject


class VenueRepository @Inject constructor(
    private val service: VenueService
) {
    fun getVenues(latitude : Double, longitude: Double): Single<Response<VenueRecommendationsResponse>> {
        val request = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(latitude, longitude)
        return service.getVenues(request.build())
    }

}