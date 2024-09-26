package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersummary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class OrderSummaryViewModel(var retailerDao: RetailerDao):ViewModel() {

    var cartItems:MutableLiveData<List<CartWithProductData>> = MutableLiveData()
    fun getProductsWithCartId(cartId:Int){
        Thread{
            cartItems.postValue(retailerDao.getProductsWithCartId(cartId))
        }.start()
    }

    fun updateOrderDetails(orderDetails: OrderDetails){
        Thread{
            retailerDao.updateOrderDetails(orderDetails)
        }.start()
    }
    fun updateTimeSlot(timeSlot: TimeSlot){
        Thread{
            retailerDao.updateTimeSlot(timeSlot)
        }.start()
    }
    fun updateMonthly(monthlyOnce: MonthlyOnce){
        Thread{
            retailerDao.addMonthlyOnceSubscription(monthlyOnce)
            deleteDaily(monthlyOnce.orderId)
            deleteWeekly(monthlyOnce.orderId)
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
    fun updateDaily(dailySubscription: DailySubscription){
        Thread{
            retailerDao.addDailySubscription(dailySubscription)
            deleteWeekly(dailySubscription.orderId)
            deleteMonthly(dailySubscription.orderId)
        }.start()
    }

    fun updateWeekly(weeklyOnce: WeeklyOnce){
        Thread{
            retailerDao.addWeeklyOnceSubscription(weeklyOnce)
            deleteDaily(weeklyOnce.orderId)
            deleteMonthly(weeklyOnce.orderId)
        }.start()
    }

    fun deleteDaily(orderId: Int){
        Thread{
            retailerDao.getOrderForDailySubscription(orderId)?.let {
                retailerDao.deleteFromDailySubscription(it)
            }
        }.start()
    }
}