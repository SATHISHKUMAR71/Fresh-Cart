package com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.model.entities.user.Address

class OrderDetailViewModel(var retailerDao: RetailerDao):ViewModel() {
    var selectedAddress:MutableLiveData<Address> = MutableLiveData()
    var date:MutableLiveData<Int> = MutableLiveData()
    var timeSlot:MutableLiveData<Int> = MutableLiveData()
    fun updateOrderDetails(orderDetails: OrderDetails){
        Thread{
            retailerDao.updateOrderDetails(orderDetails)
        }.start()
    }

    fun getSelectedAddress(addressId:Int){
        Thread{
            selectedAddress.postValue(retailerDao.getAddress(addressId))
        }.start()
    }


    fun deleteMonthly(orderId:Int){
        Thread {
            retailerDao.getOrderedDayForMonthlySubscription(
                orderId
            )?.let {
                retailerDao.deleteFromMonthlySubscription(it)
            }
        }.start()
    }

    fun deleteWeekly(orderId: Int){
        Thread{
            retailerDao.getOrderedDayForWeekSubscription(orderId)?.let {
                retailerDao.deleteFromWeeklySubscription(it)
            }
        }.start()
    }

    fun deleteDaily(orderId: Int){
        Thread{
            retailerDao.getOrderForDailySubscription(orderId)?.let {
                retailerDao.deleteFromDailySubscription(it)
            }
        }.start()
    }



    fun getSubscriptionDetails(){
        Thread {
            for (i in retailerDao.getOrderTimeSlot()) {
                println("FOR PRODUCTS ORDER ID ${i.orderId} TIME SLOTS: ${i.timeId}")
            }
            println("==========")
            for (i in retailerDao.getDailySubscription()) {
                println("FOR PRODUCTS ORDER ID ${i.orderId} Daily Subscription ")
            }
            println("==========")
            for (i in retailerDao.getWeeklySubscriptionList()) {
                println("FOR PRODUCTS ORDER ID ${i.orderId} week id ${i.weekId} Weekly Subscription ")
            }
            println("==========")
            for (i in retailerDao.getMonthlySubscriptionList()) {
                println("FOR PRODUCTS ORDER ID ${i.orderId} month id ${i.dayOfMonth} Monthly Subscription ")
            }
        }.start()
    }
    fun getMonthlySubscriptionDate(orderId:Int){
        Thread{
            retailerDao.getOrderedDayForMonthlySubscription(orderId)?.let {
                date.postValue(it.dayOfMonth)
            }
        }.start()
    }

    fun getWeeklySubscriptionDate(orderId:Int){
        Thread{
            retailerDao.getOrderedDayForWeekSubscription(orderId)?.let {
                date.postValue(it.weekId)
            }
        }.start()
    }

    fun getTimeSlot(orderId: Int){
        Thread{
            retailerDao.getOrderedTimeSlot(orderId)?.let {
                timeSlot.postValue(it.timeId)
            }
        }.start()
    }
}