package com.example.userandalbum.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.userandalbum.util.ViewModelFactory

open class BaseActivity : AppCompatActivity() {

    private var nullableViewModelFactory: ViewModelFactory? = null

    protected val viewModelFactory: ViewModelFactory
        get() {
            if (nullableViewModelFactory == null) {
                nullableViewModelFactory = ViewModelFactory(this)
            }
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)  //to disable dark mode of app
            //adjustDisplayScale( resources.configuration)
            return nullableViewModelFactory as ViewModelFactory
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}