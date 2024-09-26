package com.example.shoppinggroceryapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.LruCache
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.views.sharedviews.authenticationviews.login.LoginFragment
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.database.AppDatabase.Companion.getAppDatabase
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.views.initialview.InitialDataSetter


class MainActivity : AppCompatActivity() {

    private lateinit var fragmentTopBarView:FrameLayout
    private lateinit var fragmentBottomBarView:FrameLayout
    companion object{
        val handler = Handler(Looper.getMainLooper())
        private const val REQUEST_CAMERA_PERMISSION = 200
        private val permissions = arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO)
        var userFirstName = ""
        var userLastName = ""
        var userId = "-1"
        var userEmail = ""
        var imageCache = LruCache<String,Bitmap>((Runtime.getRuntime().maxMemory()/4).toInt())
        var userPhone = ""
        var cartId = 0
        var userImage = ""
        var isRetailer = false
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread{
            getAppDatabase(baseContext).getUserDao().initDB()
        }.start()
        val pref = getSharedPreferences("freshCart", Context.MODE_PRIVATE)
        var initialDataSetter = InitialDataSetter()
        initialDataSetter.setInitialDataForUser(pref)
        val boo = pref.getBoolean("isSigned",false)
        if(boo){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, InitialFragment())
                .commit()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentBody, LoginFragment())
                .commit()
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100)
        }
        val db2 = AppDatabase.getAppDatabase(baseContext).getUserDao()
        if(boo) {
            assignCart(db2)
        }
    }

    private fun assignCart(db2:UserDao){
        Thread {
            val cart: CartMapping? = db2.getCartForUser(userId.toInt())
            if (cart == null) {
                db2.addCartForUser(CartMapping(0, userId = userId.toInt(), "available"))
                val newCart = db2.getCartForUser(userId.toInt())
                cartId = newCart.cartId
            } else {
                cartId = cart.cartId
            }
        }.start()
    }
}