package com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.ordersummary

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.dategenerator.DateGenerator
import com.example.shoppinggroceryapp.helpers.fragmenttransaction.FragmentTransaction
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist.OrderListFragment
import com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.adapter.ProductViewAdapter
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.order.DailySubscription
import com.example.shoppinggroceryapp.model.entities.order.MonthlyOnce
import com.example.shoppinggroceryapp.model.entities.order.TimeSlot
import com.example.shoppinggroceryapp.model.entities.order.WeeklyOnce
import com.example.shoppinggroceryapp.views.userviews.addressview.savedaddress.SavedAddressList
import com.example.shoppinggroceryapp.views.userviews.cartview.cart.CartFragment
import com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.PaymentFragment
import com.example.shoppinggroceryapp.views.userviews.ordercheckoutviews.TimeSlots
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.io.File


class OrderSummaryFragment : Fragment() {


    var once = false
    var weeklyOnce = false
    var daily = false
    var monthlyOnce = false
    var tmpCart:Int? = null
    var tmpAddress:Int? = null
    private lateinit var orderSummaryViewModel:OrderSummaryViewModel
    var tmpOrderId:Int? = null
    private lateinit var deliveryFrequency:MaterialAutoCompleteTextView
    private lateinit var radioGroupTimeSlot:RadioGroup
    private lateinit var deliveryFrequencyDay:MaterialAutoCompleteTextView
    private lateinit var noteForUserLayout: LinearLayout
    private lateinit var dayOfMonth:MaterialAutoCompleteTextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val totalItems = arguments?.getInt("noOfItems")
        val days = arrayOf("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")
        val daysOfMonth = arrayOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28")
        val view =  inflater.inflate(R.layout.fragment_order_summary, container, false)
        val dayOfMonthLayout = view.findViewById<LinearLayout>(R.id.deliveryFrequencyDayMonthLayout)
        val noteForUser = view.findViewById<TextView>(R.id.noteForUser)
        dayOfMonth = view.findViewById<MaterialAutoCompleteTextView>(R.id.deliveryFrequencyDayMonth)
        val addressOwnerName = view.findViewById<TextView>(R.id.addressOwnerNameOrderSummary)
        radioGroupTimeSlot = view.findViewById<RadioGroup>(R.id.radioGroupTimeSlot)
        val addressValue = view.findViewById<TextView>(R.id.addressOrderSummary)
        val addressNumber = view.findViewById<TextView>(R.id.addressPhoneOrderSummary)
        val changeAddressButton = view.findViewById<MaterialButton>(R.id.changeAddressButtonOrderSummary)
        val noOfItems = view.findViewById<TextView>(R.id.priceDetailsMrpTotalItemsOrderSummary)
        val mrpPrice = view.findViewById<TextView>(R.id.priceDetailsMrpPriceOrderSummary)
        val totalAmount = view.findViewById<TextView>(R.id.priceDetailsTotalAmountOrderSummary)
        val continueToPayment = view.findViewById<MaterialButton>(R.id.continueButtonOrderSummary)
        val viewProductDetails = view.findViewById<MaterialButton>(R.id.viewPriceDetailsButtonOrderSummary)
        val orderSummaryToolBar = view.findViewById<MaterialToolbar>(R.id.orderSummaryToolbar)
        orderSummaryViewModel = ViewModelProvider(this,
            OrderSummaryViewModelFactory(retailerDao = AppDatabase.getAppDatabase(requireContext()).getRetailerDao())
        )[OrderSummaryViewModel::class.java]
        val recyclerViewProducts = view.findViewById<RecyclerView>(R.id.orderListRecyclerView)
        deliveryFrequency = view.findViewById<MaterialAutoCompleteTextView>(R.id.deliveryFrequency)
        deliveryFrequencyDay = view.findViewById<MaterialAutoCompleteTextView>(R.id.deliveryFrequencyDay)
        val scrollView = view.findViewById<ScrollView>(R.id.orderSummaryScrollView)
        noteForUserLayout = view.findViewById(R.id.noteForUserLayout)
        val deliveryFrequencyDayLayout = view.findViewById<LinearLayout>(R.id.deliveryFrequencyDayLayout)
        val timeSlotLayout = view.findViewById<LinearLayout>(R.id.timeSlotLayout)

        tmpCart = arguments?.getInt("cartId")
        tmpAddress = arguments?.getInt("selectedAddressId")
        tmpOrderId = arguments?.getInt("orderId")
        tmpCart?.let {
            if(it!=0) {
                orderSummaryViewModel.getProductsWithCartId(cartId = it)
            }
        }

