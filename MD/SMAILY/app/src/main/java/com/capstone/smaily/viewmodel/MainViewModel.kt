package com.capstone.smaily.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.smaily.network.ApiConfig
import com.capstone.smaily.response.*
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
    //opsional
    val parentProfiResponse = MutableLiveData<ParentProfileResponse>()
    //end
    val parentUrlResponse = MutableLiveData<ParentUrlResponse>()
    val deleteUrlResponse = MutableLiveData<DeleteUrlResponse>()

    val childrenUrlResponse = MutableLiveData<List<UrlResponse>>()

    //register parent
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

    //login parent
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

    //get login parent
    fun getParentLogin():LiveData<ParentLoginResponse> = loginParentResponse

    //token parent
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

    //get token parent
    fun getTokenParent():LiveData<ParentTokenResponse> = tokenParentResponse

    //token children
    fun tokenChildren(accessToken: String){
        _isLoading.value = true
        val getTokenP = ApiConfig.getApiService().tokenChildren(accessToken)
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

    //get token children
    fun getTokenChildren(): LiveData<ChildrenTokenResponse> = tokenChildrenResponse

    //opsional (profile parent)
    fun profilParent(id: String, accessToken: String){
        _isLoading.value = true
        val getProfilP = ApiConfig.getApiService().getProfilParent(id, accessToken)
        getProfilP.enqueue(object : Callback<ParentProfileResponse> {
            override fun onResponse(
                call: Call<ParentProfileResponse>,
                response: Response<ParentProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    parentProfiResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = response.body()?.name
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ParentProfileResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    //get profile parent
    fun getProfilParent(): LiveData<ParentProfileResponse> = parentProfiResponse

    //set url parent
    fun setUrlParent(id: String, url: String, lock: Boolean, accessToken: String){
        _isLoading.value = true
        val setUrl = ApiConfig.getApiService().setBlockUrl(id, url, lock, accessToken)
        setUrl.enqueue(object : Callback<ParentUrlResponse> {
            override fun onResponse(
                call: Call<ParentUrlResponse>,
                response: Response<ParentUrlResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    parentUrlResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                    _isIntent.value = true
                    _message.value = response.body()?.message
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ParentUrlResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }

        })
    }

    //getUrel
    fun dataUrlChildren(id: String, accessToken: String) {
        val urlGet = ApiConfig.getApiService().getBlockUrlChild(id, accessToken)
        urlGet.enqueue(object : Callback<List<UrlResponse>> {
            override fun onResponse(
                call: Call<List<UrlResponse>>,
                response: Response<List<UrlResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    childrenUrlResponse.postValue(response.body())
                    Log.d("Success", response.body().toString())
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<List<UrlResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Failure", t.message.toString())
            }
        })
    }

    //get data url
    fun getDataUrl(): LiveData<List<UrlResponse>> = childrenUrlResponse

//    //delete data Url
//    fun deleteDataUrl(id: String, url: String, accessToken: String){
//        val urlGet = ApiConfig.getApiService().deleteUrl(id, url, accessToken)
//        urlGet.enqueue(object : Callback<DeleteUrlResponse> {
//            override fun onResponse(
//                call: Call<DeleteUrlResponse>,
//                response: Response<DeleteUrlResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful){
//                    deleteUrlResponse.postValue(response.body())
//                    Log.d("Success", response.body().toString())
//                } else {
//                    _message.value = response.message()
//                }
//            }
//
//            override fun onFailure(call: Call<DeleteUrlResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.d("Failure", t.message.toString())
//            }
//        })
//    }

}