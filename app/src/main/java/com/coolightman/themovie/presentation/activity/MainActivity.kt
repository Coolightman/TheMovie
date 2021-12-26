package com.coolightman.themovie.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}