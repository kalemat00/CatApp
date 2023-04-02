package com.example.cat.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cat.retrofit.RetrofitInstanceCatProvider
import com.example.cat.usecase.model.Cat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CatViewModel(private val catProvider: RetrofitInstanceCatProvider): ViewModel() {
    sealed class CatViewModelEvent{
        data class RetrieveCatsRepos(val getSnackBarWhenFinished:() -> Unit, val numberOfCats: Int): CatViewModelEvent()
    }

    fun send(event: CatViewModelEvent) = when(event){
        is CatViewModelEvent.RetrieveCatsRepos -> retrieveRepos(event.getSnackBarWhenFinished, event.numberOfCats)
    }

    sealed class CatViewModelResult(){
        data class Result(val cat: Cat): CatViewModelResult()
        data class Error(val message: String): CatViewModelResult()
    }

    private val _result: MutableLiveData<CatViewModelResult> = MutableLiveData()
    val result: LiveData<CatViewModelResult> get() = _result

    private lateinit var _repos: List<Cat>

    private fun retrieveRepos(getSnackBarWhenFinished:() -> Unit, numberOfCats: Int) {
        viewModelScope.launch {
            try {
                _repos = catProvider.provide(numberOfCats)
                getCatFromArray()
                getSnackBarWhenFinished()
            } catch (e: Exception){
                _result.value = e.localizedMessage?.let { CatViewModelResult.Error(it) }
            }
        }
    }

    private suspend fun getCatFromArray(){
        _repos.forEach {
            _result.value = CatViewModelResult.Result(it)
            delay(5000)
        }
    }
}