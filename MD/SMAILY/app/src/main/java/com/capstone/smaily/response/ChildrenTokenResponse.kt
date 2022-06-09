package com.capstone.smaily.response

import com.google.gson.annotations.SerializedName

data class ChildrenTokenResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("childrenId")
    val childrenId: String,
    @field:SerializedName("parentId")
    val parentId: String,
    @field:SerializedName("accessToken")
    val accessToken: String,
)
