package ru.alexmenkov_photo.demoratingapp.service.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import ru.alexmenkov_photo.demoratingapp.data.Page
import ru.alexmenkov_photo.demoratingapp.data.lot.BaseLotDto

interface LotServiceRestApi {

    @GET("/api/catalog/lot")
    suspend fun getLotPage(
        @Query("path") path: String,
        @Query("search") search: String? = null,
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int,
        @Query("popularSort") popularSort: String = "desc",
        @Query("priceSort") priceSort: String = "desc",
        @Query("ratingSort") ratingSort: String = "asc",
        @Query("targetLang") targetLang: String = "en"
    ): Page<BaseLotDto>
}