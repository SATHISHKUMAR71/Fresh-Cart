package com.example.shoppinggroceryapp.fragments

class InputValidator {


    companion object{
        fun checkMobile(num:String):Boolean{
            return (num.length>8)
        }

        fun checkEmail(email:String):Boolean{
            val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-za-z]{2,}$")
            return regex.matches(email)
        }
    }
}