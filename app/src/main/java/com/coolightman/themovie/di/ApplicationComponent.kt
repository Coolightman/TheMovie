package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.activity.MainActivity
import com.coolightman.themovie.presentation.fragment.*
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

    fun inject(mainFragment: MainFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}