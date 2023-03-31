package com.example.cat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cat.retrofit.CatService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CatViewModel(private val catService: CatService): ViewModel() {
    //cat instance to send to the ui
    private val _cat: MutableLiveData<Cat> = MutableLiveData()
    val cat: LiveData<Cat> get() = _cat

    //array of cat to retrieve from api call
    private lateinit var _repos: Array<Cat>

    //error message to send to the ui
    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    fun retrieveRepos(getSnackBarWhenFinished:()  -> Unit) {
        viewModelScope.launch {
            try {
                _repos = catService.listRepos(3)
                getCatFromArray(getSnackBarWhenFinished)
            } catch (e: Exception){
                _error.value = e.localizedMessage
            }
        }
    }

    private suspend fun getCatFromArray(getSnackBarWhenFinished:()  -> Unit){
        _repos.forEach {
            _cat.value = it
            delay(5000)
        }
        getSnackBarWhenFinished()
    }
}