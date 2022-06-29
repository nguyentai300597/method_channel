package com.example.demo_one

import android.app.Activity
import android.app.AppComponentFactory
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel

class SecondActivity:AppCompatActivity() {
    private val channel_past="pass_data"

    //private lateinit var binding: ActivitySecondBinding
//    private lateinit var binding: ActivityMainBinding

//    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
//        super.onCreate(savedInstanceState, persistentState)
//       /// val binding = ActivityPluginBinding
//
//        setContentView(R.layout.activity_second)
//        val btn_click_me = findViewById(R.id.txtview) as TextView
//
//        btn_click_me.setOnClickListener{
//            Toast.makeText(this,"hiiii",Toast.LENGTH_LONG).show()
//        }
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val btn_click_me = findViewById(R.id.txtview) as TextView
        val btn_send=findViewById<Button>(R.id.btnsend)
        val edit_Text=findViewById<EditText>(R.id.txteditname)as  EditText

        btn_click_me.setOnClickListener{
           // Toast.makeText(this,"hiiii",Toast.LENGTH_LONG).show()
          //  passProductAsResult()
        }
        btn_send.setOnClickListener{
            var nameEdit:String=""
            nameEdit=edit_Text.text.toString()
            passProductAsResult(nameEdit)

        }

    }
    private fun _passtdata(){
     //   channel= MethodChannel(FlutterEngine.dartExecutor.binaryMessenger,channel_ping)
        //MainActivity().sendData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun passProductAsResult(resultData:String) {
        val data = Intent()
        data.putExtra("product", resultData);

        setResult(Activity.RESULT_OK, data);
        finish()
    }


}