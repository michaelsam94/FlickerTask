package com.example.twittercounter.di

import com.example.data.repository.FlickerRepository
import com.example.domain.SearchPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun searchPhotosUseCase(flickerRepository: FlickerRepository) =
        SearchPhotosUseCase(flickerRepository)

}