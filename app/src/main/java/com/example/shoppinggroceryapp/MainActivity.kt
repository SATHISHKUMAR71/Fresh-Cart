package com.example.shoppinggroceryapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppinggroceryapp.fragments.appfragments.InitialFragment
import com.example.shoppinggroceryapp.fragments.authentication.LoginFragment
import com.example.shoppinggroceryapp.model.dao.UserDao
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.database.AppDatabase.Companion.getAppDatabase
import com.example.shoppinggroceryapp.model.entities.order.CartMapping
import com.example.shoppinggroceryapp.model.entities.products.BrandData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product


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
        val boo = pref.getBoolean("isSigned",false)
        isRetailer = pref.getBoolean("isRetailer",false)
        userFirstName = pref.getString("userFirstName","User").toString()
        userLastName = pref.getString("userLastName","User").toString()
        userId = pref.getString("userId","userId").toString()
        userEmail = pref.getString("userEmail","userEmail").toString()
        userPhone = pref.getString("userPhone","userPhone").toString()
        userImage = pref.getString("userProfile","userImage").toString()
        if(boo){
            println("Initial Fragment Created From Activity")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentBody, InitialFragment())
                .commit()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentBody,LoginFragment())
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