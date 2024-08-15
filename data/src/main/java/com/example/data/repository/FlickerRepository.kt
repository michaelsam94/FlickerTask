package com.example.data.repository

import com.example.data.model.FlickrResponse
import kotlinx.coroutines.flow.Flow

interface FlickerRepository {
    suspend fun searchPhotos(
        text: String,
        page: Int,
        perPage: Int,
        apiKey: String
    ): Flow<FlickrResponse>
}