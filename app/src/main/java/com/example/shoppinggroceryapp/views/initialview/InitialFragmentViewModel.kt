package com.example.shoppinggroceryapp.views.initialview

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity.Companion.isRetailer
import com.example.shoppinggroceryapp.MainActivity.Companion.userEmail
import com.example.shoppinggroceryapp.MainActivity.Companion.userFirstName
import com.example.shoppinggroceryapp.MainActivity.Companion.userId
import com.example.shoppinggroceryapp.MainActivity.Companion.userImage
import com.example.shoppinggroceryapp.MainActivity.Companion.userLastName
import com.example.shoppinggroceryapp.MainActivity.Companion.userPhone

class InitialDataSetter {


    fun setInitialDataForUser(pref:SharedPreferences){
        userFirstName = pref.getString("userFirstName","User").toString()
        userLastName = pref.getString("userLastName","User").toString()
        userId = pref.getString("userId","userId").toString()
        userEmail = pref.getString("userEmail","userEmail").toString()
        userPhone = pref.getString("userPhone","userPhone").toString()
        isRetailer = pref.getBoolean("isRetailer",false)
        userImage = pref.getString("userProfile","userProfile").toString()
    }
}