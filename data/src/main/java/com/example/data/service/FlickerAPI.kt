package com.example.data.service

import com.example.data.model.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerAPI {
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun searchPhotos(
        @Query("text") text: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("api_key") apiKey: String
    ): Call<FlickrResponse>
}