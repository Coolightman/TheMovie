package com.coolightman.themovie.util

import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ParseException {
    fun parseException(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> "Check INTERNET connection"
            is SocketTimeoutException -> "Check INTERNET connection"
            else -> "Some exception"
        }
    }
}