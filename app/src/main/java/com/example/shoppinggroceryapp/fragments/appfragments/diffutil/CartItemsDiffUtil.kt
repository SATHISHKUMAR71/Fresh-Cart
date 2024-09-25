package com.example.shoppinggroceryapp.fragments.appfragments.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinggroceryapp.model.entities.products.Product

class CartItemsDiffUtil(
    private val oldList:List<Product>,
    private val newList:List<Product>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return ((oldList[oldItemPosition]== newList[newItemPosition]))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

