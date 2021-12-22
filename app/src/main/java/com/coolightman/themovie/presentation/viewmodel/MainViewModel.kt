package com.coolightman.themovie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coolightman.themovie.domain.usecase.GetMovieVideosUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val useCase: GetMovieVideosUseCase
) : ViewModel() {

//    suspend fun getMovieVideos(movieId: Long): LiveData<List<Video>>{
//        return useCase.invoke(movieId)
//    }

}