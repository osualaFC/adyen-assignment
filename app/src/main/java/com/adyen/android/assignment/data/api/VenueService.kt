package com.adyen.android.assignment.data.api

import com.adyen.android.assignment.data.api.model.VenueRecommendationsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface VenueService {

    @GET("explore")
    suspend fun getVenues(
        @QueryMap query: Map<String, String>,
        @Query("ll") location: String,
        @Query("v") apiVersion: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): VenueRecommendationsResponse
}