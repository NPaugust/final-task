package com.avgust.final_task.data.database

import androidx.room.TypeConverter
import com.avgust.final_task.data.model.Products
import com.avgust.final_task.data.model.ProductsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun productToString(product: Products): String{
        return gson.toJson(product)
    }

    @TypeConverter
    fun stringToProduct(data: String): Products {
        val typeOfList = object: TypeToken<Products>(){}.type
        return gson.fromJson(data,typeOfList)
    }


    //for basket database
    @TypeConverter
    fun productItemToString(productsItem: ProductsItem): String{
        return gson.toJson(productsItem)
    }

    @TypeConverter
    fun stringToProductItem(data: String): ProductsItem {
        val typeList = object : TypeToken<ProductsItem>(){}.type
        return gson.fromJson(data,typeList)
    }


}