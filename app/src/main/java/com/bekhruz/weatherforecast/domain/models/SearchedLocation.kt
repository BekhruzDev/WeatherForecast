package com.bekhruz.weatherforecast.domain.models

import com.bekhruz.weatherforecast.data.remote.dto.geocodingdto.Result

data class SearchedLocation(
    val results: List<Result>
    )

