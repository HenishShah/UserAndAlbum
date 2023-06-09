package com.example.userandalbum.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.userandalbum.models.ImageDetails
import com.example.userandalbum.repository.ImageDetailsRepository
import com.example.userandalbum.util.DataState
import kotlinx.coroutines.launch

class ImageDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val imageDetails: LiveData<DataState<List<ImageDetails>?>>
        get() = _dataState

    private val _dataState: MutableLiveData<DataState<List<ImageDetails>?>> = MutableLiveData()

    fun setStateEvent() {
        _dataState.value = DataState.Loading
        viewModelScope.launch {
            ImageDetailsRepository.getImageDetailsList()
                .collect { dataState ->
                    _dataState.value = dataState
                }
        }
    }
}