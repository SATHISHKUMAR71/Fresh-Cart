package com.example.shoppinggroceryapp.model.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinggroceryapp.model.dataclass.CustomerRequestWithName
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.DeletedProductList
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.model.entities.products.ProductWithCategory
import com.example.shoppinggroceryapp.model.entities.recentlyvieweditems.RecentlyViewedItems
import com.example.shoppinggroceryapp.model.entities.search.SearchHistory
import com.example.shoppinggroceryapp.model.entities.user.User
import com.example.shoppinggroceryapp.model.entities.user.Address

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User):Long

    @Query("SELECT * FROM Product WHERE categoryName IN (:categoryNames)")
    fun getProductsBySelectedCategories(categoryNames: List<String>): List<Product>


    @Query("SELECT productId FROM Product Where productId=1")
    fun initDB():Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSearchQueryInDb(searchHistory: SearchHistory)

    @Query("SELECT * FROM SearchHistory Where SearchHistory.userId=:userId")
    fun getSearchHistory(userId: Int):List<SearchHistory>

    @Query("SELECT * FROM RecentlyViewedItems")
    fun getRecentlyViewedItems():List<RecentlyViewedItems>

    @Delete
    fun deleteViewedItems(recentlyViewedItems: RecentlyViewedItems)

    @Query("SELECT RecentlyViewedItems.productId FROM RecentlyViewedItems Where userId=:user")
    fun getRecentlyViewedProducts(user:Int):List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(address: Address)

    @Update
    fun updateAddress(address: Address)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * FROM Address WHERE (Address.userId=:userId)")
    fun getAddressListForUser(userId:Int):List<Address>

    @Query("SELECT * FROM USER")
    fun getAllUsers():List<User>

    @Query("SELECT * FROM Address")
    fun getAllAddress():List<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCustomerRequest(customerRequest: CustomerRequest)

    @Query("SELECT * FROM CustomerRequest")
    fun getDataFromCustomerReq():List<CustomerRequest>

    @Query("SELECT CustomerRequest.helpId,CustomerRequest.userId,CustomerRequest.requestedDate,CustomerRequest.orderId,CustomerRequest.request,User.userFirstName,User.userLastName,User.userEmail,User.userPhone FROM CustomerRequest JOIN User ON User.userId=CustomerRequest.userId ORDER BY CustomerRequest.helpId DESC")
    fun getDataFromCustomerReqWithName():List<CustomerRequestWithName>

