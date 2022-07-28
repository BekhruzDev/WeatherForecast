package com.bekhruz.weatherforecast.data.remote

class ResponseCodes {
    companion object{
        const val OK = 200
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
    }
}