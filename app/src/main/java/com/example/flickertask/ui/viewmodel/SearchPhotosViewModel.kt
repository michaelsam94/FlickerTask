package com.example.flickertask.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Photo
import com.example.domain.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(private val searchPhotosUseCase: SearchPhotosUseCase) :
    ViewModel() {

    var photos = mutableStateOf<List<Photo>>(emptyList())
    var currentPage = mutableStateOf(1)
    var isLoading = mutableStateOf(false)
    var hasMorePages = mutableStateOf(true)

    fun searchPhotos(text: String, apiKey: String,page: Int) {
        viewModelScope.launch {
            isLoading.value = true
            searchPhotosUseCase(text, currentPage.value, 20, apiKey).collectLatest { response ->
                photos.value += response.photos.photos
                currentPage.value = page
                hasMorePages.value = response.photos.pages > currentPage.value
                isLoading.value = false
            }
        }
    }
}