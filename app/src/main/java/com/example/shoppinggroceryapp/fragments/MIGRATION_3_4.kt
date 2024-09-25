package com.example.shoppinggroceryapp.fragments

//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase

//val MIGRATION_1_2 = object :Migration(1,2){
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("CREATE TABLE recentlyViewedItems (recentlyViewedId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,productId INTEGER NOT NULL)")
//    }
//}

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//val MIGRATION_2_3 = object : Migration(2, 3) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        // Create a new table with the updated schema
//        db.execSQL("""
//            CREATE TABLE product_temp (
//                productId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                brandId INTEGER NOT NULL,
//                categoryName TEXT NOT NULL,
//                productName TEXT NOT NULL,
//                productDescription TEXT NOT NULL,
//                price REAL NOT NULL,
//                offer REAL NOT NULL,
//                productQuantity TEXT NOT NULL,
//                mainImage TEXT NOT NULL,
//                isVeg INTEGER NOT NULL,
//                manufactureDate TEXT NOT NULL,
//                expiryDate TEXT NOT NULL,
//                availableItems INTEGER NOT NULL
//            )
//        """)
//
//        // Copy data from the old table to the new table
//        db.execSQL("""
//            INSERT INTO product_temp (productId, brandId, categoryName, productName, productDescription, price, offer, productQuantity, mainImage, isVeg, manufactureDate, expiryDate, availableItems)
//            SELECT productId, brandId, categoryName, productName, productDescription, price,
//                   CAST(offer AS REAL),
//                   productQuantity,
//                   mainImage, isVeg, manufactureDate, expiryDate, availableItems
//            FROM product
//        """)
//
//        // Drop the old table
//        db.execSQL("DROP TABLE product")
//
//        // Rename the new table to the original table's name
//        db.execSQL("ALTER TABLE product_temp RENAME TO product")
//    }
//}

val MIGRATION_3_4 = object :Migration(3,4){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE RecentlyViewedItems ADD COLUMN userId INTEGER NOT NULL DEFAULT 0")
    }
}