package com.example.api.retrofit

import com.example.api.retrofit.model.WordDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchListApi {
    @GET("api/public/v1/words/search")
    fun getList(
        @Query("search") word: String,
        @Query("pageSize") pageSize: Int = 100
    ): Call<List<WordDTO>>
}