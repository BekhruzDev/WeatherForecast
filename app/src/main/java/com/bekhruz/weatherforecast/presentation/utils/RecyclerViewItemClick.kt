package com.bekhruz.weatherforecast.presentation.utils


interface RecyclerViewItemClick<T> {
    fun onItemClicked(item: T)
}