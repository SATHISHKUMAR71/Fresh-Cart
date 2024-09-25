package com.example.shoppinggroceryapp.viewmodel.accountviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class OrderListViewModel(var retailerDao: RetailerDao):ViewModel() {

    var orderedItems:MutableLiveData<List<OrderDetails>> = MutableLiveData()
    var dataReady:MutableLiveData<Boolean> = MutableLiveData()
    private var lock =Any()
    var cartWithProductList:MutableLiveData<MutableList<MutableList<CartWithProductData>>> =
        MutableLiveData<MutableList<MutableList<CartWithProductData>>>().apply {
            value = mutableListOf()
        }

    fun getOrdersForSelectedUser(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForUser(userId))
        }.start()
    }

    fun getOrdersForSelectedUserWithNoSubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForUserNoSubscription(userId))
        }.start()
    }
    fun getOrdersForRetailerWithNoSubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForRetailerNoSubscription())
        }.start()
    }

    fun getOrdersForSelectedUserDailySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForUserDailySubscription(userId))
        }.start()
    }

    fun getOrdersForRetailerDailySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersRetailerDailySubscription())
        }.start()
    }

    fun getOrdersForSelectedUserWeeklySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForUserWeeklySubscription(userId))
        }.start()
    }

    fun getOrdersForRetailerWeeklySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForRetailerWeeklySubscription())
        }.start()
    }

    fun getOrdersForSelectedUserMonthlySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForUserMonthlySubscription(userId))
        }.start()
    }
    fun getOrdersForRetailerMonthlySubscription(userId:Int){
        Thread {
            orderedItems.postValue(retailerDao.getOrdersForRetailerMonthlySubscription())
        }.start()
    }

    fun getOrderedItemsForRetailer(){
        Thread{
            orderedItems.postValue(retailerDao.getOrderDetails())
        }.start()
    }

    fun getCartWithProducts(){
        Thread {
            for(i in orderedItems.value!!) {
                println("%%%%% Product added ${orderedItems.value?.size}")
                synchronized(lock) {
                    val tmpList = retailerDao.getProductsWithCartId(i.cartId).toMutableList()
                    tmpList.addAll(retailerDao.getDeletedProductsWithCartId(i.cartId))
                    cartWithProductList.value!!.add(
                        tmpList
                    )

                    for(j in tmpList){
                        println("00998877 PRODUCT NAME ${j.productName}")
                    }
                }
            }
            dataReady.postValue(true)
        }.start()
    }
}