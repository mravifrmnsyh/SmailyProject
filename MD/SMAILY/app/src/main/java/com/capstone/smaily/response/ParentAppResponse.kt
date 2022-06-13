package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class ParentAppResponse(
    @field:SerializedName("app")
    val app: String? = null,

    @field:SerializedName("isLocked")
    val isLocked: String? = null
)
