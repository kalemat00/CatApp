package com.example.cat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CatViewModel: ViewModel() {
    //cat instance to send to the ui
    private val _cat: MutableLiveData<Cat> = MutableLiveData()
    val cat: LiveData<Cat> get() = _cat

    //array of cat to retrieve from api call
    private val _repos: MutableLiveData<Array<Cat>> = MutableLiveData()

    //error message to send to the ui
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    fun retrieveRepos(arrayLength: Int) {
        viewModelScope.launch {
            try {
                _repos.value = RetrofitInstance.getRetrofitInstance().listRepos(arrayLength)
                try {
                    getCatFromArray()
                } catch (e: Exception) {
                    _error.value = e.localizedMessage
                }
            } catch (e: Exception){
                _error.value = e.localizedMessage
            }
        }
    }

    private suspend fun getCatFromArray(){
        _repos.value!!.forEach {
            _cat.value = it
            delay(5000)
        }
    }
}