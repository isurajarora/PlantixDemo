package net.plantix.demo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object extensions {

    fun String.Companion.toTitleCase(text: String): String {
        if (text == null || text.isEmpty()) {
            return ""
        }
        val converted = StringBuilder()
        var convertNext = true
        for (ch in text.toCharArray()) {
            var ch_final = ch
            if (Character.isSpaceChar(ch)) {
                convertNext = true
            } else if (convertNext) {
                ch_final = Character.toTitleCase(ch)
                convertNext = false
            } else {
                ch_final = Character.toLowerCase(ch)
            }
            converted.append(ch_final)
        }
        return converted.toString()
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }
}