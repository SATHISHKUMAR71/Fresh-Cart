package com.example.shoppinggroceryapp.fragments.appfragments.recyclerview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.ImageHandler
import com.example.shoppinggroceryapp.fragments.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.dataclass.ChildCategoryName
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.viewmodel.categoryviewmodel.CategoryViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainCategoryAdapter(var fragment: Fragment, private var mainCategoryList: List<ParentCategory>,private var childCategoryList:List<List<ChildCategoryName>>,var imageLoader:ImageLoaderAndGetter):RecyclerView.Adapter<MainCategoryAdapter.MainCategoryHolder>() {

    companion object{
        var expandedData = mutableSetOf<Int>()
    }
    var childList = mutableListOf<ChildCategoryName>()
    inner class MainCategoryHolder(mainCategoryView:View):RecyclerView.ViewHolder(mainCategoryView){
        val invisibleView = itemView.findViewById<RecyclerView>(R.id.subCategoryRecyclerView)
        val addSymbol = itemView.findViewById<ImageView>(R.id.addSymbol)
        val parentCategoryName = itemView.findViewById<TextView>(R.id.parentCategoryName)
        val parentCategoryDescription = itemView.findViewById<TextView>(R.id.parentCategoryDescription)
        val parentCategoryImage = itemView.findViewById<ImageView>(R.id.parentCategoryImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryHolder {
        return MainCategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_category_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return mainCategoryList.size
    }

    override fun onBindViewHolder(holder: MainCategoryHolder, position: Int) {
        holder.parentCategoryName.text = mainCategoryList[position].parentCategoryName
        holder.parentCategoryDescription.text = mainCategoryList[position].parentCategoryDescription
        holder.parentCategoryImage.setImageBitmap(imageLoader.getImageInApp(fragment.requireContext(),mainCategoryList[position].parentCategoryImage))
        refreshViews(fragment.requireContext(),position,holder)
        println("EXPANDED DATA IN ADAPTER: ${expandedData}")
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition == position) {
                if (holder.invisibleView.isVisible) {
                    holder.invisibleView.animate()
                        .alpha(0f)
                        .scaleY(0f)
                        .setDuration(0)
                        .withEndAction {
                            expandedData.remove(position)
                            holder.invisibleView.visibility = View.GONE
                            holder.addSymbol.setImageDrawable(
                                ContextCompat.getDrawable(
                                    fragment.requireContext(),
                                    R.drawable.expand_circle_down_24px
                                )
                            )
                        }
                } else {
                    val categoryList = childCategoryList[position]
                    holder.addSymbol.setImageDrawable(
                        ContextCompat.getDrawable(
                            fragment.requireContext(),
                            R.drawable.expand_circle_up_24px
                        )
                    )
                    holder.invisibleView.adapter = SubCategoryAdapter(fragment, categoryList)
                    holder.invisibleView.layoutManager = LinearLayoutManager(fragment.requireContext())
                    holder.invisibleView.visibility = View.VISIBLE
                    holder.invisibleView.alpha = 0f
                    holder.invisibleView.scaleY = 0f
                    holder.invisibleView.animate()
                        .alpha(1f)
                        .scaleY(1f)
                        .setDuration(0)
                    expandedData.add(position)
                }
            }
        }

    }

    private fun refreshViews(context: Context, position: Int,holder: MainCategoryHolder) {
        if(expandedData.contains(position)){
            holder.invisibleView.visibility = View.VISIBLE
            holder.addSymbol.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.expand_circle_up_24px
                )
            )

            val categoryList = childCategoryList[position]
            holder.invisibleView.adapter = SubCategoryAdapter(fragment, categoryList)
            holder.invisibleView.layoutManager = LinearLayoutManager(context)


        }
        else{
            holder.addSymbol.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.expand_circle_down_24px
                )
            )
            holder.invisibleView.visibility = View.GONE
        }
    }

}