package com.example.shoppinggroceryapp.fragments

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface MicPermissionHandler {
    fun initMicResults()
    fun checkMicPermission(micResultLauncher: ActivityResultLauncher<Intent>, micIntent: Intent):Boolean?
}