package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class ChildrenTokenResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("accessToken")
    val accessToken: String,
)
