package com.example.cat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.cat.retrofit.CatService

class ViewModelFactory(private val catService: CatService):  ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatViewModel::class.java)){
            return CatViewModel(catService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}