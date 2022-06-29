package com.raywenderlich.platform_channel_events

import android.R
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import io.flutter.Log
import io.flutter.plugin.common.EventChannel


class NetworkStreamHandler(private var activity: Activity?) : EventChannel.StreamHandler {

    private var eventSink: EventChannel.EventSink? = null

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
        startListeningNetworkChanges()
        Log.i("activity","${activity.toString()}")
        Log.i("event","${events}")

    }

    override fun onCancel(arguments: Any?) {
        stopListeningNetworkChanges()
        eventSink = null
        activity = null
    }

    private val networkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                // Notify Flutter that the network is disconnected
                activity?.runOnUiThread { eventSink?.success(Constants.disconnected) }
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onCapabilitiesChanged(network: Network, netCap: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, netCap)
                // Pick the supported network states and notify Flutter of this new state
                val status =
                    when {
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Constants.wifi
                        netCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Constants.cellular
                        else -> Constants.unknown
                    }
                activity?.runOnUiThread { eventSink?.success(status) }
            }
        }
    } else {
        TODO("VERSION.SDK_INT < LOLLIPOP")
    }


    private fun startListeningNetworkChanges() {


        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager?.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build()
            } else {
                TODO("VERSION.SDK_INT < LOLLIPOP")
            }
            manager?.registerNetworkCallback(request, networkCallback)
        }
        Toast.makeText(activity,"startListeningNetworkChanges",Toast.LENGTH_LONG).show()
    }

    private fun stopListeningNetworkChanges() {
        val manager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager?.unregisterNetworkCallback(networkCallback)
        }
        Toast.makeText(activity,"stopListeningNetworkChanges",Toast.LENGTH_LONG).show()
    }
}