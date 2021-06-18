package com.osamaaltawil.alquds.service

import com.osamaaltawil.alquds.annotation.Cacheable
import com.osamaaltawil.alquds.helper.Constance.API_TYPES
import com.osamaaltawil.alquds.models.ApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Cacheable
    @GET(API_TYPES)
    fun getNews(
            @Query("q") query: String,
            @Query("from") from: String,
            @Query("to") to: String,
            @Query("sortBy") sortBy: String,
            @Query("apiKey") apiKey: String
    ): Call<ApiModel.Result>

}
