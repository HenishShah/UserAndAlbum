package com.example.userandalbum.repository

import com.example.userandalbum.models.ImageDetails
import com.example.userandalbum.retrofitApi.RetrofitInstance
import com.example.userandalbum.util.DataState
import com.example.userandalbum.util.logD
import com.example.userandalbum.util.logI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject

object ImageDetailsRepository {

    suspend fun getImageDetailsList(): Flow<DataState<List<ImageDetails>?>> =
        flow {
            try {
                val apiResponse = RetrofitInstance.api.getImagesList()

                if (apiResponse.isSuccessful) {
                    val response = apiResponse.body()!!

                    logD("imageDetailsApiResponse", "Success -> $apiResponse and $response")

                    val jsonArray = JSONArray(response)
                    //val name = jsonObject.getInt("name")
                    logI("imageDetailsApiResponse","jsonArray - $jsonArray")

                    val listImageDetails: ArrayList<ImageDetails> = ArrayList()
                    for(i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray[i] as JSONObject
                        val gson = Gson()
                        val imageDetails = gson.fromJson(jsonObject.toString(), ImageDetails::class.java)
                        listImageDetails.add(imageDetails)
                    }
                    logI("imageDetailsApiResponse","listImageDetails - $listImageDetails")
                    emit(DataState.Success(listImageDetails))

                } else {
                    logD("imageDetailsApiResponse", "Error -> ${apiResponse.errorBody()}")
                    emit(DataState.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                logD("imageDetailsApiResponse", "Exception -> ${e.localizedMessage}")
                emit(DataState.Error("Something Went Wrong"))
            }
        }
}