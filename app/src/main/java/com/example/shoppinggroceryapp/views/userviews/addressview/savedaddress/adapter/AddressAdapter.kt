package com.example.shoppinggroceryapp.views.userviews.addressview.savedaddress.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.fragmenttransaction.FragmentTransaction
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.example.shoppinggroceryapp.views.userviews.addressview.getaddress.GetNewAddress
import com.example.shoppinggroceryapp.views.userviews.addressview.savedaddress.SavedAddressList
import com.example.shoppinggroceryapp.views.userviews.cartview.cart.CartFragment
import com.google.android.material.button.MaterialButton

class AddressAdapter(var addressList: List<Address>,var fragment: Fragment):RecyclerView.Adapter<AddressAdapter.AddressHolder>() {

    companion object{
        var clickable = false
    }
    inner class AddressHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val contactName = itemView.findViewById<TextView>(R.id.addressOwnerName)
        val address = itemView.findViewById<TextView>(R.id.address)
        val contactNumber = itemView.findViewById<TextView>(R.id.addressPhone)
        val editAddress = itemView.findViewById<MaterialButton>(R.id.editAddressButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressHolder {
        return AddressHolder(LayoutInflater.from(parent.context).inflate(R.layout.address_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: AddressHolder, position: Int) {
        val address = "${addressList[position].buildingName}, ${addressList[position].streetName}," +
                "${addressList[position].city}, ${addressList[position].state}, ${addressList[position].postalCode}"
            holder.address.text = address
            holder.contactName.text = addressList[position].addressContactName
            holder.contactNumber.text = addressList[position].addressContactNumber

        holder.editAddress.setOnClickListener {
            SavedAddressList.editAddress = addressList[position]
            FragmentTransaction.navigateWithBackstack(fragment.parentFragmentManager, GetNewAddress(),"Edit Address")
        }
        if(clickable){
            holder.itemView.setOnClickListener {
                CartFragment.selectedAddress = addressList[position]
                clickable =false
                fragment.parentFragmentManager.popBackStack()
            }
        }
    }
}