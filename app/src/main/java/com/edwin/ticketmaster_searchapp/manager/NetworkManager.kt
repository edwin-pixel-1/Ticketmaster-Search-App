package com.edwin.ticketmaster_searchapp.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkManager(private val context: Context) {

    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        connectivityManager?.apply {
            getNetworkCapabilities(activeNetwork)?.also { capability ->
                return capability.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }
        return false
    }

}