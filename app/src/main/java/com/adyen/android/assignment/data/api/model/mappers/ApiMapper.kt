package com.adyen.android.assignment.data.api.model.mappers

interface ApiMapper<E, D> {
    fun mapData(apiEntity: E): D
}