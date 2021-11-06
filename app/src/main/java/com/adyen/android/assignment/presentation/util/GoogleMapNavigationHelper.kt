package com.adyen.android.assignment.presentation.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

object GoogleMapNavigationHelper {

    fun navigateUsingMaps(
        context: Context,
        latitude: Double,
        longitude: Double,
        walk: Boolean = false
    ) {
        val mapIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "google.navigation:q=$latitude,$longitude&mode=${
                    if (walk) "w" else "d"
                }"
            )
            setPackage("com.google.android.apps.maps")
        }

        try {
            context.startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(getPlayStoreIntent())
        }
    }

    private fun getPlayStoreIntent() = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://play.google.com/store/apps/" +
                    "details?id=com.google.android.apps.maps"
        )
        setPackage("com.android.vending")
    }
}