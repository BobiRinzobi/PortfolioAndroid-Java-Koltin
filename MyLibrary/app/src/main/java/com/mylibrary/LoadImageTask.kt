package com.mylibrary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.URL

class LoadImageTask(private val imageView: ImageView)  {

    fun execute(vararg urls: String) {
        Thread {
            val bitmap = doInBackground(*urls)

            imageView.post {
                onPostExecute(bitmap)
            }
        }.start()
    }

    fun doInBackground(vararg urls: String): Bitmap? {
        return try {
            val url = URL(urls[0])
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            BitmapFactory.decodeStream(connection.inputStream)
        } catch (e: Exception) {
            null
        }
    }
    fun onPostExecute(result: Bitmap?) {
        if (result != null) {
            imageView.setImageBitmap(result)
        }
    }
}