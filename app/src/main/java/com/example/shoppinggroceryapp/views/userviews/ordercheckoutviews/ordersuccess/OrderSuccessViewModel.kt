package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersuccess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.helpers.dategenerator.DateGenerator
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData

class OrderSuccessViewModel(var retailerDao: RetailerDao):ViewModel() {
    val lock = Any()
    var gotOrder:OrderDetails? = null
    var orderedId:MutableLiveData<Long> = MutableLiveData()
    var cartItems:List<CartWithProductData>? = null
    var newCart:MutableLiveData<CartMapping> = MutableLiveData()
    var orderWithCart:MutableLiveData<Map<OrderDetails,List<CartWithProductData>>> = MutableLiveData()
    fun placeOrder(cartId:Int,paymentMode:String,addressId:Int,deliveryStatus:String,paymentStatus:String,deliveryFrequency:String){
        Thread {
            synchronized(lock) {
                orderedId.postValue(retailerDao.addOrder(
                    OrderDetails(
                        0,
                        orderedDate = DateGenerator.getCurrentDate(),
                        deliveryDate = DateGenerator.getDeliveryDate(),
                        cartId = cartId,
                        paymentMode = paymentMode,
                        paymentStatus = paymentStatus,
                        addressId = addressId,
                        deliveryStatus = deliveryStatus,
                        deliveryFrequency = deliveryFrequency
                    )))
            }
        }.start()
    }

    fun getOrderAndCorrespondingCart(cartId:Int){
        Thread {
            synchronized(lock) {
                println(retailerDao.getOrder(cartId))
                orderWithCart.postValue(retailerDao.getOrderWithProductsWithOrderId(cartId))
            }

        }.start()
    }

    fun addMonthlySubscription(monthlyOnce: MonthlyOnce){
        Thread{
            retailerDao.addMonthlyOnceSubscription(monthlyOnce)
            getSubscriptionDetails()
        }.start()
    }

    fun addWeeklySubscription(weeklyOnce: WeeklyOnce){
        Thread{
            retailerDao.addWeeklyOnceSubscription(weeklyOnce)
            getSubscriptionDetails()
        }.start()
    }

    fun addDailySubscription(dailySubscription: DailySubscription){
        Thread{
            retailerDao.addDailySubscription(dailySubscription)
            getSubscriptionDetails()
        }.start()
    }

    fun addOrderToTimeSlot(timeSlot: TimeSlot){
        Thread{
            retailerDao.addTimeSlot(timeSlot)
        }.start()
    }

    fun getSubscriptionDetails(){
        for(i in retailerDao.getOrderTimeSlot()){
            println("FOR PRODUCTS ORDER ID ${i.orderId} TIME SLOTS: ${i.timeId}")
        }
        println("==========")
        for(i in retailerDao.getDailySubscription()){
            println("FOR PRODUCTS ORDER ID ${i.orderId} Daily Subscription ")
        }
        println("==========")
        for(i in retailerDao.getWeeklySubscriptionList()){
            println("FOR PRODUCTS ORDER ID ${i.orderId} week id ${i.weekId} Weekly Subscription ")
        }
        println("==========")
        for(i in retailerDao.getMonthlySubscriptionList()){
            println("FOR PRODUCTS ORDER ID ${i.orderId} month id ${i.dayOfMonth} Monthly Subscription ")
        }
    }
    fun updateAndAssignNewCart(cartId: Int,userId:Int){
        Thread {
            synchronized(lock) {
                retailerDao.updateCartMapping(CartMapping(cartId, userId, "not available"))
                retailerDao.addCartForUser(CartMapping(0, userId, "available"))
                newCart.postValue(retailerDao.getCartForUser(userId))
            }
        }.start()
    }
}