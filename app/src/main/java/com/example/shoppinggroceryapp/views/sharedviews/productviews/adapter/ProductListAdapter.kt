package com.example.shoppinggroceryapp.views.sharedviews.productviews.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.dategenerator.DateGenerator
import com.example.shoppinggroceryapp.views.userviews.cartview.FindNumberOfCartItems
import com.example.shoppinggroceryapp.helpers.imagehandlers.SetProductImage
import com.example.shoppinggroceryapp.views.userviews.cartview.cart.CartFragment
import com.example.shoppinggroceryapp.views.userviews.cartview.diffutil.CartItemsDiffUtil
import com.example.shoppinggroceryapp.model.dao.RetailerDao
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.Cart
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productdetail.ProductDetailFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListViewModel
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListViewModelFactory
import com.google.android.material.button.MaterialButton
import java.io.File

class ProductListAdapter(var fragment: Fragment,
                         private var file: File,
                         private var tag:String,private var isShort:Boolean,var productListViewModel: ProductListViewModel):RecyclerView.Adapter<ProductListAdapter.ProductLargeImageHolder>() {

    private var userDb:UserDao = AppDatabase.getAppDatabase(fragment.requireContext()).getUserDao()
    private var retailerDb:RetailerDao = AppDatabase.getAppDatabase(fragment.requireContext()).getRetailerDao()

    companion object{
//        var productList:MutableList<Product> = mutableListOf()
        var productsSize = 0
    }
    var size = 0
    var productList:MutableList<Product> = mutableListOf()
    private var countList = mutableListOf<Int>()
    init {
        for(i in 0..<productList.size){
            countList.add(i,0)
        }
    }


    inner class ProductLargeImageHolder(productLargeView:View):RecyclerView.ViewHolder(productLargeView){
        val productImage = productLargeView.findViewById<ImageView>(R.id.productImageLong)
        val buttonLayout = productLargeView.findViewById<LinearLayout>(R.id.buttonLayout)
        val brandName = productLargeView.findViewById<TextView>(R.id.brandName)
        val productName = productLargeView.findViewById<TextView>(R.id.productNameLong)
        val productQuantity = productLargeView.findViewById<TextView>(R.id.productQuantity)
        val productExpiryDate = productLargeView.findViewById<TextView>(R.id.productExpiryDate)
        val productPrice = productLargeView.findViewById<TextView>(R.id.productPriceLong)
        val offer = productLargeView.findViewById<TextView>(R.id.offerText)
        val productAddRemoveLayout:LinearLayout = productLargeView.findViewById(R.id.productAddRemoveLayout)
        val productAddOneTime:MaterialButton = productLargeView.findViewById(R.id.productAddLayoutOneTime)
        val totalItems:TextView = productLargeView.findViewById(R.id.totalItemsAdded)
        val addSymbolButton:ImageButton = productLargeView.findViewById(R.id.productAddSymbolButton)
        val removeSymbolButton:ImageButton = productLargeView.findViewById(R.id.productRemoveSymbolButton)
        val productMrpText:TextView = productLargeView.findViewById(R.id.productMrpText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductLargeImageHolder {
        if(isShort) {
            return ProductLargeImageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.product_layout_short, parent, false)
            )
        }
        else{
            return ProductLargeImageHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout_long,parent,false))
        }
    }

    override fun getItemCount(): Int {
        size = productList.size
        return size
    }

    override fun onBindViewHolder(holder: ProductLargeImageHolder, position: Int) {

        if(size==0){
        }
        else{
            if(MainActivity.isRetailer){
                holder.buttonLayout.visibility = View.GONE
            }
            else{
                holder.buttonLayout.visibility = View.VISIBLE
            }

            productListViewModel.getSpecificCart(MainActivity.cartId,productList[position].productId.toInt()){cart ->
                if(cart!=null){
                    MainActivity.handler.post {
                        holder.productAddOneTime.visibility = View.GONE
                        holder.productAddRemoveLayout.visibility = View.VISIBLE
                        countList[position] = cart.totalItems
                        holder.totalItems.text = cart.totalItems.toString()
                    }
                }
                else{
                    MainActivity.handler.post {
                        holder.productAddOneTime.visibility = View.VISIBLE
                        holder.productAddRemoveLayout.visibility = View.GONE
                        countList[position] = 0
                        holder.totalItems.text = "0"
                    }
                }
            }

            productListViewModel.getBrandName(productList[position].brandId){brand ->
                holder.brandName.text = brand
            }
            if(productList[position].offer>0f){
                val str = "MRP ₹"+ productList[position].price
                holder.productMrpText.text = str
                holder.productMrpText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.productMrpText.visibility = View.VISIBLE
                holder.offer.visibility = View.VISIBLE
                val offerText = productList[position].offer.toInt().toString() +"% Off"
                holder.offer.text = offerText
            }
            else{
                val str = "MRP"
                holder.productMrpText.text = str
                holder.productMrpText.paintFlags = 0
                holder.offer.text = null
                holder.offer.visibility = View.GONE
            }
            holder.productName.text = productList[position].productName
            holder.productExpiryDate.text = DateGenerator.getDayAndMonth(productList[position].expiryDate)
            holder.productQuantity.text = productList[position].productQuantity
            val price = "₹" + calculateDiscountPrice(productList[position].price, productList[position].offer)
            holder.productPrice.text = price
            val url = (productList[position].mainImage)
            SetProductImage.setImageView(holder.productImage,url,file)
            setUpListeners(holder,holder.absoluteAdapterPosition)
        }
    }

    private fun setUpListeners(holder: ProductLargeImageHolder, position: Int) {
        holder.itemView.setOnClickListener {
            try {
                ProductListFragment.selectedPos = holder.absoluteAdapterPosition

                ProductListFragment.selectedProduct.value =
                    productList[holder.absoluteAdapterPosition]
                fragment.parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                    .replace(R.id.fragmentMainLayout, ProductDetailFragment())
                    .addToBackStack("Product Detail Fragment")
                    .commit()
            }
            catch (e:Exception){

            }
        }

        holder.removeSymbolButton.setOnClickListener {
            if(holder.absoluteAdapterPosition==position) {
                val count = --countList[position]
                val positionVal = calculateDiscountPrice(productList[position].price, productList[position].offer)
                if (count == 0) {
                    productsSize--
                    if (tag == "P" || tag == "O") {
                        productListViewModel.getSpecificCart(MainActivity.cartId,productList[position].productId.toInt()){cart ->
                            if (cart != null) {
                                productListViewModel.removeProductInCart(cart)
                            }
                            ProductListFragment.totalCost.postValue(ProductListFragment.totalCost.value!! - positionVal)
                            CartFragment.viewPriceDetailData.postValue(CartFragment.viewPriceDetailData.value!! - positionVal)
                        }
                        FindNumberOfCartItems.productCount.value = FindNumberOfCartItems.productCount.value!!-1
                        holder.productAddRemoveLayout.visibility = View.GONE
                        holder.productAddOneTime.visibility = View.VISIBLE
                    } else if (tag == "C") {
                        productListViewModel.getSpecificCart(MainActivity.cartId,productList[position].productId.toInt()){cart ->
                            if (cart != null) {
                                productListViewModel.removeProductInCart(cart)
                            }
                            CartFragment.viewPriceDetailData.postValue(CartFragment.viewPriceDetailData.value!! - positionVal)
                        }
                        productList.removeAt(position)
                        countList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, productList.size)
                        FindNumberOfCartItems.productCount.value = FindNumberOfCartItems.productCount.value!!-1
                    }
                    holder.totalItems.text = "0"

                }
                else {
//                    val product = productList[position].copy(availableItems = productList[position].availableItems+1)
//                        retailerDb.updateProduct(product)
                    productListViewModel.updateItemsInCart(
                        if(productList[position].offer==-1f) {
                            Cart(
                                MainActivity.cartId,
                                productList[position].productId.toInt(),
                                count,
                                productList[position].price
                            )
                        }
                        else
                        {
                            Cart(
                                MainActivity.cartId,
                                productList[position].productId.toInt(),
                                count,calculateDiscountPrice(
                                    productList[position].price,
                                    productList[position].offer)
                            )
                        })
                    holder.totalItems.text = count.toString()
                    ProductListFragment.totalCost.value =
                        ProductListFragment.totalCost.value!! - positionVal
                    CartFragment.viewPriceDetailData.value =
                        CartFragment.viewPriceDetailData.value!! - positionVal
                }
            }
        }

        holder.addSymbolButton.setOnClickListener {
            if (holder.adapterPosition == position) {
                val count = ++countList[position]
                ProductListFragment.totalCost.value =
                    ProductListFragment.totalCost.value!! + calculateDiscountPrice(productList[position].price, productList[position].offer)
                CartFragment.viewPriceDetailData.value =
                    CartFragment.viewPriceDetailData.value!! + calculateDiscountPrice(productList[position].price, productList[position].offer)
                var cart = if(productList[position].offer==-1f) {
                    Cart(
                        MainActivity.cartId,
                        productList[position].productId.toInt(),
                        count,
                        productList[position].price
                    )
                }
                else
                { Cart(
                    MainActivity.cartId,
                    productList[position].productId.toInt(),
                    count,calculateDiscountPrice(
                        productList[position].price,
                        productList[position].offer)
                )
                }
                productListViewModel.updateItemsInCart(cart)
                holder.totalItems.text = count.toString()
            }
        }

            holder.productAddOneTime.setOnClickListener {
                if (holder.absoluteAdapterPosition == position) {
                    val count = ++countList[position]
                    productsSize++
                    holder.totalItems.text = count.toString()
                    ProductListFragment.totalCost.value =
                        ProductListFragment.totalCost.value!! + calculateDiscountPrice(productList[position].price, productList[position].offer)
                    CartFragment.viewPriceDetailData.value =
                        CartFragment.viewPriceDetailData.value!! + calculateDiscountPrice(
                            productList[position].price, productList[position].offer)
                    var cart = if(productList[position].offer==-1f) {
                        Cart(
                            MainActivity.cartId,
                            productList[position].productId.toInt(),
                            count,
                            productList[position].price
                        )
                    }
                    else
                    { Cart(
                        MainActivity.cartId,
                        productList[position].productId.toInt(),
                        count,calculateDiscountPrice(
                            productList[position].price,
                            productList[position].offer)
                    )
                    }
                    productListViewModel.updateItemsInCart(cart)
                    FindNumberOfCartItems.productCount.value = FindNumberOfCartItems.productCount.value!!+1
                    holder.productAddRemoveLayout.visibility = View.VISIBLE
                    holder.productAddOneTime.visibility = View.GONE
                }
            }
    }

    fun setProducts(newList:List<Product>){
        productsSize = newList.size
        val diffUtil = CartItemsDiffUtil(productList,newList)
        for(i in 0..<newList.size){
            countList.add(i,0)
        }
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        productList.clear()
        productList.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }




    private fun calculateDiscountPrice(price:Float, offer:Float):Float{
        if(offer>0f) {
            return price - (price * (offer / 100))
        }
        else{
            return price
        }
    }
}