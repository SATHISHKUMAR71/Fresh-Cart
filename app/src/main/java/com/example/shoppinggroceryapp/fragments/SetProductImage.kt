package com.example.shoppinggroceryapp.fragments

import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.File

class SetProductImage {

    companion object {
        fun setImageView(imageView: ImageView, url: String, file: File) {
            if (url.isNotEmpty()) {
                try {
                    val imagePath = File(file, url)
                    val bitmap = BitmapFactory.decodeFile(imagePath.absolutePath)
                    imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    println("EXCEPTION: $e")
                }
            }
        }
    }
}