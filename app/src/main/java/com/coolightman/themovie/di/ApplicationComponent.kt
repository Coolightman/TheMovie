package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.activity.MainActivity
import com.coolightman.themovie.presentation.fragment.MoviesPopularFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ViewModelModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(popularFragment: MoviesPopularFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}