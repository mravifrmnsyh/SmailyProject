package com.capstone.smaily.network

import com.capstone.smaily.response.ChildrenTokenResponse
import com.capstone.smaily.response.ParentLoginResponse
import com.capstone.smaily.response.ParentRegisterResponse
import com.capstone.smaily.response.ParentTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun registParent(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Call<ParentRegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun loginParent(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ParentLoginResponse>

    @POST("auth/{id}/registerChildren")
    fun getTokenParent(
        @Path("id") id : String,
        @Header("x-access-token") token: String
    ): Call<ParentTokenResponse>

    @POST("auth/login/children/{token}")
    fun tokenChildren(
        @Path("token") token: String
    ): Call<ChildrenTokenResponse>
}