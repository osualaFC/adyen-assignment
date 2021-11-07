package com.adyen.android.assignment.data.api

import com.adyen.android.assignment.data.api.model.Response
import com.adyen.android.assignment.data.api.model.VenueRecommendationsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface VenueService {

    @GET("venues/explore")
     fun getVenues(
        @QueryMap query: Map<String, String>
    ): Single<Response<VenueRecommendationsResponse>>
}