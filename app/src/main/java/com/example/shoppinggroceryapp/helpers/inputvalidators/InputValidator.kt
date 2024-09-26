package com.example.shoppinggroceryapp.helpers.inputvalidators

class InputValidator {

    companion object{
        fun checkEmail(email:String):Boolean{
            val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,}$")
            return regex.matches(email)
        }
    }
}