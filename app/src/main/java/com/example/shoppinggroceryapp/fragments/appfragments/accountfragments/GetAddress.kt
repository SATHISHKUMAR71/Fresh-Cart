package com.example.shoppinggroceryapp.fragments.appfragments.accountfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.authentication.InputChecker
import com.example.shoppinggroceryapp.fragments.authentication.TextLayoutInputChecker
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.user.Address
import com.example.shoppinggroceryapp.viewmodel.accountviewmodel.GetAddressViewModel
import com.example.shoppinggroceryapp.viewmodel.accountviewmodel.GetAddressViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class GetAddress : Fragment() {
    private lateinit var fullName: TextInputEditText
    private lateinit var fullNameLayout:TextInputLayout
    private lateinit var phoneLayout:TextInputLayout
    private lateinit var houseLayout:TextInputLayout
    private lateinit var streetLayout: TextInputLayout
    private lateinit var cityLayout: TextInputLayout
    private lateinit var stateLayout: TextInputLayout
    private lateinit var postalCodeLayout: TextInputLayout
    private lateinit var phone: TextInputEditText
    private lateinit var houseNo: TextInputEditText
    private lateinit var street: TextInputEditText
    private lateinit var state: AutoCompleteTextView
    private lateinit var city: TextInputEditText
    private lateinit var postalCode: TextInputEditText
    private lateinit var saveAddress:MaterialButton
    private lateinit var addressTopBar:MaterialToolbar
    private lateinit var addressInputChecker:InputChecker
    private lateinit var getAddressViewModel:GetAddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressInputChecker = TextLayoutInputChecker()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_get_address, container, false)
        fullNameLayout = view.findViewById(R.id.addressFirstNameLayout)
        phoneLayout = view.findViewById(R.id.addressPhoneNumberLayout)
        houseLayout =view.findViewById(R.id.addressHouseLayout)
        streetLayout = view.findViewById(R.id.addressStreetLayout)
        cityLayout = view.findViewById(R.id.addressCityLayout)
        stateLayout = view.findViewById(R.id.addressStateLayout)
        postalCodeLayout = view.findViewById(R.id.addressPostalCodeLayout)
        getAddressViewModel = ViewModelProvider(this, GetAddressViewModelFactory(AppDatabase.getAppDatabase(requireContext()).getUserDao()))[GetAddressViewModel::class.java]
        fullName = view.findViewById(R.id.fullName)
        phone = view.findViewById(R.id.addPhoneNumber)
        houseNo = view.findViewById(R.id.addAddressHouse)
        street = view.findViewById(R.id.addAddressStreetName)
        state = view.findViewById(R.id.addAddressState)
        city = view.findViewById(R.id.addAddressCity)
        postalCode = view.findViewById(R.id.addAddressPostalCode)
        saveAddress = view.findViewById(R.id.addNewAddress)
        addressTopBar = view.findViewById(R.id.getAddressToolbar)
        phone.filters = arrayOf(InputFilter.LengthFilter(15))
        postalCode.filters = arrayOf(InputFilter.LengthFilter(8))
        if(SavedAddress.editAddress!=null){
            SavedAddress.editAddress?.let {
                fullName.setText(it.addressContactName)
                phone.setText(it.addressContactNumber)
                houseNo.setText(it.buildingName)
                street.setText(it.streetName)
                state.setText(it.state)
                city.setText(it.city)
                postalCode.setText(it.postalCode)
            }
        }
        addressTopBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        addFocusChangeListeners()
        saveAddress.setOnClickListener {
            println("FUL NAME: ${fullName.text} ${addressInputChecker.nameCheck(fullName)}")
            fullNameLayout.error = addressInputChecker.nameCheck(fullName)
            phoneLayout.error = addressInputChecker.lengthAndEmptyCheck("Phone Number",phone,10)
            houseLayout.error = addressInputChecker.emptyCheck(houseNo)
            streetLayout.error = addressInputChecker.emptyCheck(street)
            cityLayout.error = addressInputChecker.emptyCheck(city)
            stateLayout.error = addressInputChecker.emptyCheck(state)
            postalCodeLayout.error = addressInputChecker.lengthAndEmptyCheck("Zip Code",postalCode,6)
            if(fullNameLayout.error==null && houseLayout.error==null && phoneLayout.error==null &&streetLayout.error==null &&
                cityLayout.error ==null && stateLayout.error==null && postalCodeLayout.error == null){
                if(SavedAddress.editAddress!=null){
                    getAddressViewModel.updateAddress(
                    Address(
                        addressId = SavedAddress.editAddress!!.addressId,
                        userId = MainActivity.userId.toInt(),
                        addressContactName = fullName.text.toString(),
                        addressContactNumber = phone.text.toString(),
                        buildingName = houseNo.text.toString(),
                        streetName = street.text.toString(),
                        city = city.text.toString(),
                        state = state.text.toString(),
                        country = "India",
                        postalCode = postalCode.text.toString()
                    ))

                    Toast.makeText(context,"Address Updated Successfully",Toast.LENGTH_SHORT).show()
                }
                else {
                    getAddressViewModel.addAddress(
                        Address(
                            addressId = 0,
                            userId = MainActivity.userId.toInt(),
                            addressContactName = fullName.text.toString(),
                            addressContactNumber = phone.text.toString(),
                            buildingName = houseNo.text.toString(),
                            streetName = street.text.toString(),
                            city = city.text.toString(),
                            state = state.text.toString(),
                            country = "India",
                            postalCode = postalCode.text.toString()
                        )
                    )
                    Toast.makeText(context,"Address Added Successfully",Toast.LENGTH_SHORT).show()
                }

                parentFragmentManager.popBackStack()
            }
            else{
                Toast.makeText(context,"Add all the Required Fields",Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    override fun onResume() {
        super.onResume()
        InitialFragment.hideSearchBar.value = true
        InitialFragment.hideBottomNav.value = true
    }
    override fun onStop() {
        super.onStop()
        InitialFragment.hideSearchBar.value = false
        InitialFragment.hideBottomNav.value = false
        SavedAddress.editAddress = null
    }

    fun addFocusChangeListeners(){
        fullName.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                fullNameLayout.error = null
            }
        }
        phone.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                phoneLayout.error = null
            }
        }
        houseNo.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                houseLayout.error = null
            }
        }
        street.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                streetLayout.error = null
            }
        }
        city.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                cityLayout.error = null
            }
        }
        state.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                stateLayout.error = null
            }
        }
        postalCode.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                postalCodeLayout.error = null
            }
        }

    }
}