package com.bekhruz.weatherforecast.data.remote

import okio.IOException
import java.io.IOException as JavaIOException

sealed class NetworkExceptions {
    class ServerErrorException: IOException()
    class BadRequestException: IOException()
    class UnAuthorizedException: JavaIOException()
}