package com.capstone.smaily.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.capstone.smaily.data.Injection
import com.capstone.smaily.data.UrlRepository
import com.capstone.smaily.response.ParentAppResponse
import com.capstone.smaily.response.UrlResponse
import kotlinx.coroutines.launch

class UrlViewModel(private val urlRepository: UrlRepository) : ViewModel() {
    private val _url = MutableLiveData<List<UrlResponse>>()
    var url: LiveData<List<UrlResponse>> = _url

    private val _app = MutableLiveData<List<ParentAppResponse>>()
    var app: LiveData<List<ParentAppResponse>> = _app

    fun getUrl() {
        viewModelScope.launch {
            _url.postValue(urlRepository.getUrl())
        }
    }

    fun getApp(){
        viewModelScope.launch {
            _app.postValue(urlRepository.getApp())
        }
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UrlViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UrlViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}