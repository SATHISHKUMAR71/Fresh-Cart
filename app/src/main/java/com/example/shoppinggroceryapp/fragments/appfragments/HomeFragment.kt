package com.example.shoppinggroceryapp.fragments.appfragments

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment
import com.example.shoppinggroceryapp.fragments.appfragments.productfragments.ProductListFragment.Companion.productListFilterCount
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.fragments.filter.FilterFragment
import com.example.shoppinggroceryapp.fragments.sort.BottomSheetDialog
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.viewmodel.homeviewmodel.HomeViewModel
import com.example.shoppinggroceryapp.viewmodel.homeviewmodel.HomeViewModelFactory
import com.google.android.material.button.MaterialButton
import java.io.File


class HomeFragment : Fragment() {

    var essentialItems = listOf("Butter & Ghee","Fresh Fruits","Fresh Vegetables","Milk & Cream","Mixed Nuts","Rice","Spices","Soft Drinks"
        ,"Energy Drinks","Tea & Coffee","Wheat & Flour")

    var imagesList = listOf(R.drawable.butter,R.drawable.fresh_fruits,R.drawable.fresh_veg,
        R.drawable.milk_cream,R.drawable.mixed_nuts,R.drawable.rice,R.drawable.spices,R.drawable.soft_drinks,
        R.drawable.energy_drinks,R.drawable.tea_coffee,R.drawable.wheat_flour)
    var essentialSize = essentialItems.size -1
    private lateinit var homeFragNestedScroll:NestedScrollView
    private lateinit var recentItems:RecyclerView
    var recentlyViewedList = mutableListOf<Product>()
    private lateinit var homeViewModel : HomeViewModel
    companion object{
        var position = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        BottomSheetDialog.selectedOption.value = null
        if(savedInstanceState==null){
            super.onCreate(savedInstanceState)
        }
        else{
            onViewStateRestored(savedInstanceState)
        }
        println("ON HOME CREATE 4545 Home Destroyed CREATED")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("88888 ON HOME CREATE VIew 4545")
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
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this,HomeViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getProductDao()))[HomeViewModel::class.java]
        recentItems = view.findViewById(R.id.recentlyViewedItemsHomeFrag)
        homeFragNestedScroll =  view.findViewById(R.id.nestedScrollViewHomeFrag)
        var adapter =ProductListAdapter(this,File(requireContext().filesDir,"AppImages"),"P",true)
        homeViewModel.getRecentlyViewedItems()
        recentItems.adapter = null
        adapter.setProducts(mutableListOf())
//        if(recentItems.adapter==null){
            recentItems.adapter = adapter
            recentItems.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//        }

        homeViewModel.recentlyViewedList.observe(viewLifecycleOwner){
            if((it!=null) &&( it.isNotEmpty())){
                println("ON IF $it")
                view.findViewById<TextView>(R.id.recentlyViewedItemsText).visibility = View.VISIBLE
                adapter.setProducts(it)
            }
            else{
                println("ON ELSE $it")
                adapter.setProducts(listOf())
                view.findViewById<TextView>(R.id.recentlyViewedItemsText).visibility = View.GONE
            }
        }
        view.findViewById<MaterialButton>(R.id.viewAllCategoriesBtn).setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,CategoryFragment(),"Opened Category Fragment")
        }
        val categoryContainer = view.findViewById<LinearLayout>(R.id.categoryLayoutRow)

        var i=0
        while (i<essentialSize){
            addViewToLayout(categoryContainer,i)
            i+=3
        }
        return view
    }

    fun addViewToLayout(container: ViewGroup,index:Int){
        val newView = LayoutInflater.from(requireContext()).inflate(R.layout.category_layout,container,false)
        val imageView0 = newView.findViewById<ImageView>(R.id.categoryImage0)
        val imageView1 = newView.findViewById<ImageView>(R.id.categoryImage1)
        val imageView2 = newView.findViewById<ImageView>(R.id.categoryImage2)
        val categoryType0 = newView.findViewById<TextView>(R.id.categoryType0)
        val categoryType1 = newView.findViewById<TextView>(R.id.categoryType1)
        val categoryType2 = newView.findViewById<TextView>(R.id.categoryType2)

        if ((index+2) <= essentialSize) {
            categoryType0.text = essentialItems[index]
            categoryType1.text = essentialItems[index+1]
            categoryType2.text = essentialItems[index+2]
            imageView0.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index]))
            imageView1.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index+1]))
            imageView2.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index+2]))
            setImageAndTextListener(imageView0,categoryType0)
            setImageAndTextListener(imageView1,categoryType1)
            setImageAndTextListener(imageView2,categoryType2)
        }
        else if ((index+1) <= essentialSize) {
            categoryType0.text = essentialItems[index]
            categoryType1.text = essentialItems[index+1]
            imageView0.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index]))
            imageView1.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index+1]))
            setImageAndTextListener(imageView0,categoryType0)
            setImageAndTextListener(imageView1,categoryType1)
            imageView2.visibility = View.INVISIBLE
            categoryType2.visibility = View.INVISIBLE
        }
        else{
            categoryType0.text = essentialItems[index]
            imageView0.setImageDrawable(ContextCompat.getDrawable(requireContext(),imagesList[index]))
            imageView2.visibility = View.INVISIBLE
            imageView1.visibility = View.INVISIBLE
            setImageAndTextListener(imageView0,categoryType0)
            categoryType1.visibility = View.INVISIBLE
            categoryType2.visibility = View.INVISIBLE
        }
        container.addView(newView)
    }

    private fun setImageAndTextListener(image:ImageView, text:TextView){
        val productListFrag = ProductListFragment()
        productListFrag.arguments = Bundle().apply {
            putString("category",text.text.toString())
        }
        image.setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,productListFrag,
                "Product List Opened")
        }
        text.setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,productListFrag
            ,"Product List Opened")
        }
    }

    override fun onStop() {
        super.onStop()
        println("Home Fragment On Stop")
        recentItems.stopScroll()
        homeViewModel.recentlyViewedList.value = null
        homeViewModel.recentlyViewedList.removeObservers(viewLifecycleOwner)
    }
    override fun onPause() {
        super.onPause()
        println("Home Fragment On Pause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("9090 HONE ON SAVE INSTANCE CALLED")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("ON HOME CREATE 4545 Home Destroyed")
    }
}