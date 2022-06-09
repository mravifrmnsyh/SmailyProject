package com.capstone.smaily.network

import com.capstone.smaily.response.*
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

    @POST("auth/register/{id}/children")
    fun getTokenParent(
        @Path("id") id : String,
        @Header("x-access-token") token: String
    ): Call<ParentTokenResponse>

    @POST("auth/login/children/{token}")
    fun tokenChildren(
        @Path("token") token: String
    ): Call<ChildrenTokenResponse>

    @GET("user/{id}/profile")
    fun getProfilParent(
        @Path("id") id: String,
        @Header("x-access-token") token: String
    ): Call<ParentProfileResponse>

    @FormUrlEncoded
    @PUT("user/{id}/lock/url")
    fun setBlockUrl(
        @Path("id") id : String,
        @Field("url") url : String,
        @Field("lock") lock : Boolean,
        @Header("x-access-token") token: String
    ): Call<ParentUrlResponse>

    @GET("children/{id}/lock/url")
    suspend fun getBlockUrl(
        @Path("id") id: String,
        @Header("x-access-token") token: String
    ): List<UrlResponse>

    @GET("children/{id}/lock/url")
    fun getBlockUrlChild(
        @Path("id") id: String,
        @Header("x-access-token") token: String
    ): Call<List<UrlResponse>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "user/{id}/lock/url", hasBody = true)
    fun deleteUrl(
        @Path("id") id : String,
        @Field("url") url : String,
        @Header("x-access-token") token: String
    ): Call<DeleteUrlResponse>
}