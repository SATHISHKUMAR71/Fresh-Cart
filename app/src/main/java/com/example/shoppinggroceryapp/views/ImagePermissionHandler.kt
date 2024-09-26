package com.example.shoppinggroceryapp.views

interface ImagePermissionHandler {
    fun initPermissionResult()
    fun checkPermission(isMultipleImage:Boolean)
}