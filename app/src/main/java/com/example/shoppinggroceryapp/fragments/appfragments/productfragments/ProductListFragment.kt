package com.example.shoppinggroceryapp.fragments.appfragments.productfragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ext.SdkExtensions
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.FindNumberOfCartItems
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.CartFragment
import com.example.shoppinggroceryapp.fragments.appfragments.CategoryFragment
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.appfragments.OfferFragment
import com.example.shoppinggroceryapp.fragments.appfragments.OfferFragment.Companion
import com.example.shoppinggroceryapp.fragments.appfragments.OfferFragment.Companion.offerFilterCount
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.fragments.filter.FilterFragment
import com.example.shoppinggroceryapp.fragments.retailerfragments.inventoryfragments.AddEditFragment
import com.example.shoppinggroceryapp.fragments.sort.BottomSheetDialog
import com.example.shoppinggroceryapp.fragments.sort.ProductSorter
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.viewmodel.initialviewmodel.InitialViewModelFactory
import com.example.shoppinggroceryapp.viewmodel.initialviewmodel.SearchViewModel
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModel
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream


class ProductListFragment : Fragment() {
    companion object{
        var selectedProduct:MutableLiveData<Product> = MutableLiveData()
        var selectedPos:Int? = null
        var totalCost:MutableLiveData<Float> = MutableLiveData(0f)
        var position = 0
        var dis50Val: Boolean? = null
        var dis40Val: Boolean? = null
        var dis30Val: Boolean? = null
        var dis20Val: Boolean? = null
        var dis10Val: Boolean? = null
        var productListFirstVisiblePos:Int? = null
        var productListFilterCount =0
    }
    var category:String? = null
    private lateinit var productListViewModel:ProductListViewModel
    private lateinit var fileDir:File
    private var realProductList = mutableListOf<Product>()
    private lateinit var filterCountText:TextView
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var productRV:RecyclerView
    var searchViewOpened = false
    private lateinit var selectedProduct: Product
    private lateinit var toolbar:MaterialToolbar
    private var productList:MutableList<Product> = mutableListOf()
    private lateinit var adapter:ProductListAdapter
    private lateinit var noItemsImage:ImageView
    private lateinit var notifyNoItems:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BottomSheetDialog.selectedOption.value = null
        category = arguments?.getString("category")
        FilterFragment.list = null
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
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view =  inflater.inflate(R.layout.fragment_product_list, container, false)
        val sortAndFilterLayout = view.findViewById<LinearLayout>(R.id.linearLayout15)
        fileDir = File(requireContext().filesDir,"AppImages")
        adapter = ProductListAdapter(this,fileDir,"P",false)
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        filterCountText = view.findViewById(R.id.filterCountTextView)
        toolbar = view.findViewById<MaterialToolbar>(R.id.productListToolBar)
        if(productListFilterCount!=0){
            filterCountText.text = productListFilterCount.toString()
            filterCountText.visibility = View.VISIBLE
        }
        else{
            filterCountText.visibility = View.INVISIBLE
        }
        var badgeDrawableListFragment = BadgeDrawable.create(requireContext())
        toolbar.setTitle(category)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        if(MainActivity.isRetailer){
            toolbar.menu.findItem(R.id.cart).isVisible = false
        }
        else{
            toolbar.menu.findItem(R.id.cart).isVisible = true
        }
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.searchProductInProductList -> {
                    InitialFragment.openSearchView.value = true
                }
                R.id.mic ->{
                    InitialFragment.openMicSearch.value = true
                }
                R.id.cart ->{
                    FragmentTransaction.navigateWithBackstack(parentFragmentManager,CartFragment(),"Going to cart")
                }
            }
            true
        }
        productRV = view.findViewById(R.id.productListRecyclerView)
        notifyNoItems = view.findViewById(R.id.notifyNoItemsAvailable)
        noItemsImage = view.findViewById(R.id.noItemsFound)
        searchViewModel = ViewModelProvider(this,InitialViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao()))[SearchViewModel::class.java]
        val totalCostButton = view.findViewById<MaterialButton>(R.id.totalPriceWorthInCart)
        val exploreCategoryButton = view.findViewById<MaterialButton>(R.id.categoryButtonProductList)
        val sortButton = view.findViewById<MaterialButton>(R.id.sortButton)
        val filterButton = view.findViewById<MaterialButton>(R.id.filterButton)
        productListViewModel = ViewModelProvider(this,ProductListViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao()))[ProductListViewModel::class.java]
        searchViewOpened = (arguments?.getBoolean("searchViewOpened")==true)
        productListViewModel.getCartItems(MainActivity.cartId.toInt())

        productListViewModel.cartList.observe(viewLifecycleOwner){
            FindNumberOfCartItems.productCount.value = it.size
        }

        FindNumberOfCartItems.productCount.observe(viewLifecycleOwner){
            badgeDrawableListFragment.text = it.toString()
            if(it!=0) {
                badgeDrawableListFragment.isVisible = true
                BadgeUtils.attachBadgeDrawable(badgeDrawableListFragment, toolbar, R.id.cart)
            }
            else{
                badgeDrawableListFragment.isVisible = false
            }
        }
        filterButton.setOnClickListener {
            productListFilterCount = 0
            productList = realProductList
            var filterFragment = FilterFragment(realProductList).apply {
                arguments = Bundle().apply { putString("category",category) }
            }
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,filterFragment,"Filter")
        }
        totalCost.observe(viewLifecycleOwner){
            val str ="₹"+ (it?:0).toString()
            totalCostButton.text =str
        }
        totalCostButton.setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,CartFragment(),"Going to cart")
        }
        exploreCategoryButton.setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,CategoryFragment(),"Exploring Category")
        }

        totalCost.value = 0f
        productListViewModel.getCartItems(cartId = MainActivity.cartId)
        productListViewModel.cartList.observe(viewLifecycleOwner){
            for(cart in it){
                val totalItems = cart.totalItems
                val price = cart.unitPrice
                totalCost.value =totalCost.value!!+(totalItems * price)
            }
        }


        if(FilterFragment.list!=null){
            if(productRV.adapter==null) {
                productRV.adapter = adapter
                productRV.layoutManager = LinearLayoutManager(requireContext())
            }
            adapter.setProducts(FilterFragment.list!!)
            if(FilterFragment.list!!.size==0){
                hideProductRV()
            }
            else{
                showProductRV()
            }
        }
        if(category==null){
            productListViewModel.getOnlyProducts()
        }
        else{
            productListViewModel.getProductsByCategory(category!!)
        }
        productListViewModel.productList.observe(viewLifecycleOwner){
            if(productList.isEmpty()) {
                productList = it.toMutableList()
            }
            realProductList = it.toMutableList()
            if(FilterFragment.list==null) {
//                adapter.setProducts(it)
                if(BottomSheetDialog.selectedOption.value==null) {
                    adapter.setProducts(it)
                }
                else{
                    adapter.setProducts(productList)
                }
                if (productRV.adapter == null) {
                    productRV.adapter = adapter
                    productRV.layoutManager = LinearLayoutManager(context)
                }
                if (productList.size == 0) {
                    hideProductRV()
                }
                else{
                    showProductRV()
                }
            }
        }
        productListViewModel.productCategoryList.observe(viewLifecycleOwner){
            if(productList.isEmpty()) {
                productList = it.toMutableList()
            }
            realProductList = it.toMutableList()
            if(FilterFragment.list==null) {
                if(BottomSheetDialog.selectedOption.value==null) {
                    adapter.setProducts(it)
                }
                else{
                    adapter.setProducts(productList)
                }
                if (productRV.adapter == null) {
                    productRV.adapter = adapter
                    productRV.layoutManager = LinearLayoutManager(requireContext())
                }
//                adapter.setProducts(productList)
                if (productList.size == 0) {
                    hideProductRV()
                }
                else{
                    showProductRV()
                }
            }
        }
        sortButton.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(parentFragmentManager,"Bottom Sort Sheet")
        }
        val sorter  = ProductSorter()
        BottomSheetDialog.selectedOption.observe(viewLifecycleOwner){
            if(it!=null) {
                var newList = listOf<Product>()
                if (it == 0) {
                    if (FilterFragment.list == null) {
                        newList = sorter.sortByDate(productList)
                    } else {
                        newList = sorter.sortByDate(FilterFragment.list!!)
                    }
                    adapter.setProducts(newList)
                } else if (it == 1) {
                    if (FilterFragment.list == null) {
                        newList = sorter.sortByExpiryDate(productList)
                    } else {
                        newList = sorter.sortByExpiryDate(FilterFragment.list!!)
                    }
                    adapter.setProducts(newList)
                } else if (it == 2) {
                    if (FilterFragment.list == null) {
                        newList = sorter.sortByDiscount(productList)
                    } else {
                        newList = sorter.sortByDiscount(FilterFragment.list!!)
                    }
                    adapter.setProducts(newList)
                } else if (it == 3) {
                    if (FilterFragment.list == null) {
                        newList = sorter.sortByPriceLowToHigh(productList)
                    } else {
                        newList = sorter.sortByPriceLowToHigh(FilterFragment.list!!)
                    }
                    adapter.setProducts(newList)
                } else if (it == 4) {
                    if (FilterFragment.list == null) {
                        newList = sorter.sortByPriceHighToLow(productList)
                    } else {
                        newList = sorter.sortByPriceHighToLow(FilterFragment.list!!)
                    }
                    adapter.setProducts(newList)
                }
                if (newList.isNotEmpty()) {
                    productList = newList.toMutableList()
                    if (FilterFragment.list != null) {
                        if (FilterFragment.list!!.size == newList.size) {
                            FilterFragment.list = newList.toMutableList()
                        }
                    }
                }
                productRV.layoutManager?.let { layoutManager ->
                    (layoutManager as LinearLayoutManager).scrollToPosition(0)
                }
            }
        }
        return view
    }


    override fun onResume() {
        super.onResume()
        val fab = view?.findViewById<FloatingActionButton>(R.id.addProductsToInventory)
        if(MainActivity.isRetailer){
            toolbar.visibility =View.GONE
            val params = (view?.layoutParams as FrameLayout.LayoutParams)
            params.setMargins(0,220,0,0)
            view?.layoutParams = params
            fab?.visibility = View.VISIBLE
            view?.findViewById<FloatingActionButton>(R.id.addProductsToInventory)?.setOnClickListener {
                ProductListFragment.selectedProduct.value = null
                FragmentTransaction.navigateWithBackstack(parentFragmentManager,AddEditFragment(),"Edit in Product Fragment")
            }
            view?.findViewById<LinearLayout>(R.id.linearLayout8)?.visibility = View.GONE
        }
        else{
            fab?.visibility = View.GONE
            view?.findViewById<LinearLayout>(R.id.linearLayout8)?.visibility = View.VISIBLE
        }
        if(!MainActivity.isRetailer) {
            InitialFragment.hideSearchBar.value = true
            InitialFragment.hideBottomNav.value = true
        }
        if(MainActivity.isRetailer && toolbar.title == null){
            toolbar.visibility =View.GONE
            InitialFragment.hideSearchBar.value = false
            InitialFragment.hideBottomNav.value = false
        }
        else{
            val params = (view?.layoutParams as FrameLayout.LayoutParams)
            params.setMargins(0,0,0,0)
            view?.layoutParams = params
            val fabParams = fab?.layoutParams as CoordinatorLayout.LayoutParams
            fabParams.setMargins(0,0,0,80)
            fab.layoutParams = fabParams
            toolbar.visibility =View.VISIBLE
            InitialFragment.hideSearchBar.value = true
            InitialFragment.hideBottomNav.value = true
        }

        if(InitialFragment.searchQueryList.isNotEmpty()){
            InitialFragment.hideSearchBar.value = true
            InitialFragment.hideBottomNav.value = true
            toolbar.visibility = View.VISIBLE
        }
        if(FilterFragment.list!=null){
            if(productRV.adapter==null) {
                productRV.adapter = adapter
                productRV.layoutManager = LinearLayoutManager(requireContext())
            }
            adapter.setProducts(FilterFragment.list!!)
            checkDeletedItem()
            adapter.setProducts(FilterFragment.list!!)
            if (FilterFragment.list!!.size == 0) {
                hideProductRV()
            }
            else{
                showProductRV()
            }
        }
        else{
//            adapter.setProducts(productList)
        }
        if(FilterFragment.list?.isNotEmpty()==true){
            adapter.setProducts(FilterFragment.list!!)
        }
        else if (productList.isNotEmpty()) {
            adapter.setProducts(productList)
        }
        productRV.scrollToPosition(productListFirstVisiblePos ?: 0)
    }


    override fun onPause() {
        super.onPause()
        InitialFragment.hideSearchBar.value = false
        InitialFragment.hideBottomNav.value = false
    }

    override fun onStop() {
        super.onStop()
        InitialFragment.hideSearchBar.value = false
        InitialFragment.hideBottomNav.value = false
        productRV.stopScroll()
        productListFirstVisiblePos = (productRV.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        productListViewModel.cartList.value = mutableListOf()
        if(InitialFragment.searchQueryList.size <2){
            InitialFragment.searchHint.value = ""
            InitialFragment.searchQueryList = mutableListOf()
        }
        else{
            InitialFragment.searchHint.value = InitialFragment.searchQueryList[1]
            InitialFragment.searchQueryList.removeAt(0)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        productListFirstVisiblePos=null
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
//        if(InitialFragment.searchQueryList.size <2){
//            InitialFragment.searchHint.value = ""
//            InitialFragment.searchQueryList = mutableListOf()
//        }
//        else{
//            InitialFragment.searchHint.value = InitialFragment.searchQueryList[1]
//            InitialFragment.searchQueryList.removeAt(0)
//        }
    }



    fun checkDeletedItem(){
        try {
            if (ProductDetailFragment.deletePosition != null) {
                productList.removeAt(ProductDetailFragment.deletePosition!!)
                if (FilterFragment.list != null) {
                    FilterFragment.list!!.removeAt(ProductDetailFragment.deletePosition!!)
                }
                productRV.adapter?.notifyItemRemoved(ProductDetailFragment.deletePosition ?: 0)
                ProductDetailFragment.deletePosition = null
            }
        }
        catch (e:Exception){
        }
    }

    fun showProductRV(){
        productRV.animate()
            .alpha(1f)
            .setDuration(50)
            .withEndAction { productRV.visibility = View.VISIBLE
            }
            .start()
        notifyNoItems.animate()
            .alpha(0f)
            .setDuration(50)
            .withEndAction { notifyNoItems.visibility = View.GONE }
            .start()
        noItemsImage.animate()
            .alpha(0f)
            .setDuration(50)
            .withEndAction { noItemsImage.visibility = View.GONE }
            .start()
    }

    fun hideProductRV(){
        productRV.animate()
            .alpha(0f)
            .setDuration(50)
            .withEndAction { productRV.visibility = View.GONE }
            .start()
        notifyNoItems.animate()
            .alpha(1f)
            .setDuration(50)
            .withEndAction { notifyNoItems.visibility = View.VISIBLE }
            .start()
        noItemsImage.animate()
            .alpha(1f)
            .setDuration(50)
            .withEndAction { noItemsImage.visibility = View.VISIBLE }
            .start()
    }
}