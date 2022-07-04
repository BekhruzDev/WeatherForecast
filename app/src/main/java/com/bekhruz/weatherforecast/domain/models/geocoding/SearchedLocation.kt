package com.bekhruz.weatherforecast.domain.models.geocoding

import com.bekhruz.weatherforecast.data.remote.dto.geocoding.Result

data class SearchedLocation(
    val results: List<Result>
    )

