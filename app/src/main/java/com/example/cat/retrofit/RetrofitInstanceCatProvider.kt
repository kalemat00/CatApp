package com.example.cat.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstanceCatProvider {
    class AuthorizationInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder().addHeader(Url.HEADER, Url.APIKEY).build()
            Log.i("Authorization Interceptor", "newRequest: $newRequest")
            return chain.proceed(newRequest)
        }
    }
    private val logging = HttpLoggingInterceptor()
    private val authorization = AuthorizationInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authorization)
        .build()
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .client(client)
        .baseUrl(Url.BASEURL)
        .addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    init {
        logging.level = HttpLoggingInterceptor.Level.HEADERS
    }
    private val catService: CatService = retrofit.create(CatService::class.java)
    fun provide(): CatService = catService
}