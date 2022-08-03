package com.bekhruz.weatherforecast.data.remote

import java.io.IOException

sealed class NetworkExceptions : IOException()
class ServerErrorException(private val errorMessage: String) : NetworkExceptions() {
    override val message: String
        get() = errorMessage
}

class BadRequestException(private val errorMessage: String) : NetworkExceptions() {
    override val message: String
        get() = errorMessage
}

class UnAuthorizedException(private val errorMessage: String) : NetworkExceptions() {
    override val message: String
        get() = errorMessage
}

class OtherException(private val errorMessage: String) : NetworkExceptions() {
    override val message: String
        get() = errorMessage
}
//UnknownHostException for no internet connectivity

