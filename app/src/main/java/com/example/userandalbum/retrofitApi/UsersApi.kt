package com.example.userandalbum.retrofitApi

import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    suspend fun getUserDetails(
        //@Query("id") id: Int = 1
    ): Response<String>

    @GET("photos")
    suspend fun getImagesList(
        //@Query("id") id: Int = 1
    ): Response<String>
}