package com.coolightman.themovie.util

import android.content.Context
import com.coolightman.themovie.R
import com.coolightman.themovie.di.ApplicationScope
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@ApplicationScope
class ParseCoroutineException @Inject constructor(
    private val context: Context
) {

    fun parseException(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> context.getString(R.string.error_message_unknown_host_exception)
            is SocketTimeoutException -> context.getString(R.string.error_message_socket_timeout_exception)
            is HttpException -> "Http Exception: ${throwable.code()}"
            else -> "Some exception"
        }
    }
}