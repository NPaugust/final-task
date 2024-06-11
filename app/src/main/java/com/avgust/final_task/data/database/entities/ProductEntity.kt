package com.avgust.final_task.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avgust.final_task.utils.Constants.Companion.PRODUCT_TABLE
import com.avgust.final_task.data.model.Products

@Entity(tableName = PRODUCT_TABLE)
class ProductEntity(
    var product: Products
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
