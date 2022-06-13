package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class AppResponse(

    @field:SerializedName("message")
    val message: String,

)
