package com.capstone.smaily.data

import com.capstone.smaily.network.ApiService
import com.capstone.smaily.response.UrlResponse

class UrlRepository(
    private val apiService: ApiService,
    private val idChildren: String,
    private val token: String
) {
    suspend fun getUrl(): List<UrlResponse> {
        return apiService.getBlockUrl(idChildren, token)
    }
}