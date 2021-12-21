package com.ajijul.tdd.repository

import androidx.lifecycle.LiveData
import com.ajijul.tdd.data.local.ShoppingItem
import com.ajijul.tdd.data.remote.responses.ImageResponse
import com.ajijul.tdd.other.Resource

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>
    fun observeTotalPrice(): LiveData<Float>
    suspend fun searchImage(queryText: String): Resource<ImageResponse>
}