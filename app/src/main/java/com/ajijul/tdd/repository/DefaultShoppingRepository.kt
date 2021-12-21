package com.ajijul.tdd.repository

import androidx.lifecycle.LiveData
import com.ajijul.tdd.data.local.ShoppingDao
import com.ajijul.tdd.data.local.ShoppingItem
import com.ajijul.tdd.data.remote.PixabayAPI
import com.ajijul.tdd.data.remote.responses.ImageResponse
import com.ajijul.tdd.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private var dao: ShoppingDao,
    private var pixaBay: PixabayAPI
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return dao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchImage(queryText: String): Resource<ImageResponse> {
        return try {
            val response = pixaBay.searchForImages(queryText)
            if (response.isSuccessful) {
                response?.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An Unknown error occured", null)
            } else Resource.error("An Unknown error occured", null)
        } catch (e: Exception) {
            Resource.error("Check internet connection", null)
        }
    }
}