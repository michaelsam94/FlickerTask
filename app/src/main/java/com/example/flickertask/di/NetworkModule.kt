package com.example.twittercounter.di

import android.util.Log.d
import com.example.data.service.FlickerAPI
import com.example.flickertask.BuildConfig
import com.example.flickertask.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun retrofitService(retrofit: Retrofit): FlickerAPI {
        return retrofit.create(FlickerAPI::class.java)
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: okhttp3.Interceptor,
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(headerInterceptor)
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Provides
    fun provideHeaderInterceptor(): okhttp3.Interceptor {
        return okhttp3.Interceptor { chain: okhttp3.Interceptor.Chain ->
            val timeoutSeconds = 10000
            val request: okhttp3.Request = chain.request()
            val requestBuilder: okhttp3.Request.Builder = request.newBuilder()
            requestBuilder.addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Timeout", timeoutSeconds.toString())
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> d("OkHttp", message) }.apply {
            if (BuildConfig.DEBUG) setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        }
    }


}