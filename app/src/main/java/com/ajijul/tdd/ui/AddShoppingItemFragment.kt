package com.ajijul.tdd.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ajijul.tdd.R

class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {
    private val userListViewModel: ShoppingViewModel by viewModels()
}