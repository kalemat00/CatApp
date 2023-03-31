package com.example.cat.retrofit

import com.example.cat.Cat
import retrofit2.http.GET
import retrofit2.http.Query


interface CatService {
    @GET("search")
    suspend fun listRepos(@Query("limit") arrayLength: Int): Array<Cat>
}