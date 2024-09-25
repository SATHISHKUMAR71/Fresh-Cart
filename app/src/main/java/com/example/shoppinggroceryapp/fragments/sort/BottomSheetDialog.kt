package com.example.shoppinggroceryapp.fragments.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import com.example.shoppinggroceryapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetDialog:BottomSheetDialogFragment() {

    companion object {
        var selectedOption: MutableLiveData<Int> = MutableLiveData()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sort_layout,container,false)
        val radioButtonManu = view.findViewById<RadioButton>(R.id.manufactureDataRadioButton)
        val expiryRadioButton = view.findViewById<RadioButton>(R.id.expiryDataRadioButton)
        val priceLowToHighRadioButton = view.findViewById<RadioButton>(R.id.priceLowToHighRadioButton)
        val discountRadioButton = view.findViewById<RadioButton>(R.id.discountRadioButton)
        val priceHighToLow = view.findViewById<RadioButton>(R.id.priceHighToLowRadioButton)
        view.findViewById<MaterialButton>(R.id.sortByManufacturedDate).setOnClickListener {
            radioButtonManu.isChecked =true
            selectedOption.value = 0
            dismiss()
        }
        view.findViewById<MaterialButton>(R.id.sortByExpiryDate).setOnClickListener {
            expiryRadioButton.isChecked =true
            selectedOption.value = 1
            dismiss()
        }
        view.findViewById<MaterialButton>(R.id.sortByDiscount).setOnClickListener {
            selectedOption.value = 2
            discountRadioButton.isChecked =true
            dismiss()
        }
        view.findViewById<MaterialButton>(R.id.sortByPriceLowToHigh).setOnClickListener {
            priceLowToHighRadioButton.isChecked =true
            selectedOption.value = 3
            dismiss()
        }
        view.findViewById<MaterialButton>(R.id.sortByPriceHightoLow).setOnClickListener {
            priceHighToLow.isChecked =true
            selectedOption.value = 4
            dismiss()
        }

        selectedOption.observe(viewLifecycleOwner){
            if(it == 0){
                radioButtonManu.isChecked =true
            }
            else if(it == 1){
                expiryRadioButton.isChecked =true
            }
            else if(it == 2){
                discountRadioButton.isChecked =true
            }
            else if(it == 3){
                priceLowToHighRadioButton.isChecked =true
            }
            else if(it == 4){
                priceHighToLow.isChecked =true
            }
        }

        priceHighToLow.setOnClickListener {
            selectedOption.value = 4
            dismiss()
        }
        radioButtonManu.setOnClickListener{
            selectedOption.value = 0
            dismiss()
        }
        expiryRadioButton.setOnClickListener {
            selectedOption.value = 1
            dismiss()
        }
        priceLowToHighRadioButton.setOnClickListener {
            selectedOption.value = 3
            dismiss()
        }
        discountRadioButton.setOnClickListener {
            selectedOption.value = 2
            dismiss()
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        println("ON BOTTOM DESTROY")
//        selectedOption.value = null
    }
}