package com.example.cat.retrofit

import com.example.cat.Cat
import retrofit2.http.GET
import retrofit2.http.Query


interface CatService {
    @GET(Url.PATH)
    suspend fun listRepos(@Query(Url.QUERY) arrayLength: Int): Array<Cat>
}