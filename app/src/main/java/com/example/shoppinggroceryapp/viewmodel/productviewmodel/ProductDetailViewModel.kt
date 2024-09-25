package com.example.shoppinggroceryapp.viewmodel.productviewmodel

import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductList
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItems

class ProductDetailViewModel(var retailerDao: RetailerDao):ViewModel() {



    var brandName:MutableLiveData<String> = MutableLiveData()
    var isCartAvailable:MutableLiveData<Cart> =MutableLiveData()
    var similarProductsLiveData:MutableLiveData<List<Product>> = MutableLiveData()
    var imageList:MutableLiveData<List<Images>> = MutableLiveData()
    var lock = Any()
    companion object{
        var brandLock = Any()
    }
    fun getBrandName(brandId:Long){
        Thread {
            synchronized(brandLock){
                brandName.postValue(retailerDao.getBrandName(brandId))
            }
        }.start()
    }

    fun addInRecentlyViewedItems(productId: Long){
        Thread {
            if(retailerDao.getProductsInRecentList(productId,MainActivity.userId.toInt())==null) {
                retailerDao.addProductInRecentlyViewedItems(RecentlyViewedItems(0, MainActivity.userId.toInt(),productId))
            }
        }.start()
    }

    fun getCartForSpecificProduct(cartId:Int,productId:Int){
        Thread{
            isCartAvailable.postValue(retailerDao.getSpecificCart(cartId,productId))
        }.start()
    }

    fun addProductInCart(cart:Cart){
        Thread{
            synchronized(lock){
                retailerDao.addItemsToCart(cart)
            }
        }.start()
    }

    fun updateProductInCart(cart:Cart){
        Thread{
            synchronized(lock){
                retailerDao.updateCartItems(cart)
            }
        }.start()
    }

    fun removeProductInCart(cart:Cart){
        Thread{
            synchronized(lock){
                retailerDao.removeProductInCart(cart)
            }
        }.start()
    }

    fun getSimilarProduct(category:String){
        Thread{
            similarProductsLiveData.postValue(retailerDao.getProductByCategory(category))
        }.start()
    }

    fun getImagesForProducts(productId: Long){
        Thread{
            println("IMAGE FOR PRODUCT: ${retailerDao.getImagesForProduct(productId)}")
            imageList.postValue(retailerDao.getImagesForProduct(productId))
        }.start()
    }
    fun removeProduct(product: Product){
        Thread {
            retailerDao.addDeletedProduct(DeletedProductList(productId = product.productId, brandId = product.brandId,
                categoryName = product.categoryName, productName = product.productName, productDescription = product.productDescription,
                price = product.price, offer = product.offer, productQuantity = product.productQuantity, mainImage = product.mainImage, isVeg = product.isVeg,
                manufactureDate = product.manufactureDate, expiryDate = product.expiryDate, availableItems = product.availableItems))
            retailerDao.deleteProduct(product)
        }.start()
    }

}