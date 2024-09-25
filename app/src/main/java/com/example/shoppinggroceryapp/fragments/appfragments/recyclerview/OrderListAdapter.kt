package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.DateGenerator
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.Help
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.OrderDetailFragment
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.OrderListFragment
import com.example.shoppinggroceryapp.fragments.appfragments.diffutil.CartItemsDiffUtil
import com.example.shoppinggroceryapp.fragments.appfragments.diffutil.OrderListDiffUtil
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter.Companion.productList
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderListAdapter(var orderedItems:MutableList<OrderDetails>, var fragment:Fragment,var clickable:Boolean?):RecyclerView.Adapter<OrderListAdapter.OrderLayoutViewHolder>() {

    companion object{
        var cartWithProductList = mutableListOf<MutableList<CartWithProductData>>()
    }

    inner class OrderLayoutViewHolder(orderView:View):RecyclerView.ViewHolder(orderView){
        val productNames = orderView.findViewById<TextView>(R.id.orderedProductsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderLayoutViewHolder {
        return OrderLayoutViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return orderedItems.size
    }

    override fun onBindViewHolder(holder: OrderLayoutViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.deliveryDate).setTextColor(Color.BLACK)
        holder.itemView.findViewById<TextView>(R.id.deliveryDate).setBackgroundColor(Color.TRANSPARENT)
        if(orderedItems[position].deliveryStatus=="Pending"){
            val screen = "Expected On: ${DateGenerator.getDayAndMonth(orderedItems[position].deliveryDate)}"
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).text = screen
        }
        else if(orderedItems[position].deliveryStatus=="Cancelled"){
            val screen = "Order Cancelled"
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).setTextColor(Color.WHITE)
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).setBackgroundColor(Color.RED)
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).text = screen
        }
        else if(orderedItems[position].deliveryStatus=="Delivered"){
            val screen = "Delivered On: ${DateGenerator.getDayAndMonth(orderedItems[position].deliveryDate)}"
            holder.itemView.findViewById<TextView>(R.id.deliveryDate).text = screen
        }
        val date = "Ordered On: ${DateGenerator.getDayAndMonth(orderedItems[position].orderedDate)}"
        holder.itemView.findViewById<TextView>(R.id.orderedDate).text = date
        var productName=""
        var i =0
        for(cartWithProductData in cartWithProductList[position]){
            if(i==0){
                productName += cartWithProductData.productName+" (${cartWithProductData.totalItems}) ${cartWithProductData.productQuantity}"
                i=1
            }
            else{
                productName += " ,\n"+cartWithProductData.productName+" (${cartWithProductData.totalItems}) ${cartWithProductData.productQuantity}"
            }
        }
        holder.productNames.text = productName
        holder.itemView.setOnClickListener {
            if(clickable==true){
                Help.selectedOrder = orderedItems[position]
                println("ON CLICKED ")
                FragmentTransaction.navigateWithBackstack(fragment.parentFragmentManager,Help(),"Help")
//                fragment.parentFragmentManager.popBackStack()
//                FragmentTransaction.navigateWithBackstack(fragment.parentFragmentManager,fragment,"Help")
            }
            else {
                OrderListFragment.selectedOrder = orderedItems[position]
                OrderListFragment.correspondingCartList = cartWithProductList[position]
                fragment.parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    .replace(R.id.fragmentMainLayout, OrderDetailFragment())
                    .addToBackStack("Order Detail Fragment")
                    .commit()
            }
        }
    }

    fun setOrders(newList:MutableList<OrderDetails>){
        val orderDiffUtil = OrderListDiffUtil(orderedItems,newList)
        val orderDiffResults = DiffUtil.calculateDiff(orderDiffUtil)
        orderedItems.clear()
        orderedItems.addAll(newList)
        orderDiffResults.dispatchUpdatesTo(this)
    }
}