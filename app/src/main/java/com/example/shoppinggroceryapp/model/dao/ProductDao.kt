package com.example.shoppinggroceryapp.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinggroceryapp.model.dataclass.ChildCategoryName
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItems
import com.example.shoppinggroceryapp.model.entities.user.User

@Dao
interface ProductDao:UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addParentCategory(parentCategory: ParentCategory)

    @Query("SELECT Category.parentCategoryName FROM Category WHERE Category.categoryName=:childName")
    fun getParentCategoryName(childName:String):String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSubCategory(category: Category)

    @Query("Select * from ParentCategory")
    fun getAllParentCategory():List<ParentCategory>

    @Query("SELECT * FROM CATEGORY")
    fun getAllCategory():List<Category>

    @Query("SELECT * FROM ParentCategory")
    fun getParentCategoryList():List<ParentCategory>



//    @Query("SELECT ParentCategory.*,Category.categoryName FROM ParentCategory JOIN Category ON Category.parentCategoryName=ParentCategory.parentCategoryName")
//    fun getParentWithChildName():List<ParentCategoryWithChildName>

    @Query("SELECT Category.categoryName FROM Category Where Category.parentCategoryName=:parent")
    fun getChildName(parent:String):List<ChildCategoryName>

    @Query("SELECT ParentCategory.parentCategoryName FROM ParentCategory")
    fun getParentCategoryName():Array<String>

    @Query("SELECT Category.categoryName FROM Category")
    fun getChildCategoryName():Array<String>

    @Query("SELECT Category.categoryName FROM Category Where Category.parentCategoryName=:parentName")
    fun getChildCategoryName(parentName:String):Array<String>

    @Query("SELECT * FROM Category WHERE Category.parentCategoryName=:parentCategoryName")
    fun getChildCategoryList(parentCategoryName:String):List<Category>

    @Query("SELECT * FROM ParentCategory JOIN Category ON Category.parentCategoryName=ParentCategory.parentCategoryName")
    fun getAllCategoryAndParentCategory():Map<ParentCategory,List<Category>>

}