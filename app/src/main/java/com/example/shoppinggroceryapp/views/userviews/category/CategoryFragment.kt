package com.example.shoppinggroceryapp.views.userviews.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.imagehandlers.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment.Companion.productListFilterCount
import com.example.shoppinggroceryapp.views.sharedviews.filter.FilterFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.dataclass.ChildCategoryName
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.views.userviews.category.adapter.MainCategoryAdapter
import com.example.shoppinggroceryapp.views.userviews.offer.OfferFragment


class CategoryFragment: Fragment() {

    private lateinit var mainCategoryRV:RecyclerView
    private lateinit var imageLoader: ImageLoaderAndGetter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageLoader = ImageLoaderAndGetter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        val view =  inflater.inflate(R.layout.fragment_category, container, false)
        var childList:List<List<ChildCategoryName>>? = null
        var parentList:List<ParentCategory>? = null
        val categoryViewModel = ViewModelProvider(this,
            CategoryViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getProductDao())
        )[CategoryViewModel::class.java]
        mainCategoryRV = view.findViewById(R.id.categoryRecyclerView)

        categoryViewModel.getParentCategory()
        categoryViewModel.parentList.observe(viewLifecycleOwner){
            categoryViewModel.getChildWithParentName()
        }
        categoryViewModel.childList.observe(viewLifecycleOwner){

        }
        categoryViewModel.childList.observe(viewLifecycleOwner){
            childList = it
            if(parentList!=null){
                var mainAdapter = MainCategoryAdapter(this, parentList!!, childList!!, imageLoader)
                if(mainCategoryRV.adapter==null) {
                    mainCategoryRV.adapter =mainAdapter
                    mainCategoryRV.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
        categoryViewModel.getParentCategory()

        categoryViewModel.parentList.observe(viewLifecycleOwner){
            parentList = it
            if(childList!=null){
                if(mainCategoryRV.adapter==null) {
                    mainCategoryRV.adapter =
                        MainCategoryAdapter(this, parentList!!, childList!!, imageLoader)
                    mainCategoryRV.layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onStop() {
        super.onStop()
        mainCategoryRV.stopScroll()
    }
    override fun onDestroy() {
        super.onDestroy()
        MainCategoryAdapter.expandedData = mutableSetOf()
        mainCategoryRV.adapter = null
    }

}