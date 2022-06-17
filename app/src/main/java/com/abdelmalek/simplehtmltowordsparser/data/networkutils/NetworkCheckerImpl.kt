package com.abdelmalek.simplehtmltowordsparser.data.networkutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.abdelmalek.simplehtmltowordsparser.domain.datasource.datamanager.networkutils.NetworkChecker

class NetworkCheckerImpl(private val context: Context): NetworkChecker {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return isNetworkAvailable(connectivityManager)
    }

    private fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val internetActivity =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            when {
                internetActivity?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
                internetActivity?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }

}
