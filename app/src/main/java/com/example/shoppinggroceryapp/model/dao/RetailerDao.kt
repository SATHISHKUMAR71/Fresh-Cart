package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.TimeSlots
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.model.entities.products.BrandData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductList
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItems
import com.example.shoppinggroceryapp.model.entities.user.Address

@Dao
interface RetailerDao:UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParentCategory(parentCategory: ParentCategory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewBrand(brandData: BrandData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTimeSlot(timeSlot: TimeSlot)

    @Update
    fun updateTimeSlot(timeSlot: TimeSlot)


    @Delete
    fun deleteFromWeeklySubscription(weeklyOnce: WeeklyOnce)

    @Delete
    fun deleteFromMonthlySubscription(monthlyOnce: MonthlyOnce)

    @Delete
    fun deleteFromDailySubscription(dailySubscription: DailySubscription)

    @Query("SELECT * FROM DailySubscription")
    fun getDailySubscription():List<DailySubscription>

    @Query("SELECT * FROM TimeSlot")
    fun getOrderTimeSlot():List<TimeSlot>

    @Query("SELECT * FROM WeeklyOnce")
    fun getWeeklySubscriptionList():List<WeeklyOnce>

    @Query("SELECT * FROM MonthlyOnce")
    fun getMonthlySubscriptionList():List<MonthlyOnce>

    @Query("SELECT * FROM WeeklyOnce Where WeeklyOnce.orderId=:orderId")
    fun getOrderedDayForWeekSubscription(orderId:Int):WeeklyOnce

    @Query("SELECT * FROM DailySubscription Where DailySubscription.orderId=:orderId")
    fun getOrderForDailySubscription(orderId:Int):DailySubscription

    @Query("SELECT * FROM TimeSlot Where TimeSlot.orderId=:orderId")
    fun getOrderedTimeSlot(orderId:Int):TimeSlot

    @Query("SELECT * FROM MonthlyOnce Where MonthlyOnce.orderId=:orderId")
    fun getOrderedDayForMonthlySubscription(orderId:Int):MonthlyOnce

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMonthlyOnceSubscription(monthlyOnce: MonthlyOnce)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeeklyOnceSubscription(weeklyOnce: WeeklyOnce)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDailySubscription(dailySubscription: DailySubscription)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: Product)

    @Query("SELECT * FROM Product ORDER BY productId DESC")
    fun getLastProduct():Product

    @Query("SELECT * FROM branddata")
    fun getAllBrandData():List<BrandData>

    @Update
    fun updateProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProductInRecentlyViewedItems(recentlyViewedItem: RecentlyViewedItems)

    @Query("SELECT * FROM RecentlyViewedItems WHERE RecentlyViewedItems.productId=:productId and RecentlyViewedItems.userId=:user")
    fun getProductsInRecentList(productId:Long,user:Int):RecentlyViewedItems

    @Delete
    fun deleteRecentItems(recentlyViewedItem: RecentlyViewedItems)

    @Query("SELECT * FROM Images WHERE productId=:productId")
    fun getImagesForProduct(productId: Long):List<Images>

    @Query("SELECT * FROM Images WHERE Images.images=:image")
    fun getSpecificImage(image:String):Images

    @Delete
    fun deleteImage(images: Images)

    @Query("SELECT * FROM IMAGES")
    fun getAllImages():List<Images>

    @Query("SELECT * FROM OrderDetails ORDER BY orderId DESC")
    fun getOrderDetails():List<OrderDetails>

    @Insert
    fun addImagesInDb(images: Images)
    @Update
    fun updateOrderDetails(orderDetails: OrderDetails)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOrder(order:OrderDetails):Long

    @Delete
    fun deleteOrderDetails(order: OrderDetails)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDeletedProduct(deletedProductList: DeletedProductList)

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.cartId=:cartId")
    fun getOrderDetailsForSpecificCart(cartId:Int):List<OrderDetails>


    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT BrandData.brandName FROM BrandData where BrandData.brandId=:id")
    fun getBrandName(id:Long):String

    @Query("SELECT * FROM BrandData Where BrandData.brandName=:brandName")
    fun getBrandWithName(brandName:String):BrandData


    @Query("SELECT ParentCategory.parentCategoryImage FROM ParentCategory JOIN Category ON Category.parentCategoryName=ParentCategory.parentCategoryName Where categoryName=:parentCategoryName")
    fun getParentCategoryImage(parentCategoryName: String):String

    @Query("SELECT ParentCategory.parentCategoryImage FROM ParentCategory Where parentCategoryName=:parentCategoryName")
    fun getParentCategoryImageForParent(parentCategoryName: String):String

    @Query("SELECT * FROM ParentCategory WHERE ParentCategory.parentCategoryName=:parentCategoryName")
    fun getParentCategory(parentCategoryName:String):ParentCategory

    @Query("SELECT * FROM Category WHERE Category.categoryName=:subCategoryName")
    fun getSubCategory(subCategoryName:String):Category

}