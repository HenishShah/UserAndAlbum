package com.example.userandalbum.util

import android.app.Activity
import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class CheckNetwork(
    private val activity: Activity
) {
    fun isNetworkAvailable(killActivity: Boolean = false): Boolean {
        val connectivityManager: ConnectivityManager = activity.getSystemService(Application.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->{
                    logI("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")

                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->{
                    logI("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->{
                    logI("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        showNoInternetError(killActivity)
        return false
    }

    private fun showNoInternetError(killActivity: Boolean){
        /*AlertDialog.Builder(activity)
            .setMessage("No internet available. Please check your network")
            .setNeutralButton("OK"){ _: DialogInterface, _: Int ->
                if(killActivity)
                    activity.finish()
            }
            .setCancelable(false)
            .create().show()*/

        //activity.notifyUser("No internet available. Please check your network")
    }
}