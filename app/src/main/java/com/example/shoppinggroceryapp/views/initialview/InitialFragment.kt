package com.example.shoppinggroceryapp.views.initialview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.MainActivity.Companion.isRetailer
import com.example.shoppinggroceryapp.MainActivity.Companion.userEmail
import com.example.shoppinggroceryapp.MainActivity.Companion.userFirstName
import com.example.shoppinggroceryapp.MainActivity.Companion.userId
import com.example.shoppinggroceryapp.MainActivity.Companion.userImage
import com.example.shoppinggroceryapp.MainActivity.Companion.userLastName
import com.example.shoppinggroceryapp.MainActivity.Companion.userPhone
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.views.userviews.cartview.FindNumberOfCartItems
import com.example.shoppinggroceryapp.helpers.fragmenttransaction.FragmentTransaction
import com.example.shoppinggroceryapp.helpers.permissionhandler.MicPermissionHandler
import com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.signup.SignUpFragment
import com.example.shoppinggroceryapp.views.retailerviews.customerrequestlist.CustomerRequestListFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.views.sharedviews.search.SearchViewModelFactory
import com.example.shoppinggroceryapp.views.sharedviews.search.SearchViewModel
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListViewModel
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.OrderHistoryFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.views.sharedviews.profileviews.AccountFragment
import com.example.shoppinggroceryapp.views.sharedviews.search.adapter.SearchListAdapter
import com.example.shoppinggroceryapp.views.userviews.cartview.cart.CartFragment
import com.example.shoppinggroceryapp.views.userviews.category.CategoryFragment
import com.example.shoppinggroceryapp.views.userviews.home.HomeFragment
import com.example.shoppinggroceryapp.views.userviews.offer.OfferFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import java.util.Locale


class InitialFragment : Fragment() {

    private lateinit var bottomNav:BottomNavigationView
    private lateinit var searchView:SearchView
    private lateinit var searchBar:SearchBar
    private lateinit var homeFragment: Fragment
    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var permissionHandler: com.example.shoppinggroceryapp.helpers.permissionhandler.interfaces.MicPermissionHandler
    companion object{
        private var searchString =""
        var category = ""
        var searchedQuery:MutableLiveData<String> = MutableLiveData()
        var openSearchView:MutableLiveData<Boolean> = MutableLiveData()
        var openMicSearch:MutableLiveData<Boolean> = MutableLiveData()
        var searchHint:MutableLiveData<String> =MutableLiveData()
        var searchQueryList = mutableListOf<String>()
        var hideSearchBar:MutableLiveData<Boolean> = MutableLiveData()
        var hideBottomNav:MutableLiveData<Boolean> = MutableLiveData()
        var closeSearchView:MutableLiveData<Boolean> = MutableLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openMicSearch.value = false
        openSearchView.value =false
        searchListAdapter = SearchListAdapter(this)
        permissionHandler = MicPermissionHandler(
            this
        )
        permissionHandler.initMicResults()
        if(savedInstanceState!=null){
        }
        else {
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_initial, container, false)
        val initialViewModel = InitialDataSetter()
        var searchViewModel = ViewModelProvider(this,
            SearchViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao())
        )[SearchViewModel::class.java]
        bottomNav = view.findViewById(R.id.bottomNav)
        searchBar = view.findViewById(R.id.searchBar)
        searchView = view.findViewById(R.id.searchView)
        searchView.setupWithSearchBar(searchBar)

        searchHint.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                searchBar.hint = it
            }
            else{
                searchBar.hint = "Search Products"
            }
        }

        val launchMicResults = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val micResult = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val textOutput = micResult?.get(0).toString()
                searchView.show()
                searchView.editText.setText(textOutput)
                searchView.editText.setSelection(textOutput.length)
            }
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Product Name")
        }
        openSearchView.observe(viewLifecycleOwner){
            if(it) {
                searchView.editText.setText("")
                searchView.show()
            }
            else{
                searchView.hide()
            }
        }
        searchBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.searchBarMic ->{
                    if(permissionHandler.checkMicPermission(launchMicResults,intent)==true){
                        launchMicResults.launch(intent)
                    }
                }
            }
            true
        }
        openMicSearch.observe(viewLifecycleOwner){
            if(it){
                if(permissionHandler.checkMicPermission(launchMicResults,intent)==true){
                    launchMicResults.launch(intent)
                }
            }
        }
        homeFragment = HomeFragment()
        val customerRequestListFragment = CustomerRequestListFragment()
        val pref = requireActivity().getSharedPreferences("freshCart", Context.MODE_PRIVATE)
        initialViewModel.setInitialDataForUser(pref)
        if(isRetailer){
            bottomNav.menu.clear()
            bottomNav.inflateMenu(R.menu.admin_menu)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentMainLayout,customerRequestListFragment)
                .commit()
            parentFragmentManager.registerFragmentLifecycleCallbacks(object :FragmentManager.FragmentLifecycleCallbacks(){
                override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentResumed(fm, f)
                    when (f){
                        is ProductListFragment ->{
                            bottomNav.menu.findItem(R.id.inventory).isChecked = true
                        }
                        is SignUpFragment -> bottomNav.menu.findItem(R.id.addOtherAdmin).isChecked = true
                        is OrderHistoryFragment -> {
                            bottomNav.menu.findItem(R.id.ordersReceived).isChecked = true
                        }
                        is CustomerRequestListFragment -> {
                            bottomNav.menu.findItem(R.id.customerRequest).isChecked = true
                        }
                        is AccountFragment ->{
                            bottomNav.menu.findItem(R.id.account).isChecked = true
                        }
                    }
                }
            },true)
            bottomNav.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.inventory -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            ProductListFragment(),"Products Fragment")
                    }
                    R.id.customerRequest -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,customerRequestListFragment,"Customer Request Fragment")
                    }
                    R.id.addOtherAdmin->{
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            SignUpFragment(),"Adding Other Admins")
                    }
                    R.id.account-> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            AccountFragment(),"Account Fragment")
                    }
                    R.id.ordersReceived -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            OrderHistoryFragment(),"Orders Received Fragment")
                    }
                }
                true
            }
        }
        else{
            if(savedInstanceState==null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentMainLayout, homeFragment)
                    .commit()
            }
            parentFragmentManager.registerFragmentLifecycleCallbacks(object :FragmentManager.FragmentLifecycleCallbacks(){
                override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentResumed(fm, f)
                    when (f){
                        is AccountFragment -> {
                            bottomNav.menu.findItem(R.id.account).isChecked = true
                        }
                        is CategoryFragment -> {
                            bottomNav.menu.findItem(R.id.category).isChecked = true
                        }
                        is HomeFragment -> {
                            bottomNav.menu.findItem(R.id.homeMenu).isChecked = true
                        }
                        is CartFragment -> {
                            bottomNav.menu.findItem(R.id.cart).isChecked = true
                        }
                        is OfferFragment -> {
                            bottomNav.menu.findItem(R.id.offer).isChecked = true
                        }
                    }
                }
            },true)
