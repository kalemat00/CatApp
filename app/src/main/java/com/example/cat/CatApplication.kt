package com.example.cat

import android.app.Application
import com.example.cat.retrofit.RetrofitInstanceCatProvider

class CatApplication: Application() {

    private val retrofitInstance: RetrofitInstanceCatProvider = RetrofitInstanceCatProvider()
    val catViewModel = ViewModelFactory(retrofitInstance)

}