package com.example.shoppinggroceryapp.views.sharedviews.sort

import com.example.shoppinggroceryapp.model.entities.products.Product

class ProductSorter {

    fun sortByDate(productList:List<Product>):List<Product>{
        val sortedList =productList.sortedWith(compareBy({it.manufactureDate},{it.productId}))
        return sortedList
    }
    fun sortByExpiryDate(productList:List<Product>):List<Product>{
        val sortedList =productList.sortedWith(compareBy({it.expiryDate},{it.productId}))
        return sortedList
    }
    fun sortByDiscount(productList:List<Product>):List<Product>{
        val sortedList =productList.sortedWith(compareBy({it.offer},{it.productId})).reversed()
        return sortedList
    }
    fun sortByPriceHighToLow(productList:List<Product>):List<Product>{
        val sortedList =productList.sortedWith(compareBy({it.price},{it.productId})).reversed()
        return sortedList
    }

    fun sortByPriceLowToHigh(productList:List<Product>):List<Product>{
        val sortedList =productList.sortedWith(compareBy({it.price},{it.productId}))
        return sortedList
    }
}