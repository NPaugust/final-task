package com.avgust.final_task.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avgust.final_task.data.model.ProductsItem
import com.avgust.final_task.utils.Constants.Companion.BASKET_TABLE

@Entity(tableName = BASKET_TABLE)
class BasketEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var productsItem: ProductsItem
)
