package com.example.shoppinggroceryapp.helpers.imagehandlers

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.example.shoppinggroceryapp.MainActivity
import java.io.File

class SetProductImage {

    companion object {
        fun setImageView(imageView: ImageView, url: String, file: File) {
            if (url.isNotEmpty()) {
                try {
                    if(MainActivity.imageCache.get(url)==null) {
                        println("ON ELSE IN SET IMAGE VIEW ON IF $url")
                        val imagePath = File(file, url)
                        val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath)
                        MainActivity.imageCache.put(url,bitmap)
                        imageView.setImageBitmap(bitmap)
                    }
                    else{
                        println("ON ELSE IN SET IMAGE VIEW ON ELSE $url")
                        imageView.setImageBitmap(MainActivity.imageCache.get(url))
                    }
                } catch (e: Exception) {
                    println("EXCEPTION: $e")
                }
            }
        }
    }
}