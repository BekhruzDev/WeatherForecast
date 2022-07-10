package com.bekhruz.weatherforecast.presentation.utils
object Icons {
    fun getIconsOfSixteenDayData(iconId:String):String{
        return String.format("https://www.weatherbit.io/static/img/icons/$iconId.png")
    }
    //TODO:ASK THIS
    fun String.buildUri():String = "$this .toUri().buildUpon().scheme(\"https\").build()"
}