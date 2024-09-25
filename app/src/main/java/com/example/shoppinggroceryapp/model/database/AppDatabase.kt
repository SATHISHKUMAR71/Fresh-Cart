package com.example.shoppinggroceryapp.model.database

import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppinggroceryapp.MIGRATION_4_5
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.fragments.MIGRATION_3_4
import com.example.shoppinggroceryapp.model.dao.ProductDao
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.entities.deals.Deals
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest
import com.example.shoppinggroceryapp.model.entities.help.FAQ
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
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
import com.example.shoppinggroceryapp.model.entities.search.SearchHistory
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.example.shoppinggroceryapp.model.entities.user.User

@Database(entities = [User::class,Address::class, Product::class,Images::class,ParentCategory::class,Category::class,Deals::class,FAQ::class,CustomerRequest::class,BrandData::class,CartMapping::class,Cart::class,OrderDetails::class,RecentlyViewedItems::class,DailySubscription::class,DeletedProductList::class,SearchHistory::class,WeeklyOnce::class,TimeSlot::class,MonthlyOnce::class], version = 5)
abstract class AppDatabase:RoomDatabase(){

    abstract fun getUserDao():UserDao
    abstract fun getRetailerDao():RetailerDao
    abstract fun getProductDao():ProductDao

    companion object{
//        PREPOPULATED DATA WHEN THE DATABASE IS INSTALLED
        var categoryImages = listOf(R.drawable.fruits_vegetables,R.drawable.dairy_and_eggs,R.drawable.bakery_and_sancks,R.drawable.beverages,R.drawable.packaged_ffods,R.drawable.grains_and_pulses,
            R.drawable.meat_and_seafood,R.drawable.personnel_care,R.drawable.house_hold_items,R.drawable.health_and_wellness,R.drawable.baby_care,R.drawable.frozen_food,R.drawable.condiments_and_sauces,
            R.drawable.organic_food,R.drawable.dry_fruits_and_nuts,R.drawable.baking_needs,R.drawable.cooking_oils,R.drawable.break_fast_foods,R.drawable.international_foods,
            R.drawable.sweets_and_deserts,R.drawable.pet_foods)

        var categoryList = listOf(ParentCategory(parentCategoryName="Fruits & Vegetables", parentCategoryImage="1725879407899", parentCategoryDescription="Fresh fruits and vegetables", isEssential=true)
        ,ParentCategory(parentCategoryName="Dairy & Eggs", parentCategoryImage="1725879418133", parentCategoryDescription="Milk, cheese, butter, and eggs", isEssential=true)
        ,ParentCategory(parentCategoryName="Bakery & Snacks", parentCategoryImage="1725879426377", parentCategoryDescription="Bread, cakes, biscuits, and other snacks", isEssential=true)
        ,ParentCategory(parentCategoryName="Beverages", parentCategoryImage="1725879435009", parentCategoryDescription="Soft drinks, juices, tea, and coffee", isEssential=true)
        ,ParentCategory(parentCategoryName="Packaged Foods", parentCategoryImage="1725879447550", parentCategoryDescription="Canned, frozen, and processed foods", isEssential=true)
        ,ParentCategory(parentCategoryName="Grains & Pulses", parentCategoryImage="1725879497043", parentCategoryDescription="Rice, wheat, lentils, and beans", isEssential=true)
        ,ParentCategory(parentCategoryName="Meat & Seafood", parentCategoryImage="1725879505242", parentCategoryDescription="Fresh and frozen meat and seafood", isEssential=true)
        ,ParentCategory(parentCategoryName="Personal Care", parentCategoryImage="1725879510008", parentCategoryDescription="Toiletries, skincare, and grooming products", isEssential=false)
        ,ParentCategory(parentCategoryName="Household Items", parentCategoryImage="1725879520616", parentCategoryDescription="Cleaning supplies, kitchenware, and more", isEssential=false)
        ,ParentCategory(parentCategoryName="Health & Wellness", parentCategoryImage="1725879526823", parentCategoryDescription="Medicines, vitamins, and health supplements", isEssential=false)
        ,ParentCategory(parentCategoryName="Baby Care", parentCategoryImage="1725879534644", parentCategoryDescription="Diapers, baby food, and other baby products", isEssential=false)
        ,ParentCategory(parentCategoryName="Frozen Foods", parentCategoryImage="1725879542976", parentCategoryDescription="Frozen meals, ice cream, and frozen vegetables", isEssential=false)
        ,ParentCategory(parentCategoryName="Condiments & Sauces", parentCategoryImage="1725879549128", parentCategoryDescription="Spices, sauces, and cooking essentials", isEssential=false)
        ,ParentCategory(parentCategoryName="Organic Products", parentCategoryImage="1725879554877", parentCategoryDescription="Organic fruits, vegetables, and other products", isEssential=false)
        ,ParentCategory(parentCategoryName="Dry Fruits & Nuts", parentCategoryImage="1725879560595", parentCategoryDescription="Almonds, cashews, raisins, and other dry fruits", isEssential=false)
        ,ParentCategory(parentCategoryName="Baking Needs", parentCategoryImage="1725879566707", parentCategoryDescription="Flour, sugar, baking powder, and other baking supplies", isEssential=false)
        ,ParentCategory(parentCategoryName="Cooking Oils", parentCategoryImage="1725879573333", parentCategoryDescription="Vegetable oil, olive oil, and other cooking oils", isEssential=false)
        ,ParentCategory(parentCategoryName="Breakfast Foods", parentCategoryImage="1725879580164", parentCategoryDescription="Cereals, oats, and other breakfast items", isEssential=false)
        ,ParentCategory(parentCategoryName="International Foods", parentCategoryImage="1725879585454", parentCategoryDescription="Foods from various international cuisines", isEssential=false)
        ,ParentCategory(parentCategoryName="Sweets & Desserts", parentCategoryImage="1725879590878", parentCategoryDescription="Candies, chocolates, and desserts", isEssential=false)
        ,ParentCategory(parentCategoryName="Pet Supplies", parentCategoryImage="1725879597287", parentCategoryDescription="Pet food and accessories", isEssential=false),
        )
        var productImages= listOf(R.drawable.apples,R.drawable.tomatoes,R.drawable.organic_carrots,R.drawable.whole_milk,R.drawable.cheddar_cheese,R.drawable.clarified_butter,R.drawable.fresh_farm_eggs,R.drawable.whole_wheat_bread,R.drawable.chocolate_cake,
            R.drawable.butter_cookies,R.drawable.potato_chips,R.drawable.cola,R.drawable.orange_juice,R.drawable.green_tea,R.drawable.sports_drink,R.drawable.canned_beans,R.drawable.frozen_pizza,R.drawable.instant_noodles,
            R.drawable.packaged_popcorn,R.drawable.basmati_rice,R.drawable.whole_wheat_flour,R.drawable.red_lentils,R.drawable.bannanas,R.drawable.bell_peppers,R.drawable.organic_spinach,R.drawable.low_fat_milk,R.drawable.mozzeralla_cheese,R.drawable.butter,
            R.drawable.brown_eggs,R.drawable.multi_grain_bread,R.drawable.vanilla_cup_cakes,R.drawable.hide_and_seek,R.drawable.doritos,R.drawable.lemonade,R.drawable.apple_juice,
            R.drawable.black_tea,R.drawable.vitamin_energy_drink,R.drawable.cranberry,R.drawable.frozen_chicken_nuggets,R.drawable.instant_oat_meal,R.drawable.packaged_trail_mix,R.drawable.mangoes,
            R.drawable.salem_mangoes,R.drawable.organic_beets,R.drawable.almond_milk,R.drawable.parmeesan_cheese,R.drawable.salted_butter,R.drawable.free_range_eggs,R.drawable.rye_bread,
            R.drawable.strawberry_cheese_cake,R.drawable.oatmeal_raisin_cookie,R.drawable.sweet_potato_chips,R.drawable.orange_soda,R.drawable.grape_juice,
            R.drawable.herbal_tea,R.drawable.berry_energy_drink,R.drawable.sweet_corn,R.drawable.frozen_veggie_burger,R.drawable.knorr_manchow_soup,R.drawable.packaged_nuts)
        var productList = listOf(Product(productId=1, brandId=21, categoryName="Fresh Fruits", productName="Apples", productDescription="Fresh red apples", price=120.0f, offer=10.0f, productQuantity="1 kg", mainImage="1725857406264", isVeg=true, manufactureDate="2024-08-01", expiryDate="2024-08-15", availableItems=120)
            ,Product(productId=2, brandId=21, categoryName="Fresh Vegetables", productName="Tomatoes", productDescription="Fresh tomatoes", price=60.0f, offer=10.0f, productQuantity="1 kg", mainImage="1725857097807", isVeg=true, manufactureDate="2024-08-02", expiryDate="2024-08-10", availableItems=157)
            ,Product(productId=3, brandId=21, categoryName="Organic Produce", productName="Organic Carrots", productDescription="Fresh organic carrots", price=80.0f, offer=15.0f, productQuantity="1 kg", mainImage="1725857106715", isVeg=true, manufactureDate="2024-08-05", expiryDate="2024-08-12", availableItems=90)
            ,Product(productId=4, brandId=23, categoryName="Milk & Cream", productName="Whole Milk", productDescription="Fresh whole milk", price=50.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725606099343", isVeg=false, manufactureDate="2024-08-06", expiryDate="2024-08-20", availableItems=200)
            ,Product(productId=5, brandId=21, categoryName="Cheese", productName="Cheddar Cheese", productDescription="Aged cheddar cheese", price=250.0f, offer=10.0f, productQuantity="200 grams", mainImage="1725857115098", isVeg=false, manufactureDate="2024-08-07", expiryDate="2024-09-07", availableItems=59)
            ,Product(productId=6, brandId=23, categoryName="Butter & Ghee", productName="Clarified Butter (Ghee)", productDescription="Pure clarified butter", price=300.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725621735800", isVeg=true, manufactureDate="2024-08-08", expiryDate="2024-11-08", availableItems=77)
            ,Product(productId=7, brandId=21, categoryName="Eggs", productName="Farm Fresh Eggs", productDescription="Dozen of farm-fresh eggs", price=80.0f, offer=5.0f, productQuantity="12 eggs", mainImage="1725857123228", isVeg=false, manufactureDate="2024-08-09", expiryDate="2024-08-16", availableItems=123)
            ,Product(productId=8, brandId=21, categoryName="Bread", productName="Whole Wheat Bread", productDescription="Soft whole wheat bread", price=40.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725621787373", isVeg=true, manufactureDate="2024-08-10", expiryDate="2024-08-14", availableItems=100)
            ,Product(productId=9, brandId=21, categoryName="Cakes & Pastries", productName="Chocolate Cake", productDescription="Delicious chocolate cake", price=400.0f, offer=20.0f, productQuantity="1 kg", mainImage="1725857134800", isVeg=true, manufactureDate="2024-08-11", expiryDate="2024-08-18", availableItems=30)
            ,Product(productId=10, brandId=2, categoryName="Cookies & Biscuits", productName="Butter Cookies", productDescription="Crunchy butter cookies", price=120.0f, offer=-1.0f, productQuantity="200 grams", mainImage="1725621860191", isVeg=true, manufactureDate="2024-08-12", expiryDate="2024-09-12", availableItems=150)
            ,Product(productId=11, brandId=21, categoryName="Chips & Crisps", productName="Potato Chips", productDescription="Crispy potato chips", price=60.0f, offer=10.0f, productQuantity="100 grams", mainImage="1725857143070", isVeg=true, manufactureDate="2024-08-13", expiryDate="2024-09-13", availableItems=200)
            ,Product(productId=12, brandId=24, categoryName="Soft Drinks", productName="Cola", productDescription="Carbonated cola drink", price=50.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725623932319", isVeg=true, manufactureDate="2024-08-14", expiryDate="2025-01-14", availableItems=250)
            ,Product(productId=13, brandId=30, categoryName="Juices", productName="Orange Juice", productDescription="Fresh orange juice", price=80.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725623985234", isVeg=true, manufactureDate="2024-08-15", expiryDate="2024-09-15", availableItems=180)
            ,Product(productId=14, brandId=31, categoryName="Tea & Coffee", productName="Green Tea", productDescription="Healthy green tea", price=150.0f, offer=15.0f, productQuantity="100 grams", mainImage="1725857150219", isVeg=true, manufactureDate="2024-08-16", expiryDate="2025-02-16", availableItems=100)
            ,Product(productId=15, brandId=32, categoryName="Energy Drinks", productName="Sports Drink 200 ml", productDescription="Energy drink for sports", price=90.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725852186182", isVeg=true, manufactureDate="2024-08-17", expiryDate="2025-01-17", availableItems=80)
            ,Product(productId=16, brandId=33, categoryName="Canned Goods", productName="Canned Beans", productDescription="Canned kidney beans", price=75.0f, offer=-1.0f, productQuantity="400 grams", mainImage="1725624108553", isVeg=true, manufactureDate="2024-08-18", expiryDate="2026-08-18", availableItems=150)
            ,Product(productId=17, brandId=1, categoryName="Frozen Meals", productName="Frozen Pizza", productDescription="Frozen pepperoni pizza", price=250.0f, offer=10.0f, productQuantity="500 grams", mainImage="1725857157639", isVeg=true, manufactureDate="2024-08-19", expiryDate="2025-08-19", availableItems=61)
            ,Product(productId=18, brandId=34, categoryName="Instant Foods", productName="Instant Noodles", productDescription="Pack of instant noodles", price=30.0f, offer=15.0f, productQuantity="75 grams", mainImage="1725857164759", isVeg=true, manufactureDate="2024-08-20", expiryDate="2025-08-20", availableItems=300)
            ,Product(productId=19, brandId=35, categoryName="Ready-to-Eat Snacks", productName="Packaged Popcorn", productDescription="Ready-to-eat popcorn", price=90.0f, offer=-1.0f, productQuantity="200 grams", mainImage="1725624266938", isVeg=true, manufactureDate="2024-08-21", expiryDate="2025-08-21", availableItems=120)
            ,Product(productId=20, brandId=36, categoryName="Rice", productName="Basmati Rice", productDescription="Premium basmati rice", price=120.0f, offer=-1.0f, productQuantity="1 kg", mainImage="1725624313672", isVeg=true, manufactureDate="2024-08-22", expiryDate="2025-08-22", availableItems=152)
            ,Product(productId=21, brandId=37, categoryName="Wheat & Flour", productName="Whole Wheat Flour", productDescription="High-quality whole wheat flour", price=60.0f, offer=-1.0f, productQuantity="1 kg", mainImage="1725624349410", isVeg=true, manufactureDate="2024-08-23", expiryDate="2025-08-23", availableItems=200)
            ,Product(productId=22, brandId=21, categoryName="Pulses & Lentils", productName="Red Lentils", productDescription="Pack of red lentils", price=90.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725624425286", isVeg=true, manufactureDate="2024-08-24", expiryDate="2025-08-24", availableItems=180)
            ,Product(productId=23, brandId=21, categoryName="Fresh Fruits", productName="Bananas", productDescription="Ripe yellow bananas", price=50.0f, offer=-1.0f, productQuantity="1 dozen", mainImage="1725624446189", isVeg=true, manufactureDate="2024-08-25", expiryDate="2024-09-05", availableItems=206)
            ,Product(productId=24, brandId=21, categoryName="Fresh Vegetables", productName="Bell Peppers", productDescription="Mixed color bell peppers", price=90.0f, offer=5.0f, productQuantity="1 kg", mainImage="1725857178215", isVeg=true, manufactureDate="2024-08-26", expiryDate="2024-08-15", availableItems=130)
            ,Product(productId=25, brandId=21, categoryName="Organic Produce", productName="Organic Spinach", productDescription="Fresh organic spinach", price=70.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725624539321", isVeg=true, manufactureDate="2024-08-27", expiryDate="2024-08-10", availableItems=90)
            ,Product(productId=26, brandId=38, categoryName="Milk & Cream", productName="Low Fat Milk", productDescription="Low fat milk", price=55.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725624578269", isVeg=false, manufactureDate="2024-08-28", expiryDate="2024-09-05", availableItems=180)
            ,Product(productId=27, brandId=1, categoryName="Cheese", productName="Mozzarella Cheese", productDescription="Fresh mozzarella cheese", price=300.0f, offer=-1.0f, productQuantity="200 grams", mainImage="1725624609758", isVeg=false, manufactureDate="2024-08-29", expiryDate="2024-09-10", availableItems=40)
            ,Product(productId=28, brandId=39, categoryName="Butter & Ghee", productName="Butter", productDescription="Creamy butter", price=200.0f, offer=-1.0f, productQuantity="250 grams", mainImage="1725624625894", isVeg=true, manufactureDate="2024-08-30", expiryDate="2024-11-30", availableItems=60)
            ,Product(productId=29, brandId=21, categoryName="Eggs", productName="Brown Eggs", productDescription="Dozen of brown eggs", price=85.0f, offer=-1.0f, productQuantity="12 eggs", mainImage="1725624650475", isVeg=false, manufactureDate="2024-08-31", expiryDate="2024-09-15", availableItems=130)
            ,Product(productId=30, brandId=2, categoryName="Bread", productName="Multigrain Bread", productDescription="Healthy multigrain bread", price=50.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725624665195", isVeg=true, manufactureDate="2024-09-01", expiryDate="2024-09-07", availableItems=110)
            ,Product(productId=31, brandId=40, categoryName="Cakes & Pastries", productName="Vanilla Cupcakes", productDescription="Soft vanilla cupcakes", price=220.0f, offer=-1.0f, productQuantity="6 pieces", mainImage="1725624705739", isVeg=true, manufactureDate="2024-09-02", expiryDate="2024-09-10", availableItems=41)
            ,Product(productId=32, brandId=41, categoryName="Cookies & Biscuits", productName="Hide & Seek Chocolate Chip Cookies", productDescription="Cookies with chocolate chips", price=150.0f, offer=10.0f, productQuantity="200 grams", mainImage="1725857187511", isVeg=true, manufactureDate="2024-09-03", expiryDate="2024-10-03", availableItems=160)
            ,Product(productId=33, brandId=42, categoryName="Chips & Crisps", productName="Doritos - Flamin Hot Nachos", productDescription="Crunchy tortilla chips", price=70.0f, offer=-1.0f, productQuantity="150 grams", mainImage="1725624837072", isVeg=true, manufactureDate="2024-09-04", expiryDate="2024-10-04", availableItems=190)
            ,Product(productId=34, brandId=30, categoryName="Soft Drinks", productName="Lemonade", productDescription="Refreshing lemonade", price=55.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725624898342", isVeg=true, manufactureDate="2024-09-05", expiryDate="2025-03-05", availableItems=230)
            ,Product(productId=35, brandId=43, categoryName="Juices", productName="Apple Juice (pack of 2)", productDescription="100% pure apple juice", price=90.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725624956373", isVeg=true, manufactureDate="2024-09-06", expiryDate="2024-09-20", availableItems=200)
            ,Product(productId=36, brandId=44, categoryName="Tea & Coffee", productName="Black Tea", productDescription="Strong black tea", price=120.0f, offer=-1.0f, productQuantity="100 grams", mainImage="1725625015375", isVeg=true, manufactureDate="2024-09-07", expiryDate="2025-03-07", availableItems=110)
            ,Product(productId=37, brandId=45, categoryName="Energy Drinks", productName="Vitamin Energy Drink", productDescription="Vitamin enriched energy drink", price=100.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725625059575", isVeg=true, manufactureDate="2024-09-08", expiryDate="2025-03-08", availableItems=90)
            ,Product(productId=38, brandId=47, categoryName="Canned Goods", productName="Cranberry", productDescription="Canned ripe tomatoes", price=80.0f, offer=-1.0f, productQuantity="400 grams", mainImage="1725625131265", isVeg=true, manufactureDate="2024-09-09", expiryDate="2026-09-09", availableItems=170)
            ,Product(productId=39, brandId=5, categoryName="Frozen Meals", productName="Frozen Chicken Nuggets", productDescription="Frozen chicken nuggets", price=280.0f, offer=15.0f, productQuantity="400 grams", mainImage="1725857199096", isVeg=false, manufactureDate="2024-09-10", expiryDate="2025-09-10", availableItems=71)
            ,Product(productId=40, brandId=48, categoryName="Instant Foods", productName="Instant Oatmeal", productDescription="Quick cook oatmeal", price=40.0f, offer=10.0f, productQuantity="500 grams", mainImage="1725857206498", isVeg=true, manufactureDate="2024-09-11", expiryDate="2025-09-11", availableItems=300)
            ,Product(productId=41, brandId=49, categoryName="Ready-to-Eat Snacks", productName="Packaged Trail Mix", productDescription="Healthy trail mix", price=120.0f, offer=-1.0f, productQuantity="250 grams", mainImage="1725625280090", isVeg=true, manufactureDate="2024-09-12", expiryDate="2025-09-12", availableItems=100)
            ,Product(productId=42, brandId=21, categoryName="Fresh Fruits", productName="Mangoes", productDescription="Sweet and juicy mangoes", price=150.0f, offer=-1.0f, productQuantity="1 kg", mainImage="1725625322289", isVeg=true, manufactureDate="2024-09-13", expiryDate="2024-09-20", availableItems=86)
            ,Product(productId=43, brandId=21, categoryName="Fresh Vegetables", productName="Salem Mangoes", productDescription="Crisp cucumbers", price=50.0f, offer=-1.0f, productQuantity="1 kg", mainImage="1725625346396", isVeg=true, manufactureDate="2024-09-14", expiryDate="2024-09-21", availableItems=141)
            ,Product(productId=44, brandId=21, categoryName="Organic Produce", productName="Organic Beets", productDescription="Fresh organic beets", price=90.0f, offer=-1.0f, productQuantity="1 kg", mainImage="1725625398789", isVeg=true, manufactureDate="2024-09-15", expiryDate="2024-09-25", availableItems=75)
            ,Product(productId=45, brandId=1, categoryName="Milk & Cream", productName="Almond Milk", productDescription="Unsweetened almond milk", price=70.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725625451509", isVeg=true, manufactureDate="2024-09-16", expiryDate="2024-09-30", availableItems=190)
            ,Product(productId=46, brandId=1, categoryName="Cheese", productName="Parmesan Cheese", productDescription="Grated Parmesan cheese", price=350.0f, offer=-1.0f, productQuantity="150 grams", mainImage="1725625491455", isVeg=false, manufactureDate="2024-09-17", expiryDate="2024-10-17", availableItems=35)
            ,Product(productId=47, brandId=1, categoryName="Butter & Ghee", productName="Salted Butter", productDescription="Creamy salted butter", price=210.0f, offer=-1.0f, productQuantity="250 grams", mainImage="1725625516777", isVeg=true, manufactureDate="2024-09-18", expiryDate="2024-12-18", availableItems=70)
            ,Product(productId=48, brandId=21, categoryName="Eggs", productName="Free-Range Eggs", productDescription="Dozen of free-range eggs", price=90.0f, offer=-1.0f, productQuantity="12 eggs", mainImage="1725625552072", isVeg=false, manufactureDate="2024-09-19", expiryDate="2024-09-26", availableItems=140)
            ,Product(productId=49, brandId=21, categoryName="Bread", productName="Rye Bread", productDescription="Healthy rye bread", price=55.0f, offer=-1.0f, productQuantity="500 grams", mainImage="1725625593670", isVeg=true, manufactureDate="2024-09-20", expiryDate="2024-09-27", availableItems=120)
            ,Product(productId=50, brandId=50, categoryName="Cakes & Pastries", productName="Strawberry Cheesecake", productDescription="Delicious strawberry cheesecake", price=450.0f, offer=10.0f, productQuantity="1 kg", mainImage="1725625654989", isVeg=true, manufactureDate="2024-09-21", expiryDate="2024-09-28", availableItems=26)
            ,Product(productId=51, brandId=51, categoryName="Cookies & Biscuits", productName="Oatmeal Raisin Cookies", productDescription="Healthy oatmeal raisin cookies", price=130.0f, offer=-1.0f, productQuantity="200 grams", mainImage="1725625704005", isVeg=true, manufactureDate="2024-09-22", expiryDate="2024-10-22", availableItems=140)
            ,Product(productId=52, brandId=21, categoryName="Chips & Crisps", productName="Sweet Potato Chips", productDescription="Crispy sweet potato chips", price=75.0f, offer=-1.0f, productQuantity="150 grams", mainImage="1725692339160", isVeg=true, manufactureDate="2024-09-23", expiryDate="2024-10-23", availableItems=170)
            ,Product(productId=53, brandId=52, categoryName="Soft Drinks", productName="Orange Soda", productDescription="Refreshing orange soda", price=55.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725692393279", isVeg=true, manufactureDate="2024-09-24", expiryDate="2025-03-24", availableItems=210)
            ,Product(productId=54, brandId=53, categoryName="Juices", productName="Grape Juice", productDescription="Pure grape juice", price=85.0f, offer=-1.0f, productQuantity="1 liter", mainImage="1725692459367", isVeg=true, manufactureDate="2024-09-25", expiryDate="2024-10-05", availableItems=190)
            ,Product(productId=55, brandId=31, categoryName="Tea & Coffee", productName="Herbal Tea", productDescription="Calming herbal tea", price=140.0f, offer=-1.0f, productQuantity="100 grams", mainImage="1725692474100", isVeg=true, manufactureDate="2024-09-26", expiryDate="2025-03-26", availableItems=95)
            ,Product(productId=56, brandId=47, categoryName="Energy Drinks", productName="Berry Energy Drink", productDescription="Berry flavored energy drink", price=110.0f, offer=-1.0f, productQuantity="500 ml", mainImage="1725692500176", isVeg=true, manufactureDate="2024-09-27", expiryDate="2025-03-27", availableItems=85)
            ,Product(productId=57, brandId=54, categoryName="Canned Goods", productName="Sweet Corn", productDescription="Sweet canned corn", price=70.0f, offer=10.0f, productQuantity="400 grams", mainImage="1725857222482", isVeg=true, manufactureDate="2024-09-28", expiryDate="2026-09-28", availableItems=160)
            ,Product(productId=58, brandId=55, categoryName="Frozen Meals", productName="Frozen Veggie Burgers", productDescription="Frozen veggie burgers", price=300.0f, offer=10.0f, productQuantity="400 grams", mainImage="1725857230943", isVeg=true, manufactureDate="2024-09-29", expiryDate="2025-09-29", availableItems=65)
            ,Product(productId=59, brandId=56, categoryName="Instant Foods", productName="Knorr Manchow soup", productDescription="Instant soup mix", price=35.0f, offer=20.0f, productQuantity="100 grams", mainImage="1725857238861", isVeg=true, manufactureDate="2024-09-30", expiryDate="2025-09-30", availableItems=320)
            ,Product(productId=60, brandId=57, categoryName="Ready-to-Eat Snacks", productName="Packaged Nuts", productDescription="Healthy mixed nuts", price=130.0f, offer=-1.0f, productQuantity="200 grams", mainImage="1725692661163", isVeg=true, manufactureDate="2024-10-01", expiryDate="2025-10-01", availableItems=110))

        var childCategoryList = listOf(
            Category(categoryName="Fresh Fruits", parentCategoryName="Fruits & Vegetables", categoryDescription="All kinds of fresh fruits")
            ,
            Category(categoryName="Fresh Vegetables", parentCategoryName="Fruits & Vegetables", categoryDescription="All kinds of fresh vegetables")
            ,
            Category(categoryName="Organic Produce", parentCategoryName="Fruits & Vegetables", categoryDescription="Organic fruits and vegetables")
            ,
            Category(categoryName="Milk & Cream", parentCategoryName="Dairy & Eggs", categoryDescription="Different types of milk and cream")
            ,
            Category(categoryName="Cheese", parentCategoryName="Dairy & Eggs", categoryDescription="Various types of cheese")
            ,
            Category(categoryName="Butter & Ghee", parentCategoryName="Dairy & Eggs", categoryDescription="Butter, ghee, and other dairy spreads")
            ,
            Category(categoryName="Eggs", parentCategoryName="Dairy & Eggs", categoryDescription="Fresh eggs")
            ,
            Category(categoryName="Bread", parentCategoryName="Bakery & Snacks", categoryDescription="Various types of bread")
            ,
            Category(categoryName="Cakes & Pastries", parentCategoryName="Bakery & Snacks", categoryDescription="Cakes, pastries, and other baked goods")
            ,
            Category(categoryName="Cookies & Biscuits", parentCategoryName="Bakery & Snacks", categoryDescription="Cookies and biscuits")
            ,
            Category(categoryName="Chips & Crisps", parentCategoryName="Bakery & Snacks", categoryDescription="Packaged chips and crisps")
            ,
            Category(categoryName="Soft Drinks", parentCategoryName="Beverages", categoryDescription="Carbonated and non-carbonated soft drinks")
            ,
            Category(categoryName="Juices", parentCategoryName="Beverages", categoryDescription="Various fruit and vegetable juices")

            ,
            Category(categoryName="Tea & Coffee", parentCategoryName="Beverages", categoryDescription="Different types of tea and coffee")
            ,
            Category(categoryName="Energy Drinks", parentCategoryName="Beverages", categoryDescription="Energy and sports drinks")
            ,
            Category(categoryName="Canned Goods", parentCategoryName="Packaged Foods", categoryDescription="Canned vegetables, fruits, and meats")
            ,
            Category(categoryName="Frozen Meals", parentCategoryName="Packaged Foods", categoryDescription="Frozen ready-to-eat meals")
            ,
            Category(categoryName="Instant Foods", parentCategoryName="Packaged Foods", categoryDescription="Instant noodles, soups, and mixes")
            ,
            Category(categoryName="Ready-to-Eat Snacks", parentCategoryName="Packaged Foods", categoryDescription="Packaged snacks ready for consumption")
            ,
            Category(categoryName="Rice", parentCategoryName="Grains & Pulses", categoryDescription="Various types of rice")
            ,
            Category(categoryName="Wheat & Flour", parentCategoryName="Grains & Pulses", categoryDescription="Wheat, flour, and other grains")
            ,
            Category(categoryName="Lentils", parentCategoryName="Grains & Pulses", categoryDescription="Different types of lentils")
            ,
            Category(categoryName="Beans", parentCategoryName="Grains & Pulses", categoryDescription="Various beans including kidney beans, chickpeas")
            ,
            Category(categoryName="Fresh Meat", parentCategoryName="Meat & Seafood", categoryDescription="Fresh cuts of meat")
            ,
            Category(categoryName="Seafood", parentCategoryName="Meat & Seafood", categoryDescription="Various types of seafood")
            ,
            Category(categoryName="Frozen Meat", parentCategoryName="Meat & Seafood", categoryDescription="Frozen meat products")
            ,
            Category(categoryName="Processed Meat", parentCategoryName="Meat & Seafood", categoryDescription="Processed meats like sausages and cold cuts")
            ,
            Category(categoryName="Toiletries", parentCategoryName="Personal Care", categoryDescription="Items like soap, shampoo, and toothpaste")
            ,
            Category(categoryName="Skincare", parentCategoryName="Personal Care", categoryDescription="Skincare products including creams and lotions")
            ,
            Category(categoryName="Haircare", parentCategoryName="Personal Care", categoryDescription="Haircare products like conditioners and treatments")
            ,
            Category(categoryName="Grooming", parentCategoryName="Personal Care", categoryDescription="Grooming tools and products")
            ,
            Category(categoryName="Cleaning Supplies", parentCategoryName="Household Items", categoryDescription="Cleaning agents and tools")
            ,
            Category(categoryName="Kitchenware", parentCategoryName="Household Items", categoryDescription="Utensils, cookware, and other kitchen tools")
            ,
            Category(categoryName="Paper Products", parentCategoryName="Household Items", categoryDescription="Paper towels, toilet paper, and tissues")
            ,
            Category(categoryName="Laundry Products", parentCategoryName="Household Items", categoryDescription="Laundry detergents and fabric softeners")
            ,
            Category(categoryName="Medicines", parentCategoryName="Health & Wellness", categoryDescription="Over-the-counter medicines and health aids")
            ,
            Category(categoryName="Vitamins & Supplements", parentCategoryName="Health & Wellness", categoryDescription="Vitamins and dietary supplements")
            ,
            Category(categoryName="Health Foods", parentCategoryName="Health & Wellness", categoryDescription="Foods with health benefits like protein bars and shakes")
            ,
            Category(categoryName="First Aid", parentCategoryName="Health & Wellness", categoryDescription="First aid supplies and medical kits")
            ,
            Category(categoryName="Diapers", parentCategoryName="Baby Care", categoryDescription="Various types of diapers")
            ,
            Category(categoryName="Baby Food", parentCategoryName="Baby Care", categoryDescription="Packaged baby food and formula")
            ,
            Category(categoryName="Baby Toiletries", parentCategoryName="Baby Care", categoryDescription="Baby lotions, shampoos, and wipes")
            ,
            Category(categoryName="Baby Gear", parentCategoryName="Baby Care", categoryDescription="Baby accessories like bottles and pacifiers")
            ,
            Category(categoryName="Frozen Vegetables", parentCategoryName="Frozen Foods", categoryDescription="Frozen vegetables")
            ,
            Category(categoryName="Frozen Fruits", parentCategoryName="Frozen Foods", categoryDescription="Frozen fruits")
            ,
            Category(categoryName="Frozen Desserts", parentCategoryName="Frozen Foods", categoryDescription="Frozen desserts and ice cream")
            ,
            Category(categoryName="Frozen Seafood", parentCategoryName="Frozen Foods", categoryDescription="Frozen seafood products")
            ,
            Category(categoryName="Spices", parentCategoryName="Condiments & Sauces", categoryDescription="Various spices and seasonings")
            ,
            Category(categoryName="Cooking Sauces", parentCategoryName="Condiments & Sauces", categoryDescription="Sauces for cooking like soy sauce and tomato sauce")
            ,
            Category(categoryName="Ketchup & Mustard", parentCategoryName="Condiments & Sauces", categoryDescription="Condiments like ketchup and mustard")
            ,
            Category(categoryName="Pickles & Chutneys", parentCategoryName="Condiments & Sauces", categoryDescription="Various pickles and chutneys")
            ,
            Category(categoryName="Organic Fruits", parentCategoryName="Organic Products", categoryDescription="Organic fruits")
            ,
            Category(categoryName="Organic Vegetables", parentCategoryName="Organic Products", categoryDescription="Organic vegetables")
            ,
            Category(categoryName="Organic Grains", parentCategoryName="Organic Products", categoryDescription="Organic grains like rice and flour")
            ,
            Category(categoryName="Organic Snacks", parentCategoryName="Organic Products", categoryDescription="Organic snack foods")
            ,
            Category(categoryName="Almonds", parentCategoryName="Dry Fruits & Nuts", categoryDescription="Almonds and almond products")
            ,
            Category(categoryName="Cashews", parentCategoryName="Dry Fruits & Nuts", categoryDescription="Cashews and cashew products")
            ,
            Category(categoryName="Raisins", parentCategoryName="Dry Fruits & Nuts", categoryDescription="Raisins and other dried fruits")
            ,
            Category(categoryName="Mixed Nuts", parentCategoryName="Dry Fruits & Nuts", categoryDescription="Mixed nuts and nut blends")
            ,
            Category(categoryName="Flour", parentCategoryName="Baking Needs", categoryDescription="Different types of flour for baking")
            ,
            Category(categoryName="Sugar", parentCategoryName="Baking Needs", categoryDescription="Various types of sugar for baking")
            ,
            Category(categoryName="Cake Mixes", parentCategoryName="Baking Needs", categoryDescription="Pre-packaged cake mixes and baking kits")
            ,
            Category(categoryName="Vegetable Oil", parentCategoryName="Cooking Oils", categoryDescription="Vegetable oils for cooking")
            ,
            Category(categoryName="Olive Oil", parentCategoryName="Cooking Oils", categoryDescription="Olive oil for cooking and dressings")
            ,
            Category(categoryName="Sunflower Oil", parentCategoryName="Cooking Oils", categoryDescription="Sunflower oil for cooking")
            ,
            Category(categoryName="Ghee", parentCategoryName="Cooking Oils", categoryDescription="Clarified butter used in cooking")
            ,
            Category(categoryName="Cereals", parentCategoryName="Breakfast Foods", categoryDescription="Breakfast cereals including flakes and puffs")
            ,
            Category(categoryName="Oats", parentCategoryName="Breakfast Foods", categoryDescription="Oats and oatmeal products")
            ,
            Category(categoryName="Breakfast Bars", parentCategoryName="Breakfast Foods", categoryDescription="Granola bars and breakfast bars")
            ,
            Category(categoryName="Pancake Mixes", parentCategoryName="Breakfast Foods", categoryDescription="Mixes for making pancakes and waffles")
            ,
            Category(categoryName="Italian", parentCategoryName="International Foods", categoryDescription="Italian foods including pasta and sauces")
            ,
            Category(categoryName="Chinese", parentCategoryName="International Foods", categoryDescription="Chinese foods including noodles and sauces")
            ,
            Category(categoryName="Mexican", parentCategoryName="International Foods", categoryDescription="Mexican foods including tortillas and salsas")
            ,
            Category(categoryName="Indian", parentCategoryName="International Foods", categoryDescription="Indian foods including spices and ready-to-eat meals")
            ,
            Category(categoryName="Chocolates", parentCategoryName="Sweets & Desserts", categoryDescription="Various types of chocolates")
            ,
            Category(categoryName="Candies", parentCategoryName="Sweets & Desserts", categoryDescription="Assorted candies and confectioneries")
            ,
            Category(categoryName="Cookies", parentCategoryName="Sweets & Desserts", categoryDescription="Various types of cookies")
            ,
            Category(categoryName="Ice Creams", parentCategoryName="Sweets & Desserts", categoryDescription="Different flavors of ice cream")
            ,
            Category(categoryName="Pet Food", parentCategoryName="Pet Supplies", categoryDescription="Food for various pets")
            ,
            Category(categoryName="Pet Toys", parentCategoryName="Pet Supplies", categoryDescription="Toys and play items for pets")
            ,
            Category(categoryName="Pet Grooming", parentCategoryName="Pet Supplies", categoryDescription="Grooming supplies for pets")
            ,
            Category(categoryName="Pet Accessories", parentCategoryName="Pet Supplies", categoryDescription="Accessories like collars and leashes")
        )

        var brandList = listOf(BrandData(brandId=1, brandName="Amul")
            ,BrandData(brandId=2, brandName="Britannia")
        ,BrandData(brandId=3, brandName="Haldiram's")
        ,BrandData(brandId=4, brandName="Parle-G")
        ,BrandData(brandId=5, brandName="ITC")
        ,BrandData(brandId=6, brandName="Mother Dairy")
        ,BrandData(brandId=7, brandName="Saffola")
        ,BrandData(brandId=8, brandName="Nestlé India")
        ,BrandData(brandId=9, brandName="Patanjali")
        ,BrandData(brandId=10, brandName="MTR")
        ,BrandData(brandId=11, brandName="Goldiee")
        ,BrandData(brandId=12, brandName="Narmada")
        ,BrandData(brandId=13, brandName="Kwality Wall's")
        ,BrandData(brandId=14, brandName="Kissan")
        ,BrandData(brandId=15, brandName="Dabur")
        ,BrandData(brandId=16, brandName="Dhara")
        ,BrandData(brandId=17, brandName="Bingo")
        ,BrandData(brandId=18, brandName="Tata Salt")
        ,BrandData(brandId=19, brandName="Gits")
        ,BrandData(brandId=20, brandName="Pears")
        ,BrandData(brandId=21, brandName="Fresh Farm")
        ,BrandData(brandId=22, brandName="CocoCola")
        ,BrandData(brandId=23, brandName="Daily Dairy")
        ,BrandData(brandId=24, brandName="Coco Cola")
        ,BrandData(brandId=25, brandName="Colac")
        ,BrandData(brandId=26, brandName="COCO COLA")
        ,BrandData(brandId=27, brandName="COCOOLA")
        ,BrandData(brandId=28, brandName="OLA")
        ,BrandData(brandId=29, brandName="COLA")
        ,BrandData(brandId=30, brandName="Minute Maid")
        ,BrandData(brandId=31, brandName="Organic India")
        ,BrandData(brandId=32, brandName="Gatorade")
        ,BrandData(brandId=33, brandName="Bush's")
        ,BrandData(brandId=34, brandName="Master Chow")
        ,BrandData(brandId=35, brandName="Act II")
        ,BrandData(brandId=36, brandName="India Gate")
        ,BrandData(brandId=37, brandName="Ashirvad")
        ,BrandData(brandId=38, brandName="Peak")
        ,BrandData(brandId=39, brandName="Hatsun")
        ,BrandData(brandId=40, brandName="Ellite")
        ,BrandData(brandId=41, brandName="Parle")
        ,BrandData(brandId=42, brandName="Doritos")
        ,BrandData(brandId=43, brandName="B Natural")
        ,BrandData(brandId=44, brandName="Tetley")
        ,BrandData(brandId=45, brandName="Capitol Foods")
        ,BrandData(brandId=46, brandName="Cento")
        ,BrandData(brandId=47, brandName="Ocean Spray")
        ,BrandData(brandId=48, brandName="Manna")
        ,BrandData(brandId=49, brandName="Farmly")
        ,BrandData(brandId=50, brandName="Ibacco")
        ,BrandData(brandId=51, brandName="Nestle")
        ,BrandData(brandId=52, brandName="Crush")
        ,BrandData(brandId=53, brandName="Tropicana")
        ,BrandData(brandId=54, brandName="Urban Platter")
        ,BrandData(brandId=55, brandName="McCain")
        ,BrandData(brandId=56, brandName="Knorr")
        ,BrandData(brandId=57, brandName="Deluxe")
        ,BrandData(brandId=58, brandName="Britannia"))
        var userList = listOf(User(0,"","Sathish Kumar","B","sathishkumar@shop.com","1234567890","123","",false),
            User(0,"","Admin","","admin@shop.com","8098102719","123","",true))

        @Volatile
        private var INSTANCE:AppDatabase? = null
        fun getAppDatabase(context: Context):AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fresh_cart_database")
                    .addMigrations(MIGRATION_4_5)
                    .addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            println("DB CALLED")
                            var dbRetailer = getAppDatabase(context).getRetailerDao()
                            var dbUser = getAppDatabase(context).getUserDao()
                            Thread {
                                for (j in brandList) {
                                    dbRetailer.addNewBrand(j)
                                }
                            }.start()
                            Thread {
                                for (k in userList) {
                                    println("USerList Added")
                                    dbUser.addUser(k)
                                }
                            }.start()
                            Thread {
                                val imageLoader = ImageLoaderAndGetter()
                                var productNo = 0
                                for (i in productList) {
                                    val fileName = System.currentTimeMillis().toString()
                                    imageLoader.storeImageInApp(
                                        context, BitmapFactory.decodeResource(
                                            context.resources,
                                            productImages[productNo]
                                        ), fileName
                                    )
                                    dbRetailer.addProduct(i.copy(mainImage = fileName))
                                    println("Products Added $productNo $fileName")
                                    productNo++
                                }
//                                   val imageLoader = ImageLoaderAndGetter()
                                var drawableNo = 0
                                for (j in categoryList) {
                                    val fileName = System.currentTimeMillis().toString()
                                    println("$$$$$ ITEM BYTE COUNT:${BitmapFactory.decodeResource(context.resources, categoryImages[drawableNo]).byteCount} ${j.parentCategoryName}")
                                    imageLoader.storeImageInApp(
                                        context, BitmapFactory.decodeResource(
                                            context.resources,
                                            categoryImages[drawableNo]
                                        ), fileName
                                    )
                                    dbRetailer
                                        .addParentCategory(j.copy(parentCategoryImage = fileName))
//                                        dbRetailer.addParentCategory(j)
                                    println("Images are Adding $drawableNo $fileName")
                                    drawableNo++
                                }
                            }.start()
                            Thread {
                                for (k in childCategoryList) {
                                    getAppDatabase(context).getRetailerDao().addSubCategory(k)
                                }
                            }.start()
//                            }.start()
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }


}