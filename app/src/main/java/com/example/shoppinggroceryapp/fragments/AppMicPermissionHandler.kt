package com.example.shoppinggroceryapp.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class AppMicPermissionHandler(var fragment: Fragment) :MicPermissionHandler {
    private var permissionAllowed = false
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var micIntent: Intent
    private lateinit var launchMicResults:ActivityResultLauncher<String>
    override fun initMicResults(){
        launchMicResults = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                activityResultLauncher.launch(micIntent)
            }
            else{
                Toast.makeText(fragment.context,"Please Enable Audio Permission to use Mic",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun checkMicPermission(micResultLauncher: ActivityResultLauncher<Intent>,micIntent: Intent):Boolean?{
        activityResultLauncher = micResultLauncher
        this.micIntent = micIntent
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permissionAllowed = true
            return true
        }
        else{
            launchMicResults.launch(Manifest.permission.RECORD_AUDIO)
            return null
        }
    }
}