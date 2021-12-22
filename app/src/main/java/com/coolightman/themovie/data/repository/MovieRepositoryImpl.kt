package com.coolightman.themovie.data.repository

import androidx.lifecycle.LiveData
import com.coolightman.themovie.data.database.dao.MovieDao
import com.coolightman.themovie.domain.entity.Movie
import com.coolightman.themovie.domain.entity.ShortMovie
import com.coolightman.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(): LiveData<List<ShortMovie>> {
        TODO("Not yet implemented")
    }

    override fun getTop250Movies(): LiveData<List<ShortMovie>> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getMovieInfo(movieId: Long): LiveData<Movie> {
        TODO("Not yet implemented")
    }
}