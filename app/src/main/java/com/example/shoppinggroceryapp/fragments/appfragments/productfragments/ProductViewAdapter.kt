package com.example.shoppinggroceryapp.fragments.appfragments.productfragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.model.entities.products.Images

class ProductViewAdapter(var fragment:Fragment,var imageList:List<String>,var imageLoader: ImageLoaderAndGetter) :RecyclerView.Adapter<ProductViewAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(productImageView:View):RecyclerView.ViewHolder(productImageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_image_view_in_detail,parent,false))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        if(holder.absoluteAdapterPosition == position) {
            var imageBitmap = imageLoader.getImageInApp(fragment.requireContext(),imageList[position])
            println("IMAGE BITMAP: $imageBitmap")
            holder.itemView.findViewById<ImageView>(R.id.productImage).setImageBitmap(imageBitmap)
        }
    }
}