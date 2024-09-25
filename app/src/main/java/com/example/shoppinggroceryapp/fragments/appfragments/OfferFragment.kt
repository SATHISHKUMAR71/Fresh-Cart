package com.example.shoppinggroceryapp.fragments.appfragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment.Companion.productListFilterCount
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.fragments.filter.FilterFragment
import com.example.shoppinggroceryapp.fragments.sort.BottomSheetDialog
import com.example.shoppinggroceryapp.fragments.sort.ProductSorter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.viewmodel.offerviewmodel.OfferViewModel
import com.example.shoppinggroceryapp.viewmodel.offerviewmodel.OfferViewModelFactory
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModel
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModelFactory
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.io.File


class OfferFragment : Fragment() {

    companion object {
        var dis50Val: Boolean? = null
        var dis40Val: Boolean? = null
        var dis30Val: Boolean? = null
        var dis20Val: Boolean? = null
        var dis10Val: Boolean? = null
        var offerFilterCount:Int  = 0
    }
    private lateinit var productListViewModel:ProductListViewModel
    private lateinit var filterAndSortLayout:LinearLayout
    private lateinit var filterCount:TextView
    private var realList = mutableListOf<Product>()
    var products = listOf<Product>()
    lateinit var offerList:RecyclerView
    private lateinit var adapter: ProductListAdapter
    private lateinit var offerViewModel:OfferViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Offer Frag created $offerFilterCount")
        productListFilterCount = 0
        BottomSheetDialog.selectedOption.value = null
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
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("ON CREATE VIEW OFFER FRAGMENT")
        val view =  inflater.inflate(R.layout.fragment_offer, container, false)
        val noItemsFoundImage = view.findViewById<ImageView>(R.id.noItemFoundImageOfferFragment)
        val noItemsFoundImageText = view.findViewById<TextView>(R.id.noItemsFoundText)
        offerList = view.findViewById(R.id.offerList)
        filterCount = view.findViewById<TextView>(R.id.filterCountTextViewOffer)
        filterAndSortLayout = view.findViewById(R.id.linearLayout15)
        val sortButton = view.findViewById<MaterialButton>(R.id.sortButton)
        val filterButton = view.findViewById<MaterialButton>(R.id.filterButton)
        val fileDir = File(requireContext().filesDir,"AppImages")
        adapter = ProductListAdapter(this,fileDir,"O",false)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        offerViewModel = ViewModelProvider(this,OfferViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao()))[OfferViewModel::class.java]
        productListViewModel = ViewModelProvider(this,ProductListViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao()))[ProductListViewModel::class.java]
        if(FilterFragment.list!=null){
            if(FilterFragment.list!!.isEmpty()){
                noItemsFoundImageText.visibility = View.VISIBLE
                noItemsFoundImage.visibility =View.VISIBLE
            }
            else{
                noItemsFoundImageText.visibility = View.GONE
                noItemsFoundImage.visibility =View.GONE

            }
            adapter.setProducts(FilterFragment.list!!)
            offerList.adapter = adapter
            offerList.layoutManager = LinearLayoutManager(context)
        }
        offerViewModel.getOfferedProducts()


        offerViewModel.offeredProductList.observe(viewLifecycleOwner){ offeredProductList ->

            realList = offeredProductList.toMutableList()
//            products = realList
            println("989898 observer Called ${products.size} ${offeredProductList.size} ${offeredProductList[0]}")
            if(products.isNotEmpty()){
                println("989898 products data: ${products[0].productName}")
            }
            if(products.isEmpty()) {
                products = offeredProductList
            }
            if(FilterFragment.list==null){
                println("OOOOObserver Called in IF")
                if(products.isEmpty()){
                    noItemsFoundImageText.visibility = View.VISIBLE
                    noItemsFoundImage.visibility =View.VISIBLE
                }
                else{
                    noItemsFoundImageText.visibility = View.GONE
                    noItemsFoundImage.visibility =View.GONE

                }
                if(BottomSheetDialog.selectedOption.value==null) {
                    adapter.setProducts(offeredProductList)
                }
                else{
                    adapter.setProducts(products)
                }
                offerList.adapter = adapter
                offerList.layoutManager = LinearLayoutManager(context)
            }
        }
        if(offerFilterCount!=0){
            filterCount.text = offerFilterCount.toString()
            filterCount.visibility = View.VISIBLE
        }
        else{
            filterCount.visibility = View.INVISIBLE
        }
        val filterBadge = BadgeDrawable.create(requireContext())
        filterBadge.number = 10
        filterBadge.badgeTextColor = Color.WHITE
        filterBadge.backgroundColor = Color.RED
        BadgeUtils.attachBadgeDrawable(filterBadge,filterButton)

        filterButton.setOnClickListener {
            offerFilterCount = 0
//            FilterFragment.list = realList.toMutableList()
            products = realList
            println("PRODUCTS LIST: ${products.size} $products")
            if(FilterFragment.list!=null) {
                FragmentTransaction.navigateWithBackstack(
                    parentFragmentManager,
                    FilterFragment(realList),
                    "Filter"
                )
            }
            else{
                FragmentTransaction.navigateWithBackstack(
                    parentFragmentManager,
                    FilterFragment(realList),
                    "Filter"
                )
            }
        }

        sortButton.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(parentFragmentManager,"Bottom Sort Sheet")
        }
        val sorter  = ProductSorter()
        BottomSheetDialog.selectedOption.observe(viewLifecycleOwner){
            var newList: List<Product> = mutableListOf()
            if(it==0){
                if(FilterFragment.list==null) {
                    newList = sorter.sortByDate(products)
                }
                else{
                    newList = sorter.sortByDate(FilterFragment.list!!)
                }
                adapter.setProducts(newList)
            }
            else if(it == 1){
                if(FilterFragment.list==null) {
                    newList = sorter.sortByExpiryDate(products)
                }
                else{
                    newList = sorter.sortByExpiryDate(FilterFragment.list!!)
                }
                adapter.setProducts(newList)
            }
            else if(it == 2){
                if(FilterFragment.list==null) {
                    newList = sorter.sortByDiscount(products)
                }
                else{
                    newList = sorter.sortByDiscount(FilterFragment.list!!)
                }
                adapter.setProducts(newList)
            }
            else if(it == 3){
                if(FilterFragment.list==null) {
                    newList = sorter.sortByPriceLowToHigh(products)
                }
                else{
                    newList = sorter.sortByPriceLowToHigh(FilterFragment.list!!)
                }
                adapter.setProducts(newList)
            }
            else if(it == 4){
                if(FilterFragment.list==null) {
                    newList = sorter.sortByPriceHighToLow(products)
                }
                else{
                    newList = sorter.sortByPriceHighToLow(FilterFragment.list!!)
                }
                adapter.setProducts(newList)
            }
            if(newList.isNotEmpty()){
                products = newList
                if(FilterFragment.list!=null){
                    if(FilterFragment.list!!.size==newList.size){
                        FilterFragment.list = newList.toMutableList()
                    }
                }
            }
            offerList.layoutManager?.let {layoutManager ->
                println("DATA RESETTED: ON OFFER OBSERVER")
                println("Scrolled TO THE POSITION offer")
                (layoutManager as LinearLayoutManager).scrollToPosition(0)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if(FilterFragment.list!=null){
            adapter.setProducts(FilterFragment.list!!)
            offerList.adapter = adapter
            offerList.layoutManager = LinearLayoutManager(context)
        }
        else{
            adapter.setProducts(products)
        }
        println("OFFER FRAGMENT ON Resume ${products.size}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BottomSheetDialog.selectedOption.removeObservers(viewLifecycleOwner)
    }
    override fun onStop() {
        super.onStop()
        println("OFFER FRAGMENT ON STOP")
        offerList.stopScroll()
        println("%%%%% FILTER IS NULL: ${FilterFragment.list}")
    }

    override fun onPause() {
        super.onPause()
        println("OFFER FRAGMENT ON PAUSE")
    }



    override fun onDestroy() {
        super.onDestroy()
        FilterFragment.list = null
        offerFilterCount = 0
        ProductListFragment.productListFilterCount = 0
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
        println("On Offer destroyed")
    }
}

