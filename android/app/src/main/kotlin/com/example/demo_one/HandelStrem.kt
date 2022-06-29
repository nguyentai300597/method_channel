package com.example.demo_one

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import io.flutter.plugin.common.EventChannel

class HandelStrem(private var activity: Activity): EventChannel.StreamHandler {

    private var eventSink: EventChannel.EventSink? = null
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {

        eventSink = events
        startListeningChanges()

    }

    override fun onCancel(arguments: Any?) {

                eventSink = null

    }
    private val networkCallback = {

            // Notify Flutter that the network is disconnected
            activity?.runOnUiThread { eventSink?.success("data to native") }
        }
 // @SuppressLint("ResourceType")
 private fun  startListeningChanges(){
       //val manager = activity?.getString()
      activity?.runOnUiThread { eventSink?.success("Constants.disconnected") }
eventSink!!.success("3333")
      Toast.makeText(activity,"this is handel ${eventSink.toString()}",Toast.LENGTH_LONG).show()
    }


}