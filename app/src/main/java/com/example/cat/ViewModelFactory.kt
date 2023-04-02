package com.example.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cat.retrofit.RetrofitInstanceCatProvider
import com.example.cat.usecase.CatViewModel

class ViewModelFactory(private val catProvider: RetrofitInstanceCatProvider):  ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)) {
            return modelClass.getConstructor(RetrofitInstanceCatProvider::class.java).newInstance(catProvider)
        }
        throw IllegalArgumentException("Unknown ViewModel class")

        /* the code used by sasha
        if (modelClass.isAssignableFrom(CatViewModel::class.java)){
            return CatViewModel(carProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")*/
    }
}