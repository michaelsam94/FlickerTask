package com.example.data.datasource


import com.example.data.model.FlickrResponse
import retrofit2.Response


interface PhotoDataSource {
    suspend fun searchPhotos(
        text: String,
        page: Int,
        perPage: Int,
        apiKey: String
    ): Response<FlickrResponse>

}