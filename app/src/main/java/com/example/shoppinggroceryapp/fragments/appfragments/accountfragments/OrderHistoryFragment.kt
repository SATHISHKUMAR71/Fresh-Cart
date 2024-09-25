package com.example.shoppinggroceryapp.fragments.appfragments.accountfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class OrderHistoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        val noSubscriptionButton = view.findViewById<MaterialButton>(R.id.noSubscriptionButton)
        val dailySubscription = view.findViewById<MaterialButton>(R.id.dailySubscriptionButton)
        val weeklySubscription = view.findViewById<MaterialButton>(R.id.weeklySubscriptionButton)
        val monthlySubscription = view.findViewById<MaterialButton>(R.id.monthlySubscriptionButton)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.orderHistoryNavigationIcon)
        var orderListFragment = OrderListFragment()
        var bundle = Bundle()
        var clickable = arguments?.getBoolean("isClickable")
        noSubscriptionButton.setOnClickListener {
            bundle.putString("subscriptionType","Once")
            orderListFragment.arguments = bundle
            com.example.shoppinggroceryapp.fragments.FragmentTransaction.navigateWithBackstack(parentFragmentManager,orderListFragment,"No Subscription Fragment")
        }

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        dailySubscription.setOnClickListener {
            bundle.putString("subscriptionType","Daily")
            println("ON ORDER LIST CLICKBLE $bundle")
            orderListFragment.arguments = bundle
            com.example.shoppinggroceryapp.fragments.FragmentTransaction.navigateWithBackstack(parentFragmentManager,orderListFragment,"Daily Fragment")
        }
        weeklySubscription.setOnClickListener {
            bundle.putString("subscriptionType","Weekly Once")
            println("ON ORDER LIST CLICKBLE $bundle")
            orderListFragment.arguments = bundle
            com.example.shoppinggroceryapp.fragments.FragmentTransaction.navigateWithBackstack(parentFragmentManager,orderListFragment,"Weekly Fragment")
        }
        monthlySubscription.setOnClickListener {
            bundle.putString("subscriptionType","Monthly Once")
            println("ON ORDER LIST CLICKBLE $bundle")
            orderListFragment.arguments = bundle
            com.example.shoppinggroceryapp.fragments.FragmentTransaction.navigateWithBackstack(parentFragmentManager,orderListFragment,"Monthly Fragment")
        }
        if(MainActivity.isRetailer){
            toolbar.setTitle("Orders")
            toolbar.navigationIcon = null
            toolbar.isTitleCentered = true
        }
        if(clickable==true){
            println("ON ORDER LIST CLICKBLE in Order History")
            toolbar.setTitle("Select an Order Type")
            bundle.putBoolean("isClickable",true)
            orderListFragment.arguments = bundle
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        InitialFragment.hideSearchBar.value = true
        if(!MainActivity.isRetailer){
            InitialFragment.hideBottomNav.value = true
        }
    }
    override fun onStop() {
        super.onStop()
        InitialFragment.hideSearchBar.value = false
        if(!MainActivity.isRetailer){
            InitialFragment.hideBottomNav.value = false
        }
        println("ON STOP ORDER LIST:")
    }

}