package com.example.shoppinggroceryapp.fragments.retailerfragments.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.DateGenerator
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.OrderListFragment
import com.example.shoppinggroceryapp.fragments.retailerfragments.CustomerRequestFragment
import com.example.shoppinggroceryapp.fragments.retailerfragments.RequestDetailFragment
import com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.customerrequestviewmodel.CustomerRequestViewModel
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.dataclass.CustomerRequestWithName
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest

class CustomerRequestAdapter(var customerReqViewModel: CustomerRequestViewModel, var fragment: Fragment) :RecyclerView.Adapter<CustomerRequestAdapter.CustomerRequestHolder>(){

    companion object {
        var requestList = mutableListOf<CustomerRequestWithName>()
    }
    inner class CustomerRequestHolder(customerView:View):RecyclerView.ViewHolder(customerView){
        val reqDate = customerView.findViewById<TextView>(R.id.requestedDate)
        val name = customerView.findViewById<TextView>(R.id.customerName)
        var request = customerView.findViewById<TextView>(R.id.customerRequestText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerRequestHolder {
        return CustomerRequestHolder(LayoutInflater.from(parent.context).inflate(R.layout.customer_request_view,parent,false))
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: CustomerRequestHolder, position: Int) {
        val date = "Requested On: ${DateGenerator.getDayAndMonth(requestList[position].requestedDate)}"
        val name:String = if(requestList[position].userLastName.isEmpty()){
            "Customer Name: ${requestList[position].userFirstName}"
        } else{
            "Customer Name: ${requestList[position].userFirstName} ${requestList[position].userLastName}"
        }
        holder.name.text = name
        holder.reqDate.text = date
        holder.request.text = requestList[position].request
        holder.itemView.setOnClickListener {
            customerReqViewModel.getOrderDetails(requestList[position].orderId)
            CustomerRequestFragment.customerName = name
            CustomerRequestFragment.customerEmail = requestList[position].userEmail
            CustomerRequestFragment.customerPhone = requestList[position].userPhone
            CustomerRequestFragment.customerRequest = requestList[position].request
            CustomerRequestFragment.requestedDate = DateGenerator.getDayAndMonth(requestList[position].requestedDate)
        }
    }
}