package com.adyen.android.assignment.data.api

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class PlacesQueryBuilder {
    private val baseQueryParams by lazy {
        mapOf(
            "client_id" to "3STOI1OJXLUHBZI4ZYSQVU0ONHTNYIFYXVYPIVCKJSGQUKUK",
            "client_secret" to "VU3A4VFI2RMEQDHMTX3THWPISNRT520GTSRCDKJF2QXLC0BG"
        )
    }

    fun build(): Map<String, String> {
        val queryParams = HashMap(baseQueryParams)
        queryParams["v"] = dateFormat.format(Date())
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

    companion object {
        private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.ROOT)
    }
}
