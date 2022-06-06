package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class ParentTokenResponse(
    @field:SerializedName("connectToken")
    val connectToken: String
)
