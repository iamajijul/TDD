package com.ajijul.tdd.data.remote

import com.ajijul.tdd.BuildConfig
import com.ajijul.tdd.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImages(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}