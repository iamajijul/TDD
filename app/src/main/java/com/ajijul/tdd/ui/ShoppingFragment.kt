package com.ajijul.tdd.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ajijul.tdd.R

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {
    private val userListViewModel: ShoppingViewModel by viewModels()
}