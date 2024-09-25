package com.example.shoppinggroceryapp

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object  : Migration(4,5){
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE DeletedProductList (
                productId INTEGER PRIMARY KEY NOT NULL,
                brandId INTEGER NOT NULL,
                categoryName TEXT NOT NULL,
                productName TEXT NOT NULL,
                productDescription TEXT NOT NULL,
                price REAL NOT NULL,
                offer REAL NOT NULL,
                productQuantity TEXT NOT NULL,
                mainImage TEXT NOT NULL,
                isVeg INTEGER NOT NULL,
                manufactureDate TEXT NOT NULL,
                expiryDate TEXT NOT NULL,
                availableItems INTEGER NOT NULL
            )
        """.trimIndent())
    }
}