//            searchViewModel.
            bottomNav.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.account -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            AccountFragment(),"Account Fragment")
                    }
                    R.id.cart -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            CartFragment(),"Cart Fragment")
                    }
                    R.id.homeMenu -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,homeFragment,"Initial Fragment")
                    }
                    R.id.offer -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            OfferFragment(),"Offer Fragment")
                    }
                    R.id.category -> {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,
                            CategoryFragment(),"Category Fragment")
                    }
                }
                true
            }
        }
        searchViewModel.getSearchedList()
        searchedQuery.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                searchViewModel.addItemInDb(it)
            }
        }
        var cartListViewModel = ProductListViewModel(AppDatabase.getAppDatabase(requireContext()).getUserDao())
        if(!isRetailer){
            cartListViewModel.getCartItems(MainActivity.cartId)
        }
        cartListViewModel.cartList.observe(viewLifecycleOwner){
            if(!isRetailer){
                FindNumberOfCartItems.productCount.value = it.size
            }
        }
        FindNumberOfCartItems.productCount.observe(viewLifecycleOwner){
            if(!isRetailer){
                if(it!=0) {
                    bottomNav.getOrCreateBadge(R.id.cart).isVisible = true
                    bottomNav.getOrCreateBadge(R.id.cart).text = it.toString()
                }
                else{
                    bottomNav.getOrCreateBadge(R.id.cart).isVisible = false
                }
            }
        }
        var searchRecyclerView = view.findViewById<RecyclerView>(R.id.searchRecyclerView)
//        if(!isRetailer) {
        searchView.editText.addTextChangedListener {
            searchString = it.toString()
            if (it?.isNotEmpty() == true) {
                searchViewModel.performSearch(it.toString())
            }
            else if(it.toString().isEmpty()){
                searchViewModel.getSearchedList()
            }
            else{
                searchViewModel.performSearch("-1")
            }
        }
//        }

        searchViewModel.searchedList.observe(viewLifecycleOwner){ searchList ->
            if(searchString.isEmpty() && searchList.isEmpty()){
                SearchListAdapter.searchList = mutableListOf("Frozen Pizza","Cake Mixes","Chocolate Cake","Almond Milk","Frozen Veggie Burgers")
            }
            else if(searchList.isEmpty() && searchString.isNotEmpty()){
                SearchListAdapter.searchList = searchList.toMutableList()
                Toast.makeText(context,"No Results Found",Toast.LENGTH_SHORT).show()
            }
            else {
                SearchListAdapter.searchList = searchList.toMutableList()
            }
            searchRecyclerView.adapter = SearchListAdapter(this)
            searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        val searchBarTop = view.findViewById<LinearLayout>(R.id.searchBarTop)

        closeSearchView.observe(viewLifecycleOwner){
            if(it){
                searchView.hide()
            }
        }
        searchView.addTransitionListener { searchView, previousState, newState ->
            if(newState==SearchView.TransitionState.HIDDEN){
                if(category.isNotEmpty()) {
                    var productListFragment = ProductListFragment()
                    productListFragment.arguments = Bundle().apply {
                        putBoolean("searchViewOpened", true)
                        putString("category", category)
                    }
                    category =""
                    FragmentTransaction.navigateWithBackstack(
                        parentFragmentManager,
                        productListFragment,
                        "Product List Fragment in List"
                    )
                }
            }
        }

        hideSearchBar.observe(viewLifecycleOwner){
            if(it){
                searchBarTop.visibility = View.GONE
            }
            else{
                searchBarTop.visibility = View.VISIBLE
            }
        }
        hideBottomNav.observe(viewLifecycleOwner){
            if(it){
                bottomNav.visibility = View.GONE
            }
            else{
                bottomNav.visibility =View.VISIBLE
            }
        }

        bottomNav.setOnItemReselectedListener {
        }

        return view
    }

}