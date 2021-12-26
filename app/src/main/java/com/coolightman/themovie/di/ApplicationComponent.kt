package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.activity.MainActivity
import com.coolightman.themovie.presentation.fragment.MainFragment
import com.coolightman.themovie.presentation.fragment.MoviesFavoriteFragment
import com.coolightman.themovie.presentation.fragment.MoviesPopularFragment
import com.coolightman.themovie.presentation.fragment.MoviesTop250Fragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}