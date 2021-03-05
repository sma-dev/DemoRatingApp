package ru.alexmenkov_photo.demoratingapp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModule() {

    @Provides
    @Singleton
    @Named("default")
    fun provideRetrofit(@LotServiceUrl mBaseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .build()
            ).build()
    }

    @Provides
    @Singleton
    @Named("logs_enabled")
    fun provideLogRetrofit(@LotServiceUrl mBaseUrl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .build()
            ).build()
    }
}