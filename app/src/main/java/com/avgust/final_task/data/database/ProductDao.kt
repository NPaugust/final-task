package com.avgust.final_task.data.database

import androidx.room.*
import com.avgust.final_task.data.database.entities.BasketEntity
import com.avgust.final_task.data.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    //main product page
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM product_table ORDER BY id ASC")
    fun readProduct(): Flow<List<ProductEntity>>

    //search
    @Query("SELECT * FROM product_table WHERE product LIKE :searchQuery")
    fun searchDb(searchQuery: String): Flow<List<ProductEntity>>


    //basket page
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToBasket(basketEntity: BasketEntity)

    @Query("SELECT * FROM basket_table ORDER BY id ASC")
    fun readBasket(): Flow<List<BasketEntity>>

    @Delete
    suspend fun deleteProduct(basketEntity: BasketEntity)

    @Query("DELETE FROM basket_table")
    suspend fun clearBasket()

}