package com.coolightman.themovie.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolightman.themovie.App
import com.coolightman.themovie.R
import com.coolightman.themovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as App).component
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}