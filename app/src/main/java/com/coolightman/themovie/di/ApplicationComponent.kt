package com.coolightman.themovie.di

import android.content.Context
import com.coolightman.themovie.presentation.activity.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}