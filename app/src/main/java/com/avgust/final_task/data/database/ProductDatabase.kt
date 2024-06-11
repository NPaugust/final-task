package com.avgust.final_task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.avgust.final_task.data.database.entities.BasketEntity
import com.avgust.final_task.data.database.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, BasketEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProductTypeConverter::class)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao


}