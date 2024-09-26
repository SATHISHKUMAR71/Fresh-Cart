package com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails

class OrderListDiffUtil(
    private val oldList:MutableList<OrderDetails>,
    private val newList:MutableList<OrderDetails>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        println("@#@# ${oldList[oldItemPosition]==newList[newItemPosition]}")
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        println("@#@# ${oldList[oldItemPosition]==newList[newItemPosition]}")
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}

