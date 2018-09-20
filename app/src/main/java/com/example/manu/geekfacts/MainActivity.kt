package com.example.manu.geekfacts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import java.io.IOException
import java.net.URL
import android.app.ProgressDialog
import android.os.Handler

class MainActivity : AppCompatActivity() {
    var progress:ProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create a progress dialog to show the loading animation when retriving result from internet
        progress = ProgressDialog(this)
        progress!!.setTitle("Loading")
        progress!!.setMessage("loading new fact...")
        progress!!.setCancelable(false)
        loadFactButton.setOnClickListener{
            progress!!.show()
            //set default max time limit 5 sec after which progress dialog will disappear
            Handler().postDelayed({progress!!.dismiss()},5000)
            //call the method to retrive data from internet
            loadFact()
        }
    }
    fun loadFact(){
        doAsync {
            //Execute all the lon running tasks here
            //val s: String = makeNetworkCall()
            val s: String = try {
                URL("https://geek-jokes.sameerkumar.website/api")
                        .openStream()
                        .bufferedReader()
                        .use { it.readText() }
            } catch (e: IOException) {
                "Error with ${e.message}."
            }
            uiThread {
                //hide the progress dialog
                progress!!.dismiss()
                fact_textView.text = s
            }
        }
    }
}
