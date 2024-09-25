package com.example.shoppinggroceryapp.fragments.authentication

import android.widget.AutoCompleteTextView
import com.example.shoppinggroceryapp.fragments.InputValidator
import com.google.android.material.textfield.TextInputEditText

class TextLayoutInputChecker:InputChecker{
    override fun nameCheck(text: TextInputEditText):String?{
        if(text.text.toString().isEmpty()){
            return "This Field Should Not Be Empty"
        }
        else if(text.text.toString().length<3){
            return "Name Should Contain atLeast 3 Characters"
        }
        return null
    }

    override fun emptyCheck(text: TextInputEditText):String?{
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        return null
    }

    override fun emptyCheck(text: AutoCompleteTextView): String? {
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        return null
    }

    override fun lengthAndEmptyCheck(textName:String, text: TextInputEditText, length:Int):String?{
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        else if(text.text.toString().length<length){
            return "$textName should contain atLeast $length characters"
        }
        return null
    }
    override fun lengthAndEmptyCheckForPhone(textName:String, text: TextInputEditText, length:Int):String?{
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        else if(text.text.toString().length<length){
            return "$textName should contain atLeast $length characters"
        }
        else{

            for(i in text.text.toString()){
                if(i.code !in 48..57){
                    return "Invalid Phone Number ONly Numbers are Allowed"
                }
            }
        }
        return null
    }

    override fun lengthAndEmptyCheck(
        textName: String,
        text: AutoCompleteTextView,
        length: Int
    ): String? {
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        else if(text.text.toString().length<length){
            return "$textName should contain atLeast $length characters"
        }
        return null
    }

    override fun lengthAndEmailCheck(text: TextInputEditText):String?{
        if(text.text.toString().isEmpty()){
            return "This is the required field"
        }
        else if(!InputValidator.checkEmail(text.text.toString())){
            return "Please Enter Valid Email"
        }
        return null
    }
}