package com.example.shoppinggroceryapp.views.retailerviews.addeditproduct


import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinggroceryapp.R
import com.example.shoppinggroceryapp.helpers.permissionhandler.CameraPermissionHandler
import com.example.shoppinggroceryapp.helpers.dategenerator.DateGenerator
import com.example.shoppinggroceryapp.helpers.imagehandlers.ImageHandler
import com.example.shoppinggroceryapp.helpers.imagehandlers.ImageLoaderAndGetter
import com.example.shoppinggroceryapp.helpers.permissionhandler.interfaces.ImagePermissionHandler
import com.example.shoppinggroceryapp.views.initialview.InitialFragment
import com.example.shoppinggroceryapp.views.sharedviews.productviews.productlist.ProductListFragment
import com.example.shoppinggroceryapp.helpers.inputvalidators.interfaces.InputChecker
import com.example.shoppinggroceryapp.helpers.inputvalidators.TextLayoutInputChecker
import com.example.shoppinggroceryapp.model.database.AppDatabase
import com.example.shoppinggroceryapp.model.dataclass.IntWithCheckedData
import com.example.shoppinggroceryapp.model.entities.products.Category
import com.example.shoppinggroceryapp.model.entities.products.ParentCategory
import com.example.shoppinggroceryapp.model.entities.products.Product
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Locale

class AddOrEditProductFragment : Fragment() {


    private lateinit var imageHandler: ImageHandler
    private lateinit var imageLoader: ImageLoaderAndGetter
    private var isNewParentCategory = false
    private var isNewSubCategory = false
    private lateinit var imagePermissionHandler: ImagePermissionHandler
    private var mainImage:String = ""
    private var mainImageBitmap:Bitmap?= null
    private lateinit var childArray:Array<String>
    private lateinit var parentArray:Array<String>
    private var isCategoryImageAdded = true
    private var rawExpiryDate = ""
    private var rawManufactureDate = ""
    var parentCategory = ""
    private lateinit var inputChecker: InputChecker
    var count = 0
    var editingProduct = false
    var fileName=""
    var mainImageClicked = false
    var parentCategoryImageClicked = false
    var parentCategoryImage:Bitmap? = null
    var imageList = mutableMapOf<Int,IntWithCheckedData>()
    var imageStringList = mutableListOf<String>()
    private lateinit var addParentCategoryButton:MaterialButton
    private lateinit var addParentImage:ImageView
    private lateinit var productManufactureDate:TextInputEditText
    private lateinit var productExpiryDate:TextInputEditText
    private lateinit var productParentCategory:MaterialAutoCompleteTextView
    private lateinit var productSubCat:MaterialAutoCompleteTextView
    private lateinit var addParentCategoryLayout:LinearLayout
    private lateinit var addEditProductViewModel: AddEditProductViewModel
    private lateinit var productName:TextInputEditText
    private lateinit var brandName:TextInputEditText
    private lateinit var updateBtn:MaterialButton
    private lateinit var productDescription:TextInputEditText
    private lateinit var productPrice:TextInputEditText
    private lateinit var productOffer:TextInputEditText
    private lateinit var productQuantity:TextInputEditText
    private lateinit var productAvailableItems:TextInputEditText
    private lateinit var isVeg:CheckBox
    private lateinit var imageLayout:LinearLayout
    private lateinit var view:View
    private var deletedImageList = mutableListOf<String>()
    private lateinit var formatter:SimpleDateFormat
    private lateinit var productNameLayout:TextInputLayout
    private lateinit var brandNameLayout:TextInputLayout
    private lateinit var parentCategoryLayout: TextInputLayout
    private lateinit var subCategoryLayout: TextInputLayout
    private lateinit var productDescriptionLayout:TextInputLayout
    private lateinit var priceLayout:TextInputLayout
    private lateinit var offerLayout:TextInputLayout
    private lateinit var productQuantityLayout:TextInputLayout
    private lateinit var availableItemsLayout:TextInputLayout
    private lateinit var manufactureDateLayout:TextInputLayout
    private lateinit var expiryDateLayout:TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageHandler = ImageHandler(this)
        imageHandler.initActivityResults()
        inputChecker = TextLayoutInputChecker()
        imagePermissionHandler = CameraPermissionHandler(this,imageHandler)
        imagePermissionHandler.initPermissionResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val db1 = AppDatabase.getAppDatabase(requireContext())
        addEditProductViewModel = ViewModelProvider(this,
            AddEditProductViewModelFactory(db1.getRetailerDao(),db1.getProductDao())
        )[AddEditProductViewModel::class.java]
        parentArray = arrayOf()
        childArray = arrayOf()
        view =  inflater.inflate(R.layout.fragment_add_edit, container, false)
        productNameLayout = view.findViewById(R.id.productNameLayout)
        brandNameLayout = view.findViewById(R.id.productBrandLayout)
        parentCategoryLayout = view.findViewById(R.id.productParentCatLayout)
        subCategoryLayout = view.findViewById(R.id.productCategoryLayout)
        productDescriptionLayout = view.findViewById(R.id.productDescriptionLayout)
        priceLayout = view.findViewById(R.id.productPriceLayout)
        offerLayout = view.findViewById(R.id.productOfferLayout)
        productQuantityLayout = view.findViewById(R.id.productQuantityLayout)
        availableItemsLayout = view.findViewById(R.id.productAvailableItemsLayout)
        manufactureDateLayout = view.findViewById(R.id.productManufactureLayout)
        expiryDateLayout = view.findViewById(R.id.productExpiryLayout)