        tmpAddress?.let {
            if(it!=0) {
                view.findViewById<LinearLayout>(R.id.deliveryAddressLayoutOrderSummary).visibility =
                    View.GONE
                continueToPayment.text = "Update Order"
                view.findViewById<TextView>(R.id.textView).visibility = View.GONE
                view.findViewById<LinearLayout>(R.id.priceDetailsOrderSummary).visibility =
                    View.GONE
                view.findViewById<LinearLayout>(R.id.linearLayout11)
                    .setBackgroundColor(Color.TRANSPARENT)
                viewProductDetails.visibility = View.GONE
            }
        }
        if(tmpCart==0) {
            orderSummaryViewModel.getProductsWithCartId(cartId = MainActivity.cartId)
        }
        orderSummaryViewModel.cartItems.observe(viewLifecycleOwner){
            ProductViewAdapter.productsList = it
            recyclerViewProducts.adapter = ProductViewAdapter(File(requireContext().filesDir,"AppImages"))
            recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
        val addressVal = "${CartFragment.selectedAddress?.buildingName}, ${CartFragment.selectedAddress?.streetName}, ${CartFragment.selectedAddress?.city}, ${CartFragment.selectedAddress?.state}, ${CartFragment.selectedAddress?.postalCode}"
        addressOwnerName.text = CartFragment.selectedAddress?.addressContactName
        addressValue.text = addressVal
        addressNumber.text = CartFragment.selectedAddress?.addressContactNumber

        val items = "MRP ($totalItems Products)"
        noOfItems.text = items
        val mrpAmt = "₹${CartFragment.viewPriceDetailData.value!!-49}"
        val grandAmt = "₹${CartFragment.viewPriceDetailData.value!!}"
        val priceDetails = "$grandAmt\nView Price Details"
        viewProductDetails.text = priceDetails
        mrpPrice.text =mrpAmt
        totalAmount.text = grandAmt
        viewProductDetails.setOnClickListener {
            scrollView.fullScroll(View.FOCUS_DOWN)
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
        changeAddressButton.setOnClickListener {
            val savedAddressList = SavedAddressList()
            savedAddressList.arguments = Bundle().apply {
                putBoolean("clickable",true)
            }
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,savedAddressList,"Get the address from saved address")
        }
        deliveryFrequency.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun afterTextChanged(s: Editable?) {
                if(s.toString()=="Weekly Once"){
                    var day = DateGenerator.getCurrentDay()
                    noteForUserLayout.visibility = View.VISIBLE
                    noteForUser.text = "Weekly deliveries kick off tomorrow! You can easily cancel your orders through your order history."
                    var newDays = days.filter { it!=day }.toTypedArray()
                    deliveryFrequencyDay.setSimpleItems(newDays)
                    dayOfMonth.setText("")
                    for(i in newDays){
                    }
                    weeklyOnce = true
                    once = false
                    monthlyOnce = false
                    daily = false
                    deliveryFrequencyDayLayout.visibility = View.VISIBLE
                    dayOfMonthLayout.visibility = View.GONE
                    timeSlotLayout.visibility = View.VISIBLE
                }
                else if(s.toString()=="Monthly Once"){
                    var day = DateGenerator.getCurrentDayOfMonth()
                    deliveryFrequencyDay.setText("")
                    var newDays = daysOfMonth.filter { it!=day }.toTypedArray()
                    dayOfMonth.setSimpleItems(newDays)
                    for(i in newDays){
                    }
                    noteForUserLayout.visibility = View.VISIBLE
                    noteForUser.text = "Start your monthly delivery service tomorrow! You can easily cancel your orders in your order history"
                    weeklyOnce = false
                    once = false
                    monthlyOnce = true
                    daily = false
                    dayOfMonthLayout.visibility = View.VISIBLE
                    deliveryFrequencyDayLayout.visibility = View.GONE
                    timeSlotLayout.visibility = View.VISIBLE
                }
                else if(s.toString() == "Daily"){
                    deliveryFrequencyDay.setText("")
                    dayOfMonth.setText("")
                    noteForUserLayout.visibility = View.VISIBLE
                    noteForUser.text = "Get ready for daily deliveries starting tomorrow! Cancel your orders anytime in the order history."
                    weeklyOnce = false
                    once = false
                    monthlyOnce = false
                    daily = true
                    timeSlotLayout.visibility = View.VISIBLE
                    dayOfMonthLayout.visibility = View.GONE
                    deliveryFrequencyDayLayout.visibility = View.GONE
                }
                else{
                    weeklyOnce = false
                    once = true
                    monthlyOnce = false
                    daily = false
                    noteForUserLayout.visibility = View.GONE
                    deliveryFrequencyDay.setText("")
                    dayOfMonth.setText("")
                    dayOfMonthLayout.visibility = View.GONE
                    deliveryFrequencyDayLayout.visibility = View.GONE
                    timeSlotLayout.visibility = View.GONE
                }
            }
        })


        orderSummaryToolBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        continueToPayment.setOnClickListener {
            if(deliveryFrequency.text.isEmpty()){
                Snackbar.make(view,"Please Select the Delivery Frequency",Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(Color.RED)
                }.show()
            }
            else {
                if(once){
                    doPaymentTransaction(null,null,null)
                }
                else if(daily){
                    checkSlotChosen(1)
                }
                else if(weeklyOnce){
                    checkSlotChosen(2)
                }
                else if(monthlyOnce){
                    checkSlotChosen(3)
                }
            }
        }
        return view
    }

    private fun checkSlotChosen(choice:Int?) {
        if(radioGroupTimeSlot.checkedRadioButtonId==-1){
            view?.let {
                showSnackBar("Please choose the Slot")
            }
        }
        else{
            when(choice){
                1 -> doPaymentTransaction(radioGroupTimeSlot.findViewById<RadioButton>(radioGroupTimeSlot.checkedRadioButtonId).text.toString(),null,null)
                2 -> {
                    if(deliveryFrequencyDay.text.isEmpty()){
                        showSnackBar("Please choose the Day")
                    }
                    else {
                        var weekDay: Int = -1
                        when (deliveryFrequencyDay.text.toString()) {
                            "Sunday" -> weekDay = 0
                            "Monday" -> weekDay = 1
                            "Tuesday" -> weekDay = 2
                            "Wednesday" -> weekDay = 3
                            "Thursday" -> weekDay = 4
                            "Friday" -> weekDay = 5
                            "Saturday" -> weekDay = 6
                        }
                        doPaymentTransaction(
                            radioGroupTimeSlot.findViewById<RadioButton>(
                                radioGroupTimeSlot.checkedRadioButtonId
                            ).text.toString(), null, weekDay
                        )
                    }
                }
                3 ->{
                    if(dayOfMonth.text.isEmpty()){
                        showSnackBar("Please Choose the Day of Month")
                    }
                    else{
                        try{
                            doPaymentTransaction(
                                radioGroupTimeSlot.findViewById<RadioButton>(
                                    radioGroupTimeSlot.checkedRadioButtonId
                                ).text.toString(), dayOfMonth.text.toString().toInt(), null
                            )
                        }
                        catch (e:Exception){
                        }
                    }
                }
            }
        }
    }

    fun showSnackBar(text:String){
        view?.let {
            Snackbar.make(it,text,Snackbar.LENGTH_SHORT).apply {
                setBackgroundTint(Color.RED)
            }.show()
        }
    }
    fun doPaymentTransaction(timeSlot:String?,dayOfMonth:Int?,dayOfWeek:Int?){
        var paymentFragment = PaymentFragment()
        var timeId:Int = -1
        paymentFragment.arguments = Bundle().apply {
            putString("deliveryFrequency",deliveryFrequency.text.toString())
            timeSlot?.let {
                when(it){
                    TimeSlots.EARLY_MORNING.timeDetails -> {
                        timeId = 0
                        putString("timeSlot", TimeSlots.EARLY_MORNING.timeDetails)
                        putInt("timeSlotInt",0)
                    }
                    TimeSlots.MID_MORNING.timeDetails -> {
                        timeId = 1
                        putString("timeSlot", TimeSlots.MID_MORNING.timeDetails)
                        putInt("timeSlotInt",1)
                    }
                    TimeSlots.AFTERNOON.timeDetails -> {
                        timeId = 2
                        putString("timeSlot", TimeSlots.AFTERNOON.timeDetails)
                        putInt("timeSlotInt",2)
                    }
                    TimeSlots.EVENING.timeDetails -> {
                        timeId = 3
                        putString("timeSlot", TimeSlots.EVENING.timeDetails)
                        putInt("timeSlotInt",3)
                    }
                }
            }
            dayOfMonth?.let {
                putInt("dayOfMonth",it)
            }
            dayOfWeek?.let {
                putInt("dayOfWeek",it)
            }
        }
        if(tmpAddress!=0 && tmpCart != 0 && tmpOrderId!=0){
            OrderListFragment.selectedOrder?.let {
                orderSummaryViewModel.updateOrderDetails(it.copy(deliveryFrequency = deliveryFrequency.text.toString()))
                orderSummaryViewModel.updateTimeSlot(TimeSlot(tmpOrderId!!,timeId))
                when(deliveryFrequency.text.toString()){
                    "Monthly Once" -> {orderSummaryViewModel.updateMonthly(MonthlyOnce(tmpOrderId!!,dayOfMonth!!))}
                    "Weekly Once" -> {orderSummaryViewModel.updateWeekly(WeeklyOnce(tmpOrderId!!,dayOfWeek!!))}
                    "Daily" -> {orderSummaryViewModel.updateDaily(DailySubscription(tmpOrderId!!))}
                    "Once" -> {
                        orderSummaryViewModel.deleteDaily(tmpOrderId!!)
                        orderSummaryViewModel.deleteWeekly(tmpOrderId!!)
                        orderSummaryViewModel.deleteMonthly(tmpOrderId!!)
                    }
                }
            }
            Toast.makeText(context,"Delivery Subscription Updated Successfully",Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            parentFragmentManager.popBackStack()
        }
        else {
            FragmentTransaction.navigateWithBackstack(
                parentFragmentManager,
                paymentFragment,
                "Payment Fragment"
            )
        }
    }
    override fun onResume() {
        super.onResume()
        InitialFragment.hideBottomNav.value = true
        InitialFragment.hideSearchBar.value = true
    }

    override fun onPause() {
        super.onPause()
        InitialFragment.hideBottomNav.value = false
        InitialFragment.hideSearchBar.value = false
    }

    override fun onDestroy() {
        super.onDestroy()
        tmpCart = null
        tmpOrderId = null
        tmpAddress = null
    }
}