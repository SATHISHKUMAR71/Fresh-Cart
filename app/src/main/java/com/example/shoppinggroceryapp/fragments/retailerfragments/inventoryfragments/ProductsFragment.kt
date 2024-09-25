package com.example.shoppinggroceryapp.fragments.retailerfragments.inventoryfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ProductsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitialFragment.hideSearchBar.value = false
        println("***** ON CREATE")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("PRODUCTS ON CREATED")
        println("###### ON LIST FRAG CREATE Products Frag View")
        val view =  inflater.inflate(R.layout.fragment_products, container, false)
        parentFragmentManager.beginTransaction()
            .replace(R.id.productList,ProductListFragment())
            .commit()

        view.findViewById<FloatingActionButton>(R.id.addProductsToInventory).setOnClickListener {
            ProductListFragment.selectedProduct.value = null
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,AddEditFragment(),"Edit in Product Fragments")
        }
        return view
    }


}