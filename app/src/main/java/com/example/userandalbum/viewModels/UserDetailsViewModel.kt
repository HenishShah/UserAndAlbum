package com.example.userandalbum.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.userandalbum.models.UserDetails
import com.example.userandalbum.repository.UserDetailsRepository
import com.example.userandalbum.util.DataState
import kotlinx.coroutines.launch

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val userDetails: LiveData<DataState<List<UserDetails>?>>
        get() = _dataState

    private val _dataState: MutableLiveData<DataState<List<UserDetails>?>> = MutableLiveData()

    fun setStateEvent() {
        _dataState.value = DataState.Loading
        viewModelScope.launch {
            UserDetailsRepository.getUserDetailsList()
                .collect { dataState ->
                    _dataState.value = dataState
                }
        }
    }
}