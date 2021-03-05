package ru.alexmenkov_photo.demoratingapp.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.alexmenkov_photo.demoratingapp.service.retrofit.LotServiceRestApi
import javax.inject.Named


@Module(includes = [RetrofitModule::class])
class RemoteApiModule {

    @Provides
    fun provideLotServiceApi(@Named("default") retrofit: Retrofit): LotServiceRestApi {
        return retrofit.create(LotServiceRestApi::class.java)
    }

    @Provides
    @LotServiceUrl
    fun getLotServiceUrl(): String {
        return "https://dev.freezoneapp.com/"
    }
}