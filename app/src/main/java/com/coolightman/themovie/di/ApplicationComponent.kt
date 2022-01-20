package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.fragment.*
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
    fun inject(allFactsFragment: AllFactsFragment)
    fun inject(searchMovieFragment: SearchMovieFragment)
    fun inject(reviewFragment: ReviewFragment)
    fun inject(allReviewsFragment: AllReviewsFragment)
    fun inject(staffFragment: StaffFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}