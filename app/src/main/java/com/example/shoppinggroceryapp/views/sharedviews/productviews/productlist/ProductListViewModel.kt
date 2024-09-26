package com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.products.Product

class ProductListViewModel(var userDao: UserDao):ViewModel() {

    var cartList: MutableLiveData<List<Cart>> = MutableLiveData()
    var productList: MutableLiveData<List<Product>> = MutableLiveData()
    var productCategoryList: MutableLiveData<List<Product>> = MutableLiveData()
    var manufacturedSortedList:MutableLiveData<List<Product>> = MutableLiveData()
    var cartListForProducts:MutableList<Cart?> = mutableListOf()
    fun getCartItems(cartId: Int) {
        Thread {
            cartList.postValue(userDao.getCartItems(cartId))
        }.start()
    }

    fun getOnlyProducts() {
        Thread {
            productList.postValue(userDao.getOnlyProducts())
        }.start()
    }


    fun getProductsByCategory(category: String) {
        Thread {
            var list = userDao.getProductByCategory(category)
            if(list.isEmpty()) {
                list = userDao.getProductsByName(category)
            }
            productCategoryList.postValue(list)
        }.start()
    }

    fun getSpecificCart(cartId: Int,productId:Int,callback: (Cart?) -> Unit){
        Thread{
            val cartData:Cart? = (userDao.getSpecificCart(cartId,productId))
            callback(cartData)
        }.start()
    }

    fun getBrandName(brandId:Long,callbackBrand: (String?) -> Unit){
        Thread{
            callbackBrand(userDao.getBrandName(brandId))
        }.start()
    }

    fun removeProductInCart(cart: Cart){
        Thread{
            userDao.removeProductInCart(cart)
        }.start()
    }

    fun updateItemsInCart(cart: Cart){
        Thread{
            userDao.addItemsToCart(cart)
        }.start()
    }

}