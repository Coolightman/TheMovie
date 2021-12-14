package com.coolightman.themovie

import android.app.Application
import com.coolightman.themovie.di.DaggerApplicationComponent

class App: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}