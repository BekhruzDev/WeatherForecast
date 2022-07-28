package com.bekhruz.weatherforecast.data.remote

import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.INTERNAL_SERVER_ERROR
import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.UNAUTHORIZED_ERROR
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import com.bekhruz.weatherforecast.data.remote.ResponseCodes.Companion.BAD_GATEWAY_ERROR

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
        val message = response.message
        throw when (response.code) {
            UNAUTHORIZED_ERROR -> UnAuthorizedException(message)
            INTERNAL_SERVER_ERROR, BAD_GATEWAY_ERROR -> ServerErrorException(message)
            else -> BadRequestException(message)
        }
    }
}

//TODO: ADD CACHING INTERCEPTOR
