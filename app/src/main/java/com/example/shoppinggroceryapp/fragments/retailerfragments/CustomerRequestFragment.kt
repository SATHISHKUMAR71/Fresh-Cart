package com.example.shoppinggroceryapp.fragments.retailerfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.appfragments.OfferFragment
import com.example.shoppinggroceryapp.fragments.appfragments.accountfragments.OrderListFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment.Companion.productListFilterCount
import com.example.shoppinggroceryapp.fragments.filter.FilterFragment
import com.example.shoppinggroceryapp.fragments.retailerfragments.recyclerview.CustomerRequestAdapter
import com.example.shoppinggroceryapp.fragments.retailerfragments.recyclerview.CustomerRequestAdapter.Companion.requestList
import com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.customerrequestviewmodel.CustomerRequestViewModel
import com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.customerrequestviewmodel.CustomerRequestViewModelFactory
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Images

class CustomerRequestFragment : Fragment() {

    companion object{
        var customerName:String = ""
        var requestedDate:String = ""
        var customerRequest:String = ""
        var customerPhone:String = ""
        var customerEmail:String = ""
    }

    private lateinit var customerViewModel: CustomerRequestViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        productListFilterCount = 0
        OfferFragment.offerFilterCount = 0
        FilterFragment.list = null
        OfferFragment.dis10Val = false
        OfferFragment.dis20Val = false
        OfferFragment.dis30Val = false
        OfferFragment.dis40Val = false
        OfferFragment.dis50Val =false
        ProductListFragment.dis10Val = false
        ProductListFragment.dis20Val = false
        ProductListFragment.dis30Val = false
        ProductListFragment.dis40Val = false
        ProductListFragment.dis50Val = false
        val view = inflater.inflate(R.layout.fragment_customer_request, container, false)
        val customerReqRV = view.findViewById<RecyclerView>(R.id.customerRequestRecyclerView)
        customerViewModel = ViewModelProvider(this,
            CustomerRequestViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao())
        )[CustomerRequestViewModel::class.java]
        customerViewModel.getCustomerRequest()
        customerViewModel.customerRequestList.observe(viewLifecycleOwner){
            if(customerReqRV.adapter==null) {
                val adapter = CustomerRequestAdapter(customerViewModel, this)
                customerReqRV.adapter = adapter
                customerReqRV.layoutManager = LinearLayoutManager(context)
            }
            requestList = it.toMutableList()
            if(requestList.isEmpty()){
                customerReqRV.visibility = View.GONE
                view.findViewById<ImageView>(R.id.noDataFoundImage).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.noRequestAvailableFromTheUser).visibility =View.VISIBLE
            }
            else{
                customerReqRV.visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.noDataFoundImage).visibility = View.GONE
                view.findViewById<TextView>(R.id.noRequestAvailableFromTheUser).visibility =View.GONE
            }
        }

        customerViewModel.selectedOrderLiveData.observe(viewLifecycleOwner){
            if(it!=null) {
                customerViewModel.getCorrespondingCart(it.cartId)
            }
        }
        customerViewModel.correspondingCartLiveData.observe(viewLifecycleOwner){
            if(it!=null) {
                OrderListFragment.correspondingCartList = it
                OrderListFragment.selectedOrder = customerViewModel.selectedOrderLiveData.value
                FragmentTransaction.navigateWithBackstack(parentFragmentManager,RequestDetailFragment(),"Request Detail Fragment")
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        InitialFragment.hideSearchBar.value = true
    }

    override fun onStop() {
        super.onStop()
        InitialFragment.hideSearchBar.value = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        customerViewModel.selectedOrderLiveData.value = null
        customerViewModel.correspondingCartLiveData.value = null
        customerViewModel.correspondingCartLiveData.removeObservers(viewLifecycleOwner)
        customerViewModel.selectedOrderLiveData.removeObservers(viewLifecycleOwner)
        customerViewModel.customerRequestList.removeObservers(viewLifecycleOwner)
    }
}