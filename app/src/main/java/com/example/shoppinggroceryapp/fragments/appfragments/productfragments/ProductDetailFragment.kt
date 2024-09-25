package com.example.shoppinggroceryapp.fragments.appfragments.productfragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.OptIn
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.DateGenerator
import com.example.shoppinggroceryapp.fragments.FindNumberOfCartItems
import com.example.shoppinggroceryapp.fragments.FragmentTransaction
import com.example.shoppinggroceryapp.fragments.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.fragments.appfragments.CartFragment
import com.example.shoppinggroceryapp.fragments.appfragments.CategoryFragment
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.appfragments.recyclerview.ProductListAdapter
import com.example.shoppinggroceryapp.fragments.retailerfragments.inventoryfragments.AddEditFragment
import com.example.shoppinggroceryapp.viewmodel.retailerviewmodel.inventoryviewmodel.AddEditViewModel
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.products.Images
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.viewmodel.cartviewmodel.CartViewModel
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductDetailViewModel
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductDetailViewModelFactory
import com.example.shoppinggroceryapp.viewmodel.productviewmodel.ProductListViewModelFactory
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File


class ProductDetailFragment : Fragment() {


    private var countOfOneProduct = 0
    private lateinit var imageLoader:ImageLoaderAndGetter
    private lateinit var cartViewModel:CartViewModel
    private lateinit var recyclerView:RecyclerView
    private lateinit var productDetailToolBar:MaterialToolbar
    private lateinit var mrpTextView:TextView
    private lateinit var discountedPrice:TextView
    private lateinit var badgeDrawable:BadgeDrawable
    private lateinit var addProductButton:MaterialButton
    private lateinit var totalItemsAddedProductDetail:TextView
    private lateinit var removeProductImgButton:ImageButton
    private lateinit var addProductImgButton:ImageButton
    private lateinit var addRemoveLayout:LinearLayout
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    var once = 0
    var oneTimeFragmentIn = -1
    var backNavigated = false
    var selectedProductInClass:Product? = null
    var productObserved = 0
    companion object{
        var brandData:MutableLiveData<String> = MutableLiveData()
        var selectedProductList = mutableListOf<Product>()
        var deletePosition:Int? = null
//        var productCount:MutableLiveData<Int> = MutableLiveData(0)
    }
    private lateinit var productDetailViewModel:ProductDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageLoader = ImageLoaderAndGetter()
        oneTimeFragmentIn = 0
    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("OFFER FRAGMENT detail frag ON CREATE VIEW")
        val view =  inflater.inflate(R.layout.fragment_product_detail, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.productImageViewer)
        productDetailToolBar = view.findViewById<MaterialToolbar>(R.id.productDetailToolbar)
        cartViewModel = CartViewModel(AppDatabase.getAppDatabase(requireContext()).getUserDao())
        mrpTextView = view.findViewById<TextView>(R.id.productPriceProductDetail)
        discountedPrice = view.findViewById<TextView>(R.id.discountedPrice)
        productDetailViewModel = ViewModelProvider(this,ProductDetailViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getRetailerDao()))[ProductDetailViewModel::class.java]
        productDetailViewModel.getImagesForProducts(ProductListFragment.selectedProduct.value?.productId?:0)
        addProductButton = view.findViewById(R.id.addProductButtonProductDetail)
        totalItemsAddedProductDetail = view.findViewById(R.id.totalItemsAddedProductDetail)
        removeProductImgButton = view.findViewById(R.id.productRemoveSymbolButtonProductDetail)
        addProductImgButton = view.findViewById(R.id.productAddSymbolButtonProductDetail)
        addRemoveLayout = view.findViewById(R.id.productAddRemoveLayoutProductDetail)
        productDetailViewModel.imageList.observe(viewLifecycleOwner){
            for(i in it){
            }

        }

        if(MainActivity.isRetailer){
            productDetailToolBar.menu.findItem(R.id.edit).setVisible(true)
            productDetailToolBar.menu.findItem(R.id.delete).setVisible(true)
            productDetailToolBar.menu.findItem(R.id.cart).setVisible(false)
//            view.findViewById<LinearLayout>(R.id.similarProductsLayout).visibility = View.GONE
            view.findViewById<LinearLayout>(R.id.exploreBottomLayout).visibility = View.GONE
        }

        else{
            productDetailToolBar.menu.findItem(R.id.edit).setVisible(false)
            productDetailToolBar.menu.findItem(R.id.cart).setVisible(true)
            productDetailToolBar.menu.findItem(R.id.delete).setVisible(false)
//            view.findViewById<LinearLayout>(R.id.similarProductsLayout).visibility = View.VISIBLE
            view.findViewById<LinearLayout>(R.id.exploreBottomLayout).visibility = View.VISIBLE
        }


        productDetailToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cart -> {
                    if (!MainActivity.isRetailer) {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,CartFragment(),"Cart In Product Detail")
                    }
                }
                R.id.edit -> {
                    if (MainActivity.isRetailer) {
                        FragmentTransaction.navigateWithBackstack(parentFragmentManager,AddEditFragment(),"Edit in Product Detail")
                    }
                }
                R.id.delete -> {
                    if(MainActivity.isRetailer){
                        var alertDialog =AlertDialog.Builder(context)
                            .setTitle("Delete Product!")
                            .setMessage("Are you Sure to delete this product in Inventory?")
                            .setNegativeButton("No"){dialog,which ->
                                dialog.dismiss()
                            }
                            .setPositiveButton("Yes"){dialog,which ->
                                ProductListFragment.selectedProduct.value?.let {
                                    productDetailViewModel.removeProduct(it)
                                }
                                deletePosition = ProductListFragment.selectedPos
                                dialog.dismiss()
                                parentFragmentManager.popBackStack()
                            }
                            .create()

                        alertDialog.show()
                    }
                }
            }
            true
        }
        badgeDrawable = BadgeDrawable.create(requireContext())



        view.findViewById<MaterialButton>(R.id.categoryButton).setOnClickListener {
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,CategoryFragment(),"Category Opened From Product Detail")
        }

        cartViewModel.getProductsByCartId(MainActivity.cartId)
        cartViewModel.cartProducts.observe(viewLifecycleOwner){
            FindNumberOfCartItems.productCount.value = it.size
            var noOfItemsInt = FindNumberOfCartItems.productCount.value
            if(noOfItemsInt==0){
                badgeDrawable.isVisible = false
            }
            else{
                badgeDrawable.isVisible = true
                badgeDrawable.text = noOfItemsInt.toString()
            }
            BadgeUtils.attachBadgeDrawable(badgeDrawable,productDetailToolBar,R.id.cart)
        }
        ProductListFragment.selectedProduct.value?.brandId?.let{
            productDetailViewModel.getBrandName(it)
        }

        productDetailViewModel.brandName.observe(viewLifecycleOwner){
            view.findViewById<TextView>(R.id.brandNameProductDetail).text = it
        }


        productDetailToolBar.setNavigationOnClickListener {
            setProductValue()
            parentFragmentManager.popBackStack()
        }

        ProductListFragment.selectedProduct.value?.let {
            productDetailViewModel.addInRecentlyViewedItems(it.productId)
//            productDetailViewModel.getImagesForProducts(it.productId)

        }


        recyclerView = view.findViewById(R.id.productListInProductDetailFragment)

        productDetailViewModel.similarProductsLiveData.observe(viewLifecycleOwner){
            if(it.size ==1) {
                println("0000 ON IF")
                view.findViewById<LinearLayout>(R.id.similarProductsLayout).visibility = View.GONE
            }
            else {
                println("0000 ON Else $it")
                view.findViewById<LinearLayout>(R.id.similarProductsLayout).visibility = View.VISIBLE
                val adapter = ProductListAdapter(
                    this,
                    File(requireContext().filesDir, "AppImages"),
                    "P",
                    true
                )
                recyclerView.adapter = adapter
                val tmpList = mutableListOf<Product>()
                for (i in it.toMutableList()) {
                    if (i.productId == ProductListFragment.selectedProduct.value?.productId) {
                        continue
                    }
                    tmpList.add(i)
                }
                adapter.setProducts(tmpList)
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
        addProductButton.setOnClickListener {
            println("COUNT OF PRODUCT: ${countOfOneProduct}")
            countOfOneProduct++
            productDetailViewModel.addProductInCart(
                Cart(
                    MainActivity.cartId,
                    ProductListFragment.selectedProduct.value!!.productId.toInt(),
                    countOfOneProduct,
                    calculateDiscountPrice(
                        ProductListFragment.selectedProduct.value!!.price,
                        ProductListFragment.selectedProduct.value!!.offer
                    )
                )
            )
            totalItemsAddedProductDetail.text = countOfOneProduct.toString()
            addProductButton.visibility = View.GONE
            FindNumberOfCartItems.productCount.value =
                FindNumberOfCartItems.productCount.value!! + 1
            resetBadge(badgeDrawable, productDetailToolBar)
            addRemoveLayout.visibility = View.VISIBLE
        }
        productDetailViewModel.imageList.observe(viewLifecycleOwner){
            var imageList:MutableList<String> = mutableListOf()
            imageList.add(ProductListFragment.selectedProduct.value?.mainImage?:"")
            for(i in it){
                imageList.add(i.images)
            }
            viewPager.adapter  = ProductViewAdapter(this,imageList,imageLoader)
            TabLayoutMediator(view.findViewById(R.id.imageTabLayout),viewPager){tab,pos ->

            }.attach()
        }
        addProductImgButton.setOnClickListener {
            countOfOneProduct++
            productDetailViewModel.updateProductInCart(
                Cart(
                    MainActivity.cartId,
                    ProductListFragment.selectedProduct.value!!.productId.toInt(),
                    countOfOneProduct,
                    calculateDiscountPrice(
                        ProductListFragment.selectedProduct.value!!.price,
                        ProductListFragment.selectedProduct.value!!.offer
                    )
                )
            )
            totalItemsAddedProductDetail.text = countOfOneProduct.toString()
        }
        removeProductImgButton.setOnClickListener {
            if (countOfOneProduct > 1) {
                countOfOneProduct--
                totalItemsAddedProductDetail.text = countOfOneProduct.toString()
                productDetailViewModel.updateProductInCart(
                    Cart(
                        MainActivity.cartId,
                        ProductListFragment.selectedProduct.value!!.productId.toInt(),
                        countOfOneProduct,
                        calculateDiscountPrice(
                            ProductListFragment.selectedProduct.value!!.price,
                            ProductListFragment.selectedProduct.value!!.offer
                        )
                    )
                )
            } else if (countOfOneProduct == 1) {
                countOfOneProduct--
                productDetailViewModel.removeProductInCart(
                    Cart(
                        MainActivity.cartId,
                        ProductListFragment.selectedProduct.value!!.productId.toInt(),
                        countOfOneProduct,
                        calculateDiscountPrice(
                            ProductListFragment.selectedProduct.value!!.price,
                            ProductListFragment.selectedProduct.value!!.offer
                        )
                    )
                )
                FindNumberOfCartItems.productCount.value =
                    FindNumberOfCartItems.productCount.value!! - 1
                resetBadge(badgeDrawable, productDetailToolBar)
                addRemoveLayout.visibility = View.GONE
                addProductButton.visibility = View.VISIBLE
            }
        }
        FindNumberOfCartItems.productCount.observe(viewLifecycleOwner){
            if(FindNumberOfCartItems.productCount.value==0){
                badgeDrawable.isVisible = false
            }
            else{
                badgeDrawable.isVisible = true
                badgeDrawable.text = FindNumberOfCartItems.productCount.value.toString()
            }
            BadgeUtils.attachBadgeDrawable(badgeDrawable,productDetailToolBar,R.id.cart)
        }
        return view
    }

    @OptIn(ExperimentalBadgeUtils::class)
    private fun resetBadge(badgeDrawable: BadgeDrawable,productDetailToolBar:MaterialToolbar) {

        if(FindNumberOfCartItems.productCount.value==0){
            badgeDrawable.isVisible = false
        }
        else{
            badgeDrawable.isVisible = true
            badgeDrawable.text = FindNumberOfCartItems.productCount.value.toString()
        }
        BadgeUtils.attachBadgeDrawable(badgeDrawable,productDetailToolBar,R.id.cart)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                println("BACK PRESSED CALLED")
                if(isAdded) {
                    setProductValue()
                    parentFragmentManager.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)
    }
    override fun onResume() {
        super.onResume()
        println("OFFER FRAGMENT detail frag ON RESUME")
        ProductListFragment.selectedProduct.observe(viewLifecycleOwner) { selectedProduct ->
            if(selectedProductInClass==null){
                selectedProductInClass = selectedProduct
            }

            if(once==0) {
                selectedProductList.add(selectedProduct)
            }
            if((oneTimeFragmentIn==0) || (backNavigated)) {
                println("OFFER FRAGMENT detail frag ON DESTROY 909090 OBSERVER CALLED once value $oneTimeFragmentIn $backNavigated ${this.hashCode()} ${selectedProduct.productName}")
                productDetailToolBar.title = selectedProduct.productName
                view?.findViewById<TextView>(R.id.productDescriptionProductDetail)?.text =
                    selectedProduct.productDescription
                productDetailViewModel.getImagesForProducts(selectedProduct.productId)
                val productNameWithQuantity =
                    "${ProductListFragment.selectedProduct.value?.productName} (${ProductListFragment.selectedProduct.value?.productQuantity})"
                view?.findViewById<TextView>(R.id.productNameProductDetail)?.text =
                    productNameWithQuantity
                var price = ""
                if ((ProductListFragment.selectedProduct.value?.offer ?: -1f) > 0f) {
                    mrpTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    val discountedPriceStr = " MRP ₹${
                        calculateDiscountPrice(
                            ProductListFragment.selectedProduct.value!!.price,
                            ProductListFragment.selectedProduct.value!!.offer
                        )
                    }"
                    discountedPrice.visibility = View.VISIBLE
                    discountedPrice.text = discountedPriceStr
                } else {
                    mrpTextView.paintFlags = 0
                    discountedPrice.visibility = View.GONE
                }
                price = "MRP ₹${ProductListFragment.selectedProduct.value?.price}"
                ProductListFragment.selectedProduct.value?.brandId?.let {
                    productDetailViewModel.getBrandName(it)
                }
                mrpTextView.text = price
                val offerView = view?.findViewById<TextView>(R.id.productOffer)
                if ((ProductListFragment.selectedProduct.value?.offer ?: -1f) < 1f) {
                    offerView?.visibility = View.GONE
                } else {
                    offerView?.visibility = View.VISIBLE
                }
                var offerStr =
                    ProductListFragment.selectedProduct.value?.offer?.toInt().toString() + "% Off"
                offerView?.text = offerStr
                view?.findViewById<TextView>(R.id.expiryDateProductDetail)?.text =
                    DateGenerator.getDayAndMonth(ProductListFragment.selectedProduct.value?.expiryDate!!)
                view?.findViewById<TextView>(R.id.manufactureDateProductDetail)?.text =
                    DateGenerator.getDayAndMonth(ProductListFragment.selectedProduct.value?.manufactureDate!!)

                if (ProductListFragment.selectedProduct.value != null) {
                    productDetailViewModel.getCartForSpecificProduct(
                        MainActivity.cartId,
                        ProductListFragment.selectedProduct.value!!.productId.toInt()
                    )
                    productDetailViewModel.isCartAvailable.observe(viewLifecycleOwner) {
                        if (it == null) {
                            countOfOneProduct = 0
                            addRemoveLayout.visibility = View.GONE
                            addProductButton.visibility = View.VISIBLE
                        } else {
                            addRemoveLayout.visibility = View.VISIBLE
                            addProductButton.visibility = View.GONE
                            countOfOneProduct = it.totalItems
                            println("COUNT OF PRODUCT: in observer is: ${it.totalItems} ${it.productId}")
                            totalItemsAddedProductDetail.text = countOfOneProduct.toString()
                        }
                    }
                }
                if(backNavigated){
                    backNavigated = false
                }
                else{
                    oneTimeFragmentIn = 1
                }
            }
            once = 1
            productDetailViewModel.getSimilarProduct(selectedProduct.categoryName)
        }

        InitialFragment.hideBottomNav.value = true
        InitialFragment.hideSearchBar.value = true
    }

    override fun onStop() {
        super.onStop()
        println("OFFER FRAGMENT detail frag ON STOP")
        backNavigated = true
        recyclerView.stopScroll()
        InitialFragment.hideBottomNav.value = false
        InitialFragment.hideSearchBar.value = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        println("BACK PRESSED CALLED on Destroy View")
        productDetailViewModel.brandName.value = null
        productDetailViewModel.brandName.removeObservers(viewLifecycleOwner)
        productDetailViewModel.isCartAvailable.removeObservers(viewLifecycleOwner)
    }

    override fun onPause() {
        super.onPause()
        productObserved = 0
        println("909090 observer in on Pause ${this.hashCode()} $productObserved")
        recyclerView.adapter?.let {
            it.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }
    private fun calculateDiscountPrice(price:Float, offer:Float):Float{
        if(offer>0f) {
            return price - (price * (offer / 100))
        }
        else{
            return price
        }
    }


    fun setProductValue(){
        val size =selectedProductList.size
        try{
            selectedProductList.removeAt(size-1)
            ProductListFragment.selectedProduct.value = selectedProductList[size-2]
            println("OFFER FRAGMENT detail frag ON DESTROY ${ProductListFragment.selectedProduct.value}")
        }
        catch (e:Exception){
            println(e)
        }
    }
}