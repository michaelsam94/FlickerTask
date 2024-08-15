package com.example.data.datasource


import com.example.data.model.FlickrResponse
import com.example.data.service.FlickerAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.awaitResponse


class PhotoDataSourceImpl(
    private val flickerAPI: FlickerAPI,
) : PhotoDataSource {

    override suspend fun searchPhotos(
        text: String,
        page: Int,
        perPage: Int,
        apiKey: String
    ): Response<FlickrResponse> {
        // Execute the API call and convert Call<FlickrResponse> to Response<FlickrResponse>
        return withContext(Dispatchers.IO) {
            flickerAPI.searchPhotos(text, page, perPage, apiKey).awaitResponse()
        }
    }
}