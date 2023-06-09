package com.example.userandalbum.repository

import com.example.userandalbum.retrofitApi.RetrofitInstance
import com.example.userandalbum.util.DataState
import com.example.userandalbum.models.UserDetails
import com.example.userandalbum.util.logD
import com.example.userandalbum.util.logI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject

object UserDetailsRepository {

    suspend fun getUserDetailsList(): Flow<DataState<List<UserDetails>?>> =
        flow {
            try {
                val apiResponse = RetrofitInstance.api.getUserDetails()

                if (apiResponse.isSuccessful) {
                    val response = apiResponse.body()!!

                    logD("userDetailsApiResponse", "Success -> $apiResponse and $response")

                    val jsonArray = JSONArray(response)
                    //val name = jsonObject.getInt("name")
                    logI("userDetailsApiResponse","jsonArray - $jsonArray")

                    val listUserDetails: ArrayList<UserDetails> = ArrayList()
                    for(i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray[i] as JSONObject
                        val gson = Gson()
                        val userDetails = gson.fromJson(jsonObject.toString(), UserDetails::class.java)
                        listUserDetails.add(userDetails)
                    }
                    logI("userDetailsApiResponse","listUserDetails - $listUserDetails")
                    emit(DataState.Success(listUserDetails))

                } else {
                    logD("userDetailsApiResponse", "Error -> ${apiResponse.errorBody()}")
                    emit(DataState.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                logD("userDetailsApiResponse", "Exception -> ${e.localizedMessage}")
                emit(DataState.Error("Something Went Wrong"))
            }
        }
}