//    @Query("SELECT Product.* FROM Cart Join Product ON Product.productId = Cart.productId WHERE Cart.cartId=:cartId")
//    fun getProductsByCartIdLiveData(cartId:Int):LiveData<MutableList<Product>>

    @Query("SELECT * FROM Product Order By productId DESC")
    fun getOnlyProducts():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '50' Order By productId DESC")
    fun getOnlyProduct50():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '40' Order By productId DESC")
    fun getOnlyProduct40():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '30' Order By productId DESC")
    fun getOnlyProduct30():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '20' Order By productId DESC")
    fun getOnlyProduct20():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '10' Order By productId DESC")
    fun getOnlyProduct10():List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '50' and Product.categoryName=:category ")
    fun getOnlyProduct50WithCat(category:String):List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '40' and Product.categoryName=:category Order By productId DESC")
    fun getOnlyProduct40WithCat(category:String):List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '30' and Product.categoryName=:category Order By productId DESC")
    fun getOnlyProduct30WithCat(category:String):List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '20' and Product.categoryName=:category Order By productId DESC")
    fun getOnlyProduct20WithCat(category:String):List<Product>

    @Query("SELECT * FROM Product WHERE Product.offer >= '10' and Product.categoryName=:category Order By productId DESC")
    fun getOnlyProduct10WithCat(category:String):List<Product>

    @Query("SELECT * FROM PRODUCT")
    fun getOnlyProductsLiveData():LiveData<MutableList<Product>>

    @Query("SELECT * FROM user WHERE ((userEmail=:emailOrPhone OR userPhone=:emailOrPhone) AND (userPassword=:password))")
    fun getUser(emailOrPhone:String,password:String):User

    @Query("SELECT * FROM USER WHERE USER.userId=:userId")
    fun getUserById(userId: Int):User

    @Query("SELECT * FROM USER WHERE USER.userPhone=:phone")
    fun getUserByPhone(phone: String):User

    @Query("SELECT * FROM USER WHERE USER.userPassword=:password")
    fun getUserByPassword(password: String):User


    @Query("SELECT * FROM USER WHERE USER.userEmail=:email")
    fun getUserByEmail(email: String):User


    @Query("SELECT userFirstName FROM user WHERE (userId=:id)")
    fun getUserFirstName(id:Int):String

    @Query("SELECT userLastName FROM user WHERE (userId=:id)")
    fun getUserLastName(id:Int):String

    @Query("SELECT * FROM PRODUCT WHERE Product.productId==:productId")
    fun getProductById(productId:Long):Product

    @Query("SELECT * FROM DeletedProductList WHERE DeletedProductList.productId==:productId")
    fun getDeletedProductById(productId:Long):DeletedProductList

    @Query("SELECT * FROM user WHERE (user.userEmail=:emailOrPhone OR user.userPhone=:emailOrPhone)")
    fun getUserData(emailOrPhone:String):User


    @Query("SELECT * FROM Product WHERE Product.offer!='-1'")
    fun getOfferedProducts():List<Product>

    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.expiryDate DESC")
    fun getSortedExpiryHighProducts(query: String):List<Product>

    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.expiryDate ASC")
    fun getSortedExpiryLowProducts(query: String):List<Product>

    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.manufactureDate ASC")
    fun getSortedManufacturedLowProducts(query: String):List<Product>



    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.manufactureDate DESC")
    fun getSortedManufacturedHighProducts(query: String):List<Product>

    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.price DESC")
    fun getSortedPriceHighProducts(query: String):List<Product>

    @Query("SELECT * FROM Product WHERE (Product.categoryName =:query) ORDER BY Product.price ASC")
    fun getSortedPriceLowProducts(query: String):List<Product>

    @Query("SELECT * FROM Product ORDER BY Product.expiryDate DESC")
    fun getSortedExpiryHighProductsNoCat():List<Product>

    @Query("SELECT * FROM Product ORDER BY Product.expiryDate ASC")
    fun getSortedExpiryLowProductsNoCat():List<Product>

    @Query("SELECT * FROM Product ORDER BY Product.manufactureDate ASC")
    fun getSortedManufacturedLowProductsNoCat():List<Product>

    @Query("SELECT * FROM Product ORDER BY Product.manufactureDate DESC")
    fun getSortedManufacturedHighProductsNoCat():List<Product>

    @Query("SELECT * FROM Product WHERE offer>-1 ORDER BY Product.manufactureDate DESC")
    fun getSortedManufacturedHighProductsNoCatWithDiscount():List<Product>


    @Query("SELECT * FROM Product ORDER BY Product.price DESC")
    fun getSortedPriceHighProductsNoCat():List<Product>

    @Query("SELECT * FROM Product ORDER BY Product.price ASC")
    fun getSortedPriceLowProductsNoCat():List<Product>

    @Query("SELECT * FROM Product WHERE(Product.categoryName =:query)")
    fun getProductByCategory(query:String):List<Product>

    @Query("SELECT * FROM Product WHERE(PRODUCT.productName=:query)")
    fun getProductsByName(query: String):List<Product>

    @Query("SELECT * FROM Product WHERE(Product.categoryName =:query)")
    fun getProductByCategoryLiveData(query:String):LiveData<MutableList<Product>>

    @Query("SELECT * FROM User JOIN Address ON User.userId = Address.userId WHERE User.userId=:id")
    fun getAddressDetailsForUser(id:Int):Map<User,List<Address>>

    @Query("SELECT Category.categoryName FROM Category WHERE Category.categoryName LIKE '%' || :query || '%'")
    fun getProductForQuery(query: String):List<String>

    @Query("SELECT Product.productName FROM Product WHERE Product.productName LIKE '%' || :query || '%'")
    fun getProductForQueryName(query: String):List<String>

    @Query("SELECT * FROM Address WHERE Address.addressId=:addressId")
    fun getAddress(addressId:Int):Address

    @Query("SELECT Product.*,Category.parentCategoryName,Category.categoryDescription FROM Product JOIN Category ON Product.categoryName=Category.categoryName")
    fun getProducts():Map<Category,List<Product>>

    @Query("SELECT * FROM CartMapping WHERE CartMapping.userId=:userId and CartMapping.status='available'")
    fun getCartForUser(userId:Int):CartMapping

    @Insert
    fun addCartForUser(cartMapping:CartMapping)

    @Query("Select * from OrderDetails Join CartMapping on CartMapping.cartId=OrderDetails.cartId Where CartMapping.userId=:userId")
    fun getBoughtProductsList(userId: Int):List<OrderDetails>

    @Query("SELECT * From CartMapping Where CartMapping.userId=:userId and CartMapping.status!='available'")
    fun getCartId(userId:Int):List<CartMapping>

    @Query("SELECT * FROM Cart WHERE Cart.cartId=:cartId")
    fun getCartItems(cartId:Int):List<Cart>

    @Query("SELECT Product.* FROM Cart Join Product ON Product.productId = Cart.productId WHERE Cart.cartId=:cartId")
    fun getProductsByCartId(cartId:Int):List<Product>


    @Query("SELECT Product.* FROM Cart Join Product ON Product.productId = Cart.productId WHERE Cart.cartId=:cartId")
    fun getProductsByCartIdLiveData(cartId:Int):LiveData<MutableList<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItemsToCart(cart:Cart)

    @Query("SELECT Product.* FROM Cart JOIN Product ON Cart.productId=Product.productId WHERE Cart.cartId-:cartId")
    fun getCartWithProducts(cartId:Int):List<Product>


    @Query("SELECT Product.mainImage AS mainImage,Product.productName AS productName,Product.productDescription as productDescription,Cart.totalItems as totalItems,Cart.unitPrice as unitPrice,Product.manufactureDate AS manufactureDate" +
            ",Product.expiryDate as expiryDate,Product.productQuantity as productQuantity,BrandData.brandName as brandName FROM Cart Join Product ON Product.productId=Cart.productId JOIN BrandData ON BrandData.brandId=Product.brandId WHERE Cart.cartId=:cartId")
    fun getProductsWithCartId(cartId:Int):List<CartWithProductData>

    @Query("SELECT DeletedProductList.mainImage AS mainImage,DeletedProductList.productName AS productName,DeletedProductList.productDescription as productDescription,Cart.totalItems as totalItems,Cart.unitPrice as unitPrice,DeletedProductList.manufactureDate AS manufactureDate" +
            ",DeletedProductList.expiryDate as expiryDate,DeletedProductList.productQuantity as productQuantity,BrandData.brandName as brandName FROM Cart Join DeletedProductList ON DeletedProductList.productId=Cart.productId JOIN BrandData ON BrandData.brandId=DeletedProductList.brandId WHERE Cart.cartId=:cartId")
    fun getDeletedProductsWithCartId(cartId:Int):List<CartWithProductData>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE CartMapping.userId=:userID ORDER BY orderId DESC")
    fun getOrdersForUser(userID:Int):List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (CartMapping.userId=:userID and OrderDetails.deliveryFrequency='Weekly Once') ORDER BY orderId DESC")
    fun getOrdersForUserWeeklySubscription(userID:Int):List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (OrderDetails.deliveryFrequency='Weekly Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerWeeklySubscription():List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (CartMapping.userId=:userID and OrderDetails.deliveryFrequency='Daily') ORDER BY orderId DESC")
    fun getOrdersForUserDailySubscription(userID:Int):List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (OrderDetails.deliveryFrequency='Daily') ORDER BY orderId DESC")
    fun getOrdersRetailerDailySubscription():List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (CartMapping.userId=:userID and OrderDetails.deliveryFrequency='Monthly Once') ORDER BY orderId DESC")
    fun getOrdersForUserMonthlySubscription(userID:Int):List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (OrderDetails.deliveryFrequency='Monthly Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerMonthlySubscription():List<OrderDetails>

    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (CartMapping.userId=:userID and OrderDetails.deliveryFrequency='Once') ORDER BY orderId DESC")
    fun getOrdersForUserNoSubscription(userID:Int):List<OrderDetails>


    @Query("SELECT OrderDetails.* FROM OrderDetails JOIN CartMapping ON CartMapping.cartId=OrderDetails.cartId WHERE (OrderDetails.deliveryFrequency='Once') ORDER BY orderId DESC")
    fun getOrdersForRetailerNoSubscription():List<OrderDetails>

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.cartId=:cartId")
    fun getOrder(cartId:Int):OrderDetails

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.orderId=:orderId")
    fun getOrderWithOrderId(orderId:Int):OrderDetails

    @Query("SELECT OrderDetails.*,Product.mainImage AS mainImage,Product.productName AS productName,Product.productDescription as productDescription,Cart.totalItems as totalItems," +
            "Cart.unitPrice as unitPrice,Product.manufactureDate AS manufactureDate,Product.expiryDate as expiryDate,Product.productQuantity as productQuantity,BrandData.brandName as brandName FROM OrderDetails JOIN Cart ON Cart.cartId=OrderDetails.cartId JOIN Product ON Product.productId=Cart.productId JOIN BrandData ON BrandData.brandId=Product.brandId WHERE OrderDetails.cartId=:cartId")
    fun getOrderWithProducts(cartId: Int):Map<OrderDetails,List<CartWithProductData>>
    @Query("SELECT OrderDetails.*,Product.mainImage AS mainImage,Product.productName AS productName,Product.productDescription as productDescription,Cart.totalItems as totalItems," +
            "Cart.unitPrice as unitPrice,Product.manufactureDate AS manufactureDate,Product.expiryDate as expiryDate,Product.productQuantity as productQuantity,BrandData.brandName as brandName FROM OrderDetails JOIN Cart ON Cart.cartId=OrderDetails.cartId JOIN Product ON Product.productId=Cart.productId JOIN BrandData ON BrandData.brandId=Product.brandId WHERE OrderDetails.orderId=:orderId")
    fun getOrderWithProductsWithOrderId(orderId: Int):Map<OrderDetails,List<CartWithProductData>>

    @Update
    fun updateParentCategory(parentCategory: ParentCategory)

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.orderId=:orderId")
    fun getOrderDetails(orderId:Int):OrderDetails

    @Delete
    fun removeProductInCart(cart: Cart)

    @Update
    fun updateCartMapping(cartMapping: CartMapping)
    @Query("SELECT * FROM Cart WHERE Cart.cartId=:cartId and Cart.productId=:productId")
    fun getSpecificCart(cartId:Int,productId:Int):Cart
    @Update
    fun updateCartItems(cart: Cart)

}