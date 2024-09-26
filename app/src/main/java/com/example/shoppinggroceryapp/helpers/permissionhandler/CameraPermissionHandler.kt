package com.example.shoppinggroceryapp.helpers.permissionhandler

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.shoppinggroceryapp.helpers.imagehandlers.ImageHandler
import com.example.shoppinggroceryapp.helpers.permissionhandler.interfaces.ImagePermissionHandler

class CameraPermissionHandler(var fragment:Fragment, var imageHandler: ImageHandler?):
    ImagePermissionHandler {

    private lateinit var requestLauncher: ActivityResultLauncher<String>
    private var isMultipleImage = false
    override fun initPermissionResult(){
        requestLauncher = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
            if(isGranted){
                imageHandler!!.showAlertDialog(isMultipleImage)
            }
            else{
                imageHandler!!.showAlertDialog(isMultipleImage)
//                Toast.makeText(fragment.context,"Please Allow Camera Permission to take picture from device",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun checkPermission(isMultipleImage:Boolean) {
        this.isMultipleImage = isMultipleImage
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            imageHandler!!.showAlertDialog(isMultipleImage)
        }
        else{
            println("ON REQUEST Else")
            requestLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}