package com.capstone.smaily.data

import android.content.Context
import com.capstone.smaily.network.ApiConfig
import com.capstone.smaily.preferences.ParentLoginPref

object Injection {
    fun provideRepository(context: Context): UrlRepository {
        val apiService = ApiConfig.getApiService()
        val idChild = ParentLoginPref(context).getUser().idChildren.toString()
        val idParent = ParentLoginPref(context).getUser().id.toString()
        val token = ParentLoginPref(context).getUser().accesstoken.toString()
        return UrlRepository(apiService, idParent, idChild, token)
    }
}