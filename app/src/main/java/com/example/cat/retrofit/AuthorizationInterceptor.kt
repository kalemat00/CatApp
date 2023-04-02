package com.example.cat.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val header: String, private val apikey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().addHeader(header, apikey).build()
        Log.i("Authorization Interceptor", "newRequest: $newRequest")
        return chain.proceed(newRequest)
    }
}