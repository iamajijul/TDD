package com.ajijul.tdd.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    var name: String,
    var amount: Int,
    var price: Float,
    var imgUrl: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int
)
