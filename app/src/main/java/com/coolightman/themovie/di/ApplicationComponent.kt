package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.fragment.GalleryFragment
import com.coolightman.themovie.presentation.fragment.MainFragment
import com.coolightman.themovie.presentation.fragment.MovieDetailFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ViewModelModule::class,
        RepositoryBindModule::class,
        NetworkModule::class,
        DatabaseModule::class
    ]
)
@ApplicationScope
interface ApplicationComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(movieDetailFragment: MovieDetailFragment)
    fun inject(galleryFragment: GalleryFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}