package com.example.shoppinggroceryapp.viewmodel.productviewmodel

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
    fun getCartItems(cartId: Int) {
        Thread {
            cartList.postValue(userDao.getCartItems(cartId))
        }.start()
    }

    fun getOnlyProducts() {
        Thread {
            productList.postValue(userDao.getOnlyProducts())
            println("VIEW IS CREATING: Vlaues got in viewModel: ${productList.value?.size}")
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

}