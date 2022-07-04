package com.bekhruz.weatherforecast.data.remote.utils

interface Mapper<I, O> {
    fun asDomain(dto:I):O
}