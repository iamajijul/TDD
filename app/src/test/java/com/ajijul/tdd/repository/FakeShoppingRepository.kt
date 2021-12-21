package com.ajijul.tdd.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ajijul.tdd.data.local.ShoppingItem
import com.ajijul.tdd.data.remote.responses.ImageResponse
import com.ajijul.tdd.other.Resource

class FakeShoppingRepository : ShoppingRepository {

    var allShoppingItems = mutableListOf<ShoppingItem>()
    var observableShoppingItem = MutableLiveData<List<ShoppingItem>>(allShoppingItems)
    var observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value : Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshShoppingItems(){
        observableShoppingItem.postValue(allShoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return allShoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        allShoppingItems.add(shoppingItem)
        refreshShoppingItems()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        allShoppingItems.remove(shoppingItem)
        refreshShoppingItems()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImage(queryText: String): Resource<ImageResponse> {
       return if(shouldReturnNetworkError){
           Resource.error("ERROR",null)
       }else{
           Resource.success(ImageResponse(listOf(),0,0))
       }
    }
}