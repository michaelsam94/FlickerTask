package com.example.domain

import com.example.data.repository.FlickerRepository

class SearchPhotosUseCase (private val repo: FlickerRepository) {
    suspend operator fun invoke(text: String,
                                page: Int,
                                perPage: Int,
                                apiKey: String) = repo.searchPhotos(text, page, perPage, apiKey)
}