package com.example.repository

import com.example.data.datasource.PhotoDataSource
import com.example.data.model.FlickrResponse
import com.example.data.repository.FlickerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class FlickerRepositoryImpl(
    private val photoDataSource: PhotoDataSource,
) : FlickerRepository {

    override suspend fun searchPhotos(
        text: String,
        page: Int,
        perPage: Int,
        apiKey: String
    ): Flow<FlickrResponse> = flow {
        val response = photoDataSource.searchPhotos(text,page,perPage,apiKey)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(it)
            } ?: throw Exception("Response body is null")
        } else
            throw Exception("Response is not successful")
    }.catch {
        throw Exception(it)
    }


}