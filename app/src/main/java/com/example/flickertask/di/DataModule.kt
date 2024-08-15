package com.example.twittercounter.di

import com.example.data.datasource.PhotoDataSource
import com.example.data.datasource.PhotoDataSourceImpl
import com.example.data.repository.FlickerRepository
import com.example.data.service.FlickerAPI
import com.example.repository.FlickerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun providePhotoDataSource(flickerAPI: FlickerAPI): PhotoDataSource =
        PhotoDataSourceImpl(flickerAPI)

    @Provides
    fun provideFlickerRepository(photoDataSource: PhotoDataSource): FlickerRepository =
        FlickerRepositoryImpl(photoDataSource)
}