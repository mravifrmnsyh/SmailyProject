package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class UrlResponse(
    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("isLocked")
    val isLocked: String? = null
)