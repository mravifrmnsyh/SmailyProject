package com.capstone.smaily.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.smaily.network.ApiConfig
import com.capstone.smaily.response.ChildrenTokenResponse
import com.capstone.smaily.response.ParentLoginResponse
import com.capstone.smaily.response.ParentRegisterResponse
import com.capstone.smaily.response.ParentTokenResponse
import com.capstone.smaily.ui.parent.LoginParentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isIntent = MutableLiveData<Boolean>()
    val isIntent: LiveData<Boolean> = _isIntent

    val registerParenResponse = MutableLiveData<ParentRegisterResponse>()
    val loginParentResponse = MutableLiveData<ParentLoginResponse>()
    val tokenParentResponse = MutableLiveData<ParentTokenResponse>()
    val tokenChildrenResponse = MutableLiveData<ChildrenTokenResponse>()

    fun registerParent(name: String, email: String, password: String){
        _isLoading.value = true
        val registP = ApiConfig.getApiService().registParent(name, password, email)
        registP.enqueue(object: Callback<ParentRegisterResponse>{
            override fun onResponse(
                call: Call<ParentRegisterResponse>,
                response: Response<ParentRegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    registerParenResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = response.body()?.message
                } else {
                    _message.value = response.message()
                    Log.d("Failed", response.message())
                }
            }

            override fun onFailure(call: Call<ParentRegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun loginParent(email: String, password: String) {
        _isLoading.value = true
        val loginP = ApiConfig.getApiService().loginParent(email, password)
        loginP.enqueue(object : Callback<ParentLoginResponse> {
            override fun onResponse(
                call: Call<ParentLoginResponse>,
                response: Response<ParentLoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    loginParentResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = "Login Success"
                } else {
                    _message.value = response.message()
                    Log.d("Failed", response.message())
                }
            }

            override fun onFailure(call: Call<ParentLoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getParentLogin():LiveData<ParentLoginResponse> = loginParentResponse

    fun tokenParent(id: String, accessToken: String){
        _isLoading.value = true
        val getTokenP = ApiConfig.getApiService().getTokenParent(id, accessToken)
        getTokenP.enqueue(object : Callback<ParentTokenResponse> {
            override fun onResponse(
                call: Call<ParentTokenResponse>,
                response: Response<ParentTokenResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    tokenParentResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = "Get Token Success"
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ParentTokenResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getTokenParent():LiveData<ParentTokenResponse> = tokenParentResponse

    fun tokenChildren(token: String){
        _isLoading.value = true
        val getTokenP = ApiConfig.getApiService().tokenChildren(token)
        getTokenP.enqueue(object : Callback<ChildrenTokenResponse> {
            override fun onResponse(
                call: Call<ChildrenTokenResponse>,
                response: Response<ChildrenTokenResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    tokenChildrenResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = response.body()?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ChildrenTokenResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    fun getTokenChildren(): LiveData<ChildrenTokenResponse> = tokenChildrenResponse
}