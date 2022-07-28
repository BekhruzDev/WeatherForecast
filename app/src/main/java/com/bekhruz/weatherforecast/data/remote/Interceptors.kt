package com.bekhruz.weatherforecast.data.remote

import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.INTERNAL_SERVER_ERROR
import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.UNAUTHORIZED
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import com.bekhruz.weatherforecast.data.remote.NetworkExceptions.*
import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.BAD_GATEWAY

class HttpLoggingInterceptor {
    private val httpLoggingInterceptor = HttpLoggingInterceptor()
    operator fun invoke(): HttpLoggingInterceptor {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        throw when (response.code) {
            UNAUTHORIZED -> UnAuthorizedException()
            INTERNAL_SERVER_ERROR, BAD_GATEWAY -> ServerErrorException()
            else -> BadRequestException()
        }
    }
}

//TODO: ADD CACHING INTERCEPTOR
