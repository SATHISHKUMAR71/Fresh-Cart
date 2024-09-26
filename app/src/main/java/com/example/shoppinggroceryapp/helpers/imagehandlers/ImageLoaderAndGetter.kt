package com.example.shoppinggroceryapp.helpers.imagehandlers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageLoaderAndGetter {


    fun storeImageInApp(context: Context, bitMap: Bitmap, fileName:String) {
        val fileDir = File(context.filesDir,"AppImages")
        if(!fileDir.exists()){
            fileDir.mkdirs()
        }
        try {
            val bitmapFile = File(fileDir, fileName)
            val fileOutputStream = FileOutputStream(bitmapFile)
            bitMap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        }
        catch (e:Exception){
        }
    }

    fun getImageInApp(context: Context,fileName: String):Bitmap?{
        val fileDir = File(context.filesDir,"AppImages")
        val bitmapFilePath = File(fileDir,fileName)
        try {
            val fileInputStream = FileInputStream(bitmapFilePath)
            return BitmapFactory.decodeStream(fileInputStream)
        }
        catch (e:Exception){
            return null
        }
    }

    fun deleteImageInApp(context: Context,fileName: String):Boolean{
        val fileDir = File(context.filesDir,"AppImages")
        val bitmapFilePath = File(fileDir,fileName)
        return bitmapFilePath.delete()
    }


}