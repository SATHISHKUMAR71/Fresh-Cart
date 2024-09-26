package com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.views.userviews.offer.OfferFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment.Companion.productListFilterCount
import com.example.shoppinggroceryapp.views.sharedviews.filter.FilterFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.model.entities.products.CartWithProductData
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist.adapter.OrderListAdapter
import com.example.shoppinggroceryapp.views.userviews.help.Help
import com.google.android.material.appbar.MaterialToolbar


class OrderListFragment : Fragment() {


    companion object{
        var selectedOrder:OrderDetails? = null
        var correspondingCartList:List<CartWithProductData>? = null
    }

    private lateinit var orderList:RecyclerView
    private lateinit var toolbar:MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(Help.backPressed){
            parentFragmentManager.popBackStack()
            Help.backPressed = false
        }
        productListFilterCount = 0
        OfferFragment.offerFilterCount = 0
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
        FilterFragment.list = null
        var dataReady:MutableLiveData<Boolean> = MutableLiveData()
        val view =  inflater.inflate(R.layout.fragment_order_list, container, false)
        orderList = view.findViewById<RecyclerView>(R.id.orderList)
        val clickable = arguments?.getBoolean("isClickable",false)
        val orderListViewModel = ViewModelProvider(this,
            OrderListViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getRetailerDao())
        )[OrderListViewModel::class.java]
        var cartWithProductsList = mutableListOf<MutableList<CartWithProductData>>()
        var orderedItems:MutableList<OrderDetails> = mutableListOf()
        var orderAdapter = OrderListAdapter(orderedItems.toMutableList(), this, clickable)
        var subscriptionType = arguments?.getString("subscriptionType")
        toolbar = view.findViewById(R.id.materialToolbarOrderList)
        orderListViewModel.orderedItems.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                view.findViewById<TextView>(R.id.noOrderFoundText).visibility =View.VISIBLE
                view.findViewById<ImageView>(R.id.noOrderFoundImage).visibility =View.VISIBLE
                orderList.visibility = View.GONE
            }
            else{
                view.findViewById<TextView>(R.id.noOrderFoundText).visibility =View.GONE
                view.findViewById<ImageView>(R.id.noOrderFoundImage).visibility =View.GONE
                orderList.visibility = View.VISIBLE
            }
            orderedItems = it.toMutableList()
            orderAdapter.setOrders(it.toMutableList())
            orderListViewModel.getCartWithProducts()
        }

        orderListViewModel.cartWithProductList.observe(viewLifecycleOwner){
            if(cartWithProductsList.size == orderedItems.size){
                dataReady.value=true
            }
        }
        orderListViewModel.dataReady.observe(viewLifecycleOwner){
            cartWithProductsList = orderListViewModel.cartWithProductList.value!!

            if(orderList.adapter==null) {
                orderList.adapter = orderAdapter
                orderList.layoutManager = LinearLayoutManager(context)
            }
            OrderListAdapter.cartWithProductList = cartWithProductsList
        }

        if(!MainActivity.isRetailer) {
            if(orderedItems.isEmpty()) {
                when (subscriptionType) {
                    "Weekly Once" -> {
                        toolbar.setTitle("Weekly Orders")
                        orderListViewModel.getOrdersForSelectedUserWeeklySubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    "Monthly Once" -> {
                        toolbar.setTitle("Monthly Orders")
                        orderListViewModel.getOrdersForSelectedUserMonthlySubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    "Daily" -> {
                        toolbar.setTitle("Daily Orders")
                        orderListViewModel.getOrdersForSelectedUserDailySubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    "Once" -> {
                        toolbar.setTitle("One Time Orders")
                        orderListViewModel.getOrdersForSelectedUserWithNoSubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    else ->{
                        orderListViewModel.getOrdersForSelectedUser(MainActivity.userId.toInt())
                    }
                }
            }
        }
        else{
            if(orderedItems.isEmpty()) {
                when (subscriptionType) {
                    "Weekly Once" -> {
                        toolbar.setTitle("Weekly Orders")
                        orderListViewModel.getOrdersForRetailerWeeklySubscription(MainActivity.userId.toInt())
                    }

                    "Monthly Once" -> {
                        toolbar.setTitle("Monthly Orders")
                        orderListViewModel.getOrdersForRetailerMonthlySubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    "Daily" -> {
                        toolbar.setTitle("Daily Orders")
                        orderListViewModel.getOrdersForRetailerDailySubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    "Once" -> {
                        toolbar.setTitle("One Time Orders")
                        orderListViewModel.getOrdersForRetailerWithNoSubscription(
                            MainActivity.userId.toInt()
                        )
                    }

                    else -> {
                        orderListViewModel.getOrdersForSelectedUser(MainActivity.userId.toInt())
                    }
                }
            }
        }


        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        if(clickable==true){
            toolbar.setTitle("Select an Order")
        }
        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
    override fun onResume() {
        super.onResume()
        view?.visibility =View.VISIBLE
        InitialFragment.hideSearchBar.value = true
        InitialFragment.hideBottomNav.value = true

    }

    override fun onStop() {
        super.onStop()
        orderList.stopScroll()
        InitialFragment.hideSearchBar.value = false
        InitialFragment.hideBottomNav.value = false
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}