package com.example.shoppinggroceryapp.fragments.authentication

import android.widget.AutoCompleteTextView
import com.example.shoppinggroceryapp.fragments.InputValidator
import com.google.android.material.textfield.TextInputEditText

interface InputChecker {
    fun nameCheck(text:TextInputEditText):String?
    fun emptyCheck(text: TextInputEditText):String?
    fun lengthAndEmptyCheck(textName:String,text: TextInputEditText,length:Int):String?
    fun lengthAndEmptyCheck(textName:String,text: AutoCompleteTextView,length:Int):String?
    fun lengthAndEmailCheck(text: TextInputEditText):String?
    fun emptyCheck(text: AutoCompleteTextView):String?
    fun lengthAndEmptyCheckForPhone(textName:String, text: TextInputEditText, length:Int):String?
}