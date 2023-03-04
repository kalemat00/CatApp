package com.example.cat

import retrofit2.http.GET
import retrofit2.http.Query


interface CatService {
    @GET("search")
    suspend fun listRepos(@Query("limit") arrayLength: Int): Array<Cat>
}