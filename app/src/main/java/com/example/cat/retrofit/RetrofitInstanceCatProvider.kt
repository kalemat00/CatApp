package com.example.cat.retrofit

import com.example.cat.Cat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstanceCatProvider {
    private val client = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }
        )
        .addInterceptor(AuthorizationInterceptor(Url.HEADER, Url.APIKEY))
        .build()
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl(Url.BASEURL)
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    private val catService: CatService = retrofit.create(CatService::class.java)
    suspend fun provide(numberOfCats: Int): Array<Cat> = catService.listRepos(numberOfCats)
}