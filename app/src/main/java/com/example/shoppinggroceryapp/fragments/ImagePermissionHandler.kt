package com.example.shoppinggroceryapp.fragments

import android.content.Context

interface ImagePermissionHandler {
    fun initPermissionResult()
    fun checkPermission(isMultipleImage:Boolean)
}