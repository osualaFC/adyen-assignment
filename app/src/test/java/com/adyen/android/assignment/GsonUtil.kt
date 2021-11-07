package com.adyen.android.assignment

import com.adyen.android.assignment.data.api.model.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {
    inline fun <reified T> fromJson(json: String?, java: Class<Response<*>>): T {
        return Gson().fromJson<T>(json, object: TypeToken<T>(){}.type)
    }
}
