package com.coolightman.themovie.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Coroutine_exception", "$throwable")
    }

}