package com.example.imgviewbitmaprp1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.concurrent.thread

const val img_url =
    "https://upload.wikimedia.org/wikipedia/commons/f/f9/Phoenicopterus_ruber_in_S%C3%A3o_Paulo_Zoo.jpg"

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imgView)
        // type 1 glide
        //Glide.with(this).load(img_url).into(imageView)
        //type 2 thread and handler
//        thread(start = true) {
//            val bitmap = convertToBitmap()
//            val handler = Handler(Looper.getMainLooper())
//            handler.post {
//                imageView.setImageBitmap(bitmap)
//            }
//        }
        //type 3 coroutines
//        CoroutineScope(Dispatchers.IO).launch {
//            val bitmap = convertToBitmap()
//            withContext(Dispatchers.Main) {
//                imageView.setImageBitmap(bitmap)
//            }
//        }
        //4 network on main thread
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitNetwork().build())
        val bitmap = convertToBitmap()
        imageView.setImageBitmap(bitmap)

    }

    private fun convertToBitmap(): Bitmap? {
        val conn = URL(img_url).openConnection()
        return try {
            conn.connect()
            val inputStream = conn.getInputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}