        updateBtn = view.findViewById(R.id.updateInventoryBtn)
        addParentCategoryLayout = view.findViewById(R.id.addParentCategoryImageLayout)
        addParentImage = view.findViewById(R.id.addParentCategoryImage)
        addParentCategoryButton = view.findViewById(R.id.addParentCategoryImageButton)
        productName = view.findViewById(R.id.productNameEditFrag)
        brandName = view.findViewById(R.id.productBrandEditFrag)
        productParentCategory = view.findViewById(R.id.productParentCatEditFrag)
        productSubCat = view.findViewById(R.id.productCategoryEditFrag)
        productDescription = view.findViewById(R.id.productDescriptionEditFrag)
        productPrice = view.findViewById(R.id.productPriceEditFrag)
        productOffer = view.findViewById(R.id.productOfferEditFrag)
        productQuantity = view.findViewById(R.id.productQuantityEditFrag)
        productAvailableItems = view.findViewById(R.id.productAvailableItemsEditFrag)
        isVeg = view.findViewById(R.id.productIsVegEditFrag)
        productManufactureDate = view.findViewById(R.id.productManufactureEditFrag)
        productExpiryDate = view.findViewById(R.id.productExpiryEditFrag)
        imageLayout =view.findViewById(R.id.imageLayout)
        imageLoader = ImageLoaderAndGetter()


        setUpCategoryListeners()
        setUpTextFocusListeners()

        formatter = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        ProductListFragment.selectedProduct.value?.let {
            editingProduct = true
            addEditProductViewModel.getBrandName(it.brandId)
            imageHandler.gotImage.value = imageLoader.getImageInApp(requireContext(),it.mainImage)
            mainImage = it.mainImage
            updateBtn.text = "Update Product"
            view.findViewById<MaterialToolbar>(R.id.materialToolbarEditProductFrag).title = "Update Product"
            mainImageClicked = true
            addEditProductViewModel.getImagesForProduct(it.productId)
            productName.setText(it.productName)
            addEditProductViewModel.getParentCategoryImage(it.categoryName)
            productDescription.setText(it.productDescription)
            productPrice.setText(it.price.toString())
            productOffer.setText(it.offer.toString())
            productSubCat.setText(it.categoryName)
            productQuantity.setText(it.productQuantity)
            productAvailableItems.setText(it.availableItems.toString())
            isVeg.isChecked = it.isVeg
            rawExpiryDate = it.expiryDate
            rawManufactureDate = it.manufactureDate
            productManufactureDate.setText(DateGenerator.getDayAndMonth(it.manufactureDate))
            productExpiryDate.setText(DateGenerator.getDayAndMonth(it.expiryDate))
        }

        addEditProductViewModel.categoryImage.observe(viewLifecycleOwner){
            it?.let {
                if(it.isNotEmpty()) {
                    val image =imageLoader.getImageInApp(requireContext(), it)
                    addParentImage.setImageBitmap(image)
                    addParentCategoryButton.text = "Change Category Image"
                    if(image==null){
                        addParentImage.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.add_photo_alternate_24px))
                    }
                }
            }
        }

        addEditProductViewModel.imageList.observe(viewLifecycleOwner){
            if(editingProduct) {
                for (i in it) {
                    imageStringList.add(i.images)
                    fileName = i.images
                    imageHandler.gotImage.value =
                        imageLoader.getImageInApp(requireContext(), i.images)
                }
                editingProduct = false
                fileName = ""
            }
            else{
                for (i in it) {
                    imageHandler.gotImage.value =
                        imageLoader.getImageInApp(requireContext(), i.images)
                }
            }
        }

        addEditProductViewModel.brandName.observe(viewLifecycleOwner){
            brandName.setText(it)
        }

        addEditProductViewModel.getParentArray()
        addEditProductViewModel.parentArray.observe(viewLifecycleOwner){
            parentArray = it
            if(isNewParentCategory){
                for(i in it){
                    if(i == productParentCategory.text.toString()){
                        addEditProductViewModel.getParentCategoryImageForParent(i)
                        isNewParentCategory = false
                    }
                }
            }
            productParentCategory.setSimpleItems(parentArray)
        }
        ProductListFragment.selectedProduct.value?.let {
            addEditProductViewModel.getParentCategory(it.categoryName)
        }
        addEditProductViewModel.parentCategory.observe(viewLifecycleOwner){ parentCategoryValue ->
            productParentCategory.setText(parentCategoryValue)
        }


        addEditProductViewModel.childArray.observe(viewLifecycleOwner){ childItems->
            productSubCat.setSimpleItems(childItems)
            ProductListFragment.selectedProduct.value?.let {
                productSubCat.setText(it.categoryName)
            }
        }

        val dateManufacturePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select the Birthday Date")
            .setTextInputFormat(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        val dateExpiryPicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select the Birthday Date")
            .setTextInputFormat(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()))
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        dateManufacturePicker.addOnPositiveButtonClickListener {
            rawManufactureDate = formatter.format(it)
            productManufactureDate.setText(DateGenerator.getDayAndMonth(formatter.format(it)))
        }
        dateExpiryPicker.addOnPositiveButtonClickListener {
            rawExpiryDate = formatter.format(it)
            productExpiryDate.setText(DateGenerator.getDayAndMonth(formatter.format(it)))
        }

        setUpDatePickerListeners(dateManufacturePicker,dateExpiryPicker)
        setUpTextChangedListeners()
        setUpUpdateButtonListener()
        setUpAddNewImageListeners()
        setUpImageHandlerObserver(container)

        view.findViewById<MaterialToolbar>(R.id.materialToolbarEditProductFrag).setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return view
    }

    private fun setUpTextFocusListeners() {
        productName.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                productNameLayout.error = null
            }
        }
        brandName.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                brandNameLayout.error = null
            }
        }
        productParentCategory.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                parentCategoryLayout.error = null
            }
        }
        productSubCat.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                subCategoryLayout.error = null
            }
        }
        productDescription.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                productDescriptionLayout.error = null
            }
        }
        productPrice.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                priceLayout.error = null
            }
        }
        productOffer.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                offerLayout.error = null
            }
        }
        productQuantity.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                productQuantityLayout.error = null
            }
        }
        productAvailableItems.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                availableItemsLayout.error = null
            }
        }
        productManufactureDate.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    manufactureDateLayout.error = null
                }
            }

        })
        productExpiryDate.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()){
                    expiryDateLayout.error = null
                }
            }
        })
    }

    private fun setUpImageHandlerObserver(container: ViewGroup?) {
        imageHandler.gotImage.observe(viewLifecycleOwner){
            val newView = LayoutInflater.from(context).inflate(R.layout.image_view,container,false)
            val image = newView.findViewById<ImageView>(R.id.productImage)
            image.setImageBitmap(it)
            if(parentCategoryImageClicked){
                parentCategoryImage = it
                isCategoryImageAdded = true
                addParentImage.setImageBitmap(it)
                parentCategoryImageClicked = false
            }
            else {
                imageList.putIfAbsent(count,IntWithCheckedData(it,false,fileName))
                val currentCount = count
                if(mainImageClicked){
                    mainImageBitmap = it
                    newView.findViewById<CheckBox>(R.id.mainImageCheckBox).isChecked = mainImageClicked
                    imageList[currentCount] = IntWithCheckedData(it,true,fileName)
                    mainImageClicked = false
                }
                newView.findViewById<CheckBox>(R.id.mainImageCheckBox).setOnCheckedChangeListener { buttonView, isChecked ->
                    if(imageList[currentCount]!=null) {
                        imageList[currentCount]!!.isChecked = isChecked
                    }
                }
                newView.findViewById<ImageButton>(R.id.deleteImage).setOnClickListener {
                    if(imageLoader.deleteImageInApp(requireContext(),imageList[currentCount]?.fileName?:"")){
                        deletedImageList.add(imageList[currentCount]?.fileName?:"")
                    }
                    imageLayout.removeView(newView)
                    imageList.remove(currentCount)
                }
                imageLayout.addView(newView, 0)
                count++
            }
        }
    }

    private fun setUpAddNewImageListeners() {
        view.findViewById<ImageView>(R.id.addNewImage).setOnClickListener {
            imagePermissionHandler.checkPermission(true)
        }
        view.findViewById<MaterialButton>(R.id.addNewImageButton).setOnClickListener {
            imagePermissionHandler.checkPermission(true)
        }
    }

    private fun setUpUpdateButtonListener() {
        updateBtn.setOnClickListener {
            productNameLayout.error = inputChecker.nameCheck(productName)
            brandNameLayout.error = inputChecker.emptyCheck(brandName)
            parentCategoryLayout.error = inputChecker.lengthAndEmptyCheck("Parent Category",productParentCategory,3)
            subCategoryLayout.error = inputChecker.lengthAndEmptyCheck("Sub Category",productParentCategory,3)
            productDescriptionLayout.error = inputChecker.lengthAndEmptyCheck("Product Description",productDescription,30)
            priceLayout.error = inputChecker.emptyCheck(productPrice)
            offerLayout.error = inputChecker.emptyCheck(productOffer)
            productQuantityLayout.error = inputChecker.lengthAndEmptyCheck("Product Quantity",productQuantity,2)
            availableItemsLayout.error = inputChecker.emptyCheck(productAvailableItems)
            manufactureDateLayout.error = inputChecker.emptyCheck(productManufactureDate)
            expiryDateLayout.error = inputChecker.emptyCheck(productExpiryDate)
            if(productNameLayout.error == null &&
                brandNameLayout.error == null&&
                parentCategoryLayout.error == null&&
                subCategoryLayout.error == null&&
                productDescriptionLayout.error == null &&
                priceLayout.error == null&&
                offerLayout.error == null&&
                productQuantityLayout.error == null&&
                availableItemsLayout.error == null&&
                manufactureDateLayout.error == null&&
                expiryDateLayout.error == null
            ){
                var checkedCount = 0
                var bitmap:Bitmap? = null
                for(i in imageList){
                    if(i.value.isChecked){
                        checkedCount++
                        bitmap = i.value.bitmap
                        mainImageBitmap = bitmap
                    }
                }
                if(checkedCount>1){
                    Snackbar.make(view,"Product Should Contain Only One Main Image",Snackbar.LENGTH_SHORT).apply {
                        setBackgroundTint(Color.argb(255,230,20,20))
                        show()
                    }
                }
                else if(checkedCount <= 0){
                    Snackbar.make(view,"Product Should Contain atLeast One Main Image",Snackbar.LENGTH_SHORT).apply {
                        setBackgroundTint(Color.argb(255,230,20,20))
                        show()
                    }
                }
                else if(checkedCount==1) {
                    mainImage = "${System.currentTimeMillis()}"
                    imageLoader.storeImageInApp(requireContext(), bitmap!!, mainImage)
                }
                if(mainImage.isNotEmpty() && checkedCount ==1) {
                    val imageListNames = mutableListOf<String>()
                    for (i in imageList) {
                        if ((i.value.bitmap != mainImageBitmap) && (i.value.fileName !in imageStringList)) {
                            val tmpName = System.currentTimeMillis().toString()
                            imageLoader.storeImageInApp(requireContext(), i.value.bitmap, tmpName)
                            imageListNames.add(tmpName)
                        }
                    }
                    val brandNameStr = brandName.text.toString()
                    val subCategoryName = productSubCat.text.toString()
                    if (isNewParentCategory) {
                        val filName = "${System.currentTimeMillis()}"
                        if (parentCategoryImage != null) {
                            imageLoader.storeImageInApp(
                                requireContext(),
                                parentCategoryImage!!,
                                filName
                            )
                        }
                        if (imageLoader.getImageInApp(requireContext(), filName) == null) {
                            isCategoryImageAdded = false
                        }
                        addEditProductViewModel.addParentCategory(
                            ParentCategory(
                                productParentCategory.text.toString(),
                                filName,
                                "",
                                false
                            )
                        )
                    }
                    if (isNewSubCategory) {
                        addEditProductViewModel.addSubCategory(
                            Category(
                                productSubCat.text.toString(),
                                productParentCategory.text.toString(), ""
                            )
                        )
                    }
                    if (isCategoryImageAdded) {

                        addEditProductViewModel.updateInventory(
                            brandNameStr,
                            (ProductListFragment.selectedProduct.value == null),
                            Product(
                                0,
                                0,
                                subCategoryName,
                                productName.text.toString(),
                                productDescription.text.toString(),
                                productPrice.text.toString().toFloat(),
                                productOffer.text.toString().toFloat(),
                                productQuantity.text.toString(),
                                mainImage,
                                isVeg.isChecked,
                                rawManufactureDate,
                                rawExpiryDate,
                                productAvailableItems.text.toString().toInt()
                            ),
                            ProductListFragment.selectedProduct.value?.productId,
                            imageListNames,
                            deletedImageList
                        )
                        parentFragmentManager.popBackStack()
                        Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Please add the Category Image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(context,"All the Fields are mandatory",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpTextChangedListeners() {
        productParentCategory.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotEmpty()) {
                    addParentCategoryLayout.visibility = View.VISIBLE
                }
                else{
                    addParentCategoryLayout.visibility = View.GONE
                }
                if(!parentCategoryChecker(s.toString())){
                    isNewParentCategory = true
                    addParentImage.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.add_photo_alternate_24px))
                    addParentCategoryButton.text = "Add Category Image"
                }
                else{
                    addEditProductViewModel.getParentCategoryImageForParent(s.toString())
                    addEditProductViewModel.getChildArray(s.toString())
                    isNewParentCategory = false
                }
            }
        })

        productSubCat.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(!subCategoryChecker(s.toString())){
                    isNewSubCategory = true
                }
                else{
                    isNewSubCategory = false
                }
            }
        })
    }

    private fun setUpDatePickerListeners(
        dateManufacturePicker: MaterialDatePicker<Long>,
        dateExpiryPicker: MaterialDatePicker<Long>
    ) {
        productManufactureDate.setOnClickListener {
            dateManufacturePicker.show(parentFragmentManager,"Manufacture Date")
        }
        productExpiryDate.setOnClickListener {
            dateExpiryPicker.show(parentFragmentManager,"Expiry Date")
        }
    }

    private fun setUpCategoryListeners() {
        addParentCategoryButton.setOnClickListener {
            isNewParentCategory = true
            parentCategoryImageClicked = true
            imagePermissionHandler.checkPermission(false)
        }
        addParentImage.setOnClickListener {
            isNewParentCategory = true
            parentCategoryImageClicked = true
            imagePermissionHandler.checkPermission(false)
        }
    }

    private fun setUpListeners() {

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

    fun parentCategoryChecker(parentCategory: String):Boolean{
        for(i in parentArray){
            if(parentCategory==i){
                return true
            }
        }
        return false
    }

    fun subCategoryChecker(childCategory: String):Boolean{
        for(i in childArray){
            if(childCategory==i){
                return true
            }
        }
        return false
    }
}