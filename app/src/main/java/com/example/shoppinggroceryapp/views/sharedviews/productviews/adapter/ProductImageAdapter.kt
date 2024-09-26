package com.example.shoppinggroceryapp.views.sharedviews.productviews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.imagehandlers.ImageLoaderAndGetter

class ProductImageAdapter(var fragment:Fragment, var imageList:List<String>, var imageLoader: ImageLoaderAndGetter) :RecyclerView.Adapter<ProductImageAdapter.ProductViewHolder>(){

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
            holder.itemView.findViewById<ImageView>(R.id.productImage).setImageBitmap(imageBitmap)
        }
    }
}