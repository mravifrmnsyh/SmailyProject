package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class ParentLoginResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("accessToken")
    val accessToken: String,
)
