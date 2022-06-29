package com.example.demo_one

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.raywenderlich.platform_channel_events.NetworkStreamHandler
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.JSONMessageCodec
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import org.json.JSONObject


class MainActivity: FlutterActivity() {
    private val channel_ping="checkping"
    private val channel_activity="acitity_Second"
    private val networkEventChannel = "platform_channel_events/connectivity"
    private val  StreamChannel="StreamChannel"
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    val StringCodecChannel = "StringCodec"
    val JSONMessageCodecChannel = "JSONMessageCodec"
    val BinaryCodecChannel = "BinaryCodec"
    val StandardMessageCodecChannel = "StandardMessageCodec"
     var dataresule:String="no data"

    private lateinit var channel: MethodChannel
    var handler: Handler = Handler(Looper.getMainLooper())
    private var eventSink: EventChannel.EventSink? = null
    var callback: Runnable? = null
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(FlutterEngine(this@MainActivity))
       // setContentView(R.layout.activity_second)
//        EventChannel(flutterEngine.dartExecutor.binaryMessenger,channel_ping).setStreamHandler(HandelStrem(
//            this
//        ))
        EventChannel(flutterEngine.dartExecutor.binaryMessenger, networkEventChannel)
            .setStreamHandler(NetworkStreamHandler(this))
        sendMethodChanel()
       demoEventChannel()

        super.configureFlutterEngine(flutterEngine)
        


    }
    private  fun sendMethodChanel(){
        channel= MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger,channel_ping)
        channel.setMethodCallHandler { call, result ->

            if(call.method=="checkping"){
                Toast.makeText(this,"floast in android",Toast.LENGTH_LONG).show()
            }
            if (call.method=="activity"){
                //      val requestCode = [{"val requestCode = [yourCodeHere]"}]

                startActivityForResult(Intent(this@MainActivity,SecondActivity::class.java),SECOND_ACTIVITY_REQUEST_CODE)
              //  result.success("444444")


                //startActivity(Intent(this@MainActivity,SecondActivity::class.java),"")

            }
            if(call.method=="sendata"){
                Toast.makeText(this,"sendata",Toast.LENGTH_LONG).show()
                result.success(dataresule)
               // demoBasicMessageChannel2("")
            }
        }
    }
//    fun sendData(){
//        Toast.makeText(this,"floast in android",Toast.LENGTH_LONG).show()
//        channel.invokeMethod("sendata", "Helllo")
//    }
//    private fun callFlutter(): Result<String> {
//        //binaryMessenger I construct is in onCreate
//        channel= MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger,channel_ping)
//        channel.invokeMethod("sendata", "Helllo")
//            //channel.run {  }
//        return Result.success("123123123")
//    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                // Get String data from Intent
                val returnString = data!!.getStringExtra("product")
               // Toast.makeText(this,"NGUYEN DUC TAI$returnString",Toast.LENGTH_LONG).show()
dataresule= returnString!!
                //channel.invokeMethod("sendata", "sss")

                  //  demoBasicMessageChannel2(returnString)

               sendMethodChanel()
                Log.i("da vao","name")
//                channel.setMethodCallHandler { call, result ->
//                    if(call.method=="sendata"){
//                        Toast.makeText(this,"sendata",Toast.LENGTH_LONG).show()
//                        result.success("dataasuces")
//                    }
//                }
               // configureFlutterEngine(FlutterEngine(this@MainActivity))


                // Set text view with string

            }
        }

    }



    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
    }
//    fun demoEventChannel() {
//        EventChannel(flutterEngine!!.dartExecutor.binaryMessenger, StreamChannel).setStreamHandler(
//            object : EventChannel.StreamHandler {
//                override fun onListen(args: Any, events: EventSink) {
//                    handler.postDelayed(buildCallBack(events), 1000)
//                }
//
//                override fun onCancel(args: Any) {}
//            }
//        )
//    }
//    fun buildCallBack( events:EventChannel.EventSink): Runnable {
//events.success("destroy")
//        Log.i("21312","destroy")
//        handler.postDelayed(callback, 1000);
//
//        return callback
//    }



    //var handler = Handler(Looper.getMainLooper())

    fun demoEventChannel() {
        EventChannel(flutterEngine!!.dartExecutor.binaryMessenger, "stream").setStreamHandler(
            object : EventChannel.StreamHandler {
                override fun onListen(args: Any?, events: EventSink?) {
                    //events?.success(true)
                    if (events != null) {
                        buildCallBack(events)?.let { handler.postDelayed(it, 1000) }
                    }
                }

                override fun onCancel(args: Any) {}
            }
        )
    }

    var i = 0
    //var callback: Runnable? = null

    fun buildCallBack(events: EventSink): Runnable? {
        if (callback == null) {
            callback = Runnable {
                events.success(dataresule)
                callback?.let { handler.postDelayed(it, 1000) }
            }
        }
        return callback
    }
    fun demoBasicMessageChannel2(result:String?) {
        val messageChannel = BasicMessageChannel(
            flutterEngine!!.dartExecutor.binaryMessenger,
            JSONMessageCodecChannel, JSONMessageCodec.INSTANCE
        )
        messageChannel.setMessageHandler { message, reply ->
            val jsonObject = JSONObject()
            try {
                jsonObject.put("phone", " 0973901999")
                jsonObject.put("email", "ryan@gmail.com")
            } catch (exp: Exception) {
            }
            messageChannel.send(jsonObject)
            reply.reply(null)
        }
    }


}
