package com.example.shoppinggroceryapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData

class ImageHandler(var fragment:Fragment) {


    var gotImage:MutableLiveData<Bitmap> = MutableLiveData()
    private var previousImage = ""
    private lateinit var launchImage: ActivityResultLauncher<Intent>
    private lateinit var launchCamera: ActivityResultLauncher<Intent>
    fun initActivityResults() {
        launchImage =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    result.data?.let { data ->
                        if(data.clipData != null){
                            var imageUris = mutableListOf<Uri>()
                            for(i in 0..data.clipData!!.itemCount-1){
                                imageUris.add(data.clipData!!.getItemAt(i).uri)
                            }
                            val bitmaps = imageUris.mapNotNull {uri ->
                                try{
                                    val inputStream = fragment.requireContext().contentResolver.openInputStream(uri)
                                    BitmapFactory.decodeStream(inputStream)
                                }
                                catch (e:Exception){
                                    e.printStackTrace()
                                    null
                                }
                            }
                            for(i in bitmaps){
                                gotImage.value = i
                            }
                        }
                        else {
                            val image = data.data
                            val inputStream =
                                fragment.requireContext().contentResolver.openInputStream(image!!)
                            val img1 = BitmapFactory.decodeStream(inputStream)
                            if (img1 != null) {
                                gotImage.value = img1
                            }
                        }
                    }
                }
            }
        launchCamera =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val image = result.data?.extras?.get("data") as Bitmap
                    if(gotImage.value.toString() != previousImage){
                        gotImage.value = image
                    }
                    previousImage = image.toString()
                }
            }
    }
    fun showAlertDialog(isMultipleImage:Boolean) {
        AlertDialog.Builder(fragment.requireContext())
            .setTitle("Choose Image Source")
            .setItems(arrayOf("Camera","Gallery")){_,which ->
                when(which){
                    0 -> {
                        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        launchCamera.launch(i)
                    }
                    1 -> {
                        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        if(isMultipleImage) {
                            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        }
                        launchImage.launch(i)
                    }
                }
            }
            .show()
    }
    fun launchOnlyImage(isMultipleImage: Boolean){
        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if(isMultipleImage) {
            i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        launchImage.launch(i)
    }

}