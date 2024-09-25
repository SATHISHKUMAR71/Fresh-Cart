package com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.inventoryviewmodel

import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.fragments.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductDetailFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.entities.products.BrandData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductDetailViewModel

class AddEditViewModel(var retailerDao: RetailerDao,var productDao: ProductDao):ViewModel() {

    var brandName:MutableLiveData<String> = MutableLiveData()
    var parentArray:MutableLiveData<Array<String>> = MutableLiveData()
    var imageList:MutableLiveData<List<Images>> = MutableLiveData()
    var parentCategory:MutableLiveData<String> = MutableLiveData()
    var childArray:MutableLiveData<Array<String>> = MutableLiveData()
    var categoryImage:MutableLiveData<String> = MutableLiveData()
    companion object {
        var modifiedProduct: MutableLiveData<Product> = MutableLiveData()
    }

    fun getBrandName(brandId:Long){
        Thread{
            synchronized(ProductDetailViewModel.brandLock) {
                brandName.postValue(retailerDao.getBrandName(brandId))
            }
        }.start()
    }

    fun getParentArray(){
        Thread{
            parentArray.postValue(productDao.getParentCategoryName())
        }.start()
    }

    fun getParentCategory(childName:String){
        Thread{
            println("PARENT CATEGORY CALLED $childName")
            parentCategory.postValue(productDao.getParentCategoryName(childName))
        }.start()
    }

    fun getChildArray(){
        Thread {
            childArray.postValue(productDao.getChildCategoryName())
        }.start()
    }

    fun getParentCategoryImage(parentCategoryName:String){
        Thread{
            println("### Parent Image:$parentCategoryName ${retailerDao.getParentCategoryImage(parentCategoryName)}")
            categoryImage.postValue(retailerDao.getParentCategoryImage(parentCategoryName))
        }.start()
    }

    fun getParentCategoryImageForParent(parentCategoryName:String){
        Thread{
            println("00990099 Parent Image:$parentCategoryName ${retailerDao.getParentCategoryImageForParent(parentCategoryName)}")
            categoryImage.postValue(retailerDao.getParentCategoryImageForParent(parentCategoryName))
        }.start()
    }

    fun getChildArray(parentName:String){
        Thread {
            childArray.postValue(productDao.getChildCategoryName(parentName))
        }.start()
    }

    fun addParentCategory(parentCategory: ParentCategory){
        Thread{
            println("00990099 IMAGE ${parentCategory.parentCategoryImage}")
            println("00990099 PARENT CATEGORY: ${parentCategory}")
            retailerDao.addParentCategory(parentCategory)
        }.start()
    }

    fun addSubCategory(category: Category){
        Thread{
            println("@@@@ SUB CATEGORY ADDED")
            retailerDao.addSubCategory(category)
        }.start()
    }

    fun getImagesForProduct(productId: Long){
        Thread{
            imageList.postValue(retailerDao.getImagesForProduct(productId))
        }.start()
    }

    fun updateInventory(brandName:String,isNewProduct:Boolean,product: Product,productId:Long?,imageList: List<String>,deletedImageList:MutableList<String>){
        var brand:BrandData
        Thread{
            synchronized(ProductDetailViewModel.brandLock) {
                brand = retailerDao.getBrandWithName(brandName)
                var prod:Product
                var lastProduct:Product
                if (brand == null) {
                    retailerDao.addNewBrand(BrandData(0, brandName))
                    brand = retailerDao.getBrandWithName(brandName)
                }
                if (isNewProduct) {
                    prod = product.copy(brandId = brand.brandId)
                    modifiedProduct.postValue(prod)
                    retailerDao.addProduct(prod)
                    lastProduct = retailerDao.getLastProduct()
                } else {
                    prod = product.copy(brandId = brand.brandId, productId = productId!!)
                    retailerDao.updateProduct(prod)
                    modifiedProduct.postValue(prod)
                    lastProduct = prod
                }

                println("LAST PRODUCT: $lastProduct")
//                for(j in retailerDao.getImagesForProduct(lastProduct.productId)){
//                    retailerDao.deleteImage(j)
//                }
                for(j in deletedImageList){
                    println("Images Added Deleted 9090")
                    retailerDao.deleteImage(retailerDao.getSpecificImage(j))
                }
                for(i in imageList){
                    retailerDao.addImagesInDb(Images(0,lastProduct.productId,i))
                    println("Images Added 9090")
                }
                ProductListFragment.selectedProduct.postValue(prod)
                println("00009999 Updated Product: $prod")
            }

        }.start()
    }



}