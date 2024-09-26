package com.example.shoppinggroceryapp.views.userviews.help

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.MainActivity
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.dategenerator.DateGenerator
import com.example.shoppinggroceryapp.helpers.fragmenttransaction.FragmentTransaction
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.entities.help.CustomerRequest
import com.example.shoppinggroceryapp.model.entities.order.OrderDetails
import com.example.shoppinggroceryapp.views.sharedviews.orderviews.orderlist.OrderListFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class Help : Fragment() {


    private lateinit var helpViewModel: HelpViewModel
    companion object{
        var selectedOrder:OrderDetails? = null
        var backPressed = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_help, container, false)
        view.findViewById<MaterialToolbar>(R.id.helpReqTool).setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val req = view.findViewById<TextView>(R.id.customerRequestHelpFrag)
        helpViewModel = ViewModelProvider(this,
            HelpViewModelFactory(
                AppDatabase.getAppDatabase(requireContext()).getUserDao()
            )
        )[HelpViewModel::class.java]
        val orderGroup = view.findViewById<LinearLayout>(R.id.orderViewLayout)
        if(selectedOrder ==null){
            val orderListFragment = OrderListFragment()
            orderListFragment.arguments = Bundle().apply {
                putBoolean("isClickable",true)
            }
            FragmentTransaction.navigateWithBackstack(parentFragmentManager,orderListFragment,"Select the order")
        }
        else{
            val selectOrderFrag = parentFragmentManager.findFragmentByTag("SelectOrder")
            selectOrderFrag?.let{
                parentFragmentManager.beginTransaction()
                    .remove(selectOrderFrag)
                    .commit()
            }
            val selectedOrderView = LayoutInflater.from(context).inflate(R.layout.order_layout,
                container,false)
            if(selectedOrder!!.deliveryStatus=="Pending"){
                val screen = "Expected On: ${DateGenerator.getDayAndMonth(selectedOrder!!.deliveryDate)}"
                selectedOrderView.findViewById<TextView>(R.id.deliveryDate).text = screen
            }
            else{
                val screen = "Delivered On: ${DateGenerator.getDayAndMonth(selectedOrder!!.deliveryDate)}"
                selectedOrderView.findViewById<TextView>(R.id.deliveryDate).text = screen
            }
            val orderDate = "Ordered On: ${DateGenerator.getDayAndMonth(selectedOrder!!.orderedDate)}"
            selectedOrderView.findViewById<TextView>(R.id.orderedDate).text = orderDate
            helpViewModel.assignProductList(selectedOrder!!.cartId)
            helpViewModel.productList.observe(viewLifecycleOwner){
                selectedOrderView.findViewById<TextView>(R.id.orderedProductsList).text = it
            }
            selectedOrderView.findViewById<ImageView>(R.id.imageView).visibility = View.GONE
            orderGroup.addView(selectedOrderView)
            view.findViewById<MaterialButton>(R.id.sendReqBtn).setOnClickListener {
                if(req.text.toString().isNotEmpty()){
                    Toast.makeText(requireContext(),"Request Sent Successfully",Toast.LENGTH_SHORT).show()
                    var orderId = selectedOrder!!.orderId
                    helpViewModel.sendReq(CustomerRequest(0,MainActivity.userId.toInt(),
                        DateGenerator.getCurrentDate(),
                        orderId,req.text.toString()))
                    parentFragmentManager.popBackStack()
                }
                else{
                    Toast.makeText(requireContext(),"Please Write the Request",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    override fun onPause() {
        super.onPause()
        backPressed = true
        selectedOrder = null
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
    }
}