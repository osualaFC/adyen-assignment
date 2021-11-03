package com.adyen.android.assignment.data.api.model.mappers

interface ApiMapper<E, D> {
    fun mapToDomain(apiEntity: E): D
}