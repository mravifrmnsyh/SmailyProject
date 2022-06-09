package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class DeleteUrlResponse(
    @field:SerializedName("message")
    val message: String
)
