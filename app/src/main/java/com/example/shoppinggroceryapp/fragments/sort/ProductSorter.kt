package com.example.shoppinggroceryapp.fragments.sort

import com.example.shoppinggroceryapp.model.entities.products.Product

class ProductSorter {

    fun sortByDate(productList:List<Product>):List<Product>{
        for(i in productList){
            println(i.manufactureDate)
        }
        var sortedList =productList.sortedWith(compareBy({it.manufactureDate},{it.productId}))
        println("Sorted Date")
        for(j in sortedList){
            println(j.manufactureDate)
        }
        return sortedList
    }
    fun sortByExpiryDate(productList:List<Product>):List<Product>{
        for(i in productList){
            println(i.expiryDate)
        }
        var sortedList =productList.sortedWith(compareBy({it.expiryDate},{it.productId}))
        println("Sorted Date")
        for(j in sortedList){
            println(j.expiryDate)
        }
        return sortedList
    }
    fun sortByDiscount(productList:List<Product>):List<Product>{
        for(i in productList){
            println(i.offer)
        }
        var sortedList =productList.sortedWith(compareBy({it.offer},{it.productId})).reversed()
        println("Sorted Date")
        for(j in sortedList){
            println(j.offer)
        }
        return sortedList
    }
    fun sortByPriceHighToLow(productList:List<Product>):List<Product>{
        for(i in productList){
            println(i.price)
        }
        var sortedList =productList.sortedWith(compareBy({it.price},{it.productId})).reversed()
        println("Sorted Price High to Low")
        for(j in sortedList){
            println(j.price)
        }
        return sortedList
    }

    fun sortByPriceLowToHigh(productList:List<Product>):List<Product>{
        for(i in productList){
            println(i.price)
        }
        var sortedList =productList.sortedWith(compareBy({it.price},{it.productId}))
        println("Sorted Price Low To High")
        for(j in sortedList){
            println(j.price)
        }
        return sortedList
    }
}