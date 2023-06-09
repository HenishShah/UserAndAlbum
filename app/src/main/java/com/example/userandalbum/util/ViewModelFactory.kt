package com.example.userandalbum.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.userandalbum.viewModels.UserDetailsViewModel

//view model factory class
class ViewModelFactory(
    private val activity: AppCompatActivity
) {
    fun getUserDetailsViewModel(): UserDetailsViewModel {
        return ViewModelProviders.of(activity)[UserDetailsViewModel::class.java]
    }
}