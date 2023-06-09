package com.example.userandalbum.retrofitApi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            val logging =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .client(client)
                .addConverterFactory(StringConverterFactory().create())
                .build()
        }

        val api: UsersApi by lazy {
            retrofit.create(UsersApi::class.java)
        }
    }
}