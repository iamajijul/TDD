package com.ajijul.tdd.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(amount * price) FROM shopping_items")
    fun observeTotalPrice() : LiveData<Float>
}