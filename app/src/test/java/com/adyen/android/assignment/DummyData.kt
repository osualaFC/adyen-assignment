package com.adyen.android.assignment

import androidx.annotation.VisibleForTesting
import com.adyen.android.assignment.data.api.model.Response
import com.adyen.android.assignment.data.api.model.VenueRecommendationsResponse

@Suppress("UNCHECKED_CAST")
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DummyData {

    var venueResponse = venueResponse()

    private fun venueResponse() : Response<VenueRecommendationsResponse> {
        return GsonUtil.fromJson(DependencyProvider.getResponseFromJson("explore-response"),
            Response::class.java) as Response<VenueRecommendationsResponse>
    }
}