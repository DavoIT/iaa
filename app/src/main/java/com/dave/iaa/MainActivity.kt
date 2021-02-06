package com.dave.iaa

import android.os.Bundle
import android.os.Handler
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val agesListResIds = arrayOf(
        R.string.age1,
        R.string.age2,
        R.string.age3,
        R.string.age4
    )
    private val fuelTypesListResIds = arrayOf(
        R.string.fuel1,
        R.string.fuel2,
        R.string.fuel3
    )

    private val vehicleTypesListResIds = arrayOf(
        R.string.vehicle1,
        R.string.vehicle2,
        R.string.vehicle3,
        R.string.vehicle4
    )

    private lateinit var locationSpinner: Spinner
    private lateinit var ageSpinner: Spinner
    private lateinit var fuelTypeSpinner: Spinner
    private lateinit var vehicleTypeSpinner: Spinner

    private lateinit var auctionLayout: ViewGroup
    private lateinit var auctionIAAButton: View
    private lateinit var auctionCopartButton: View
    private lateinit var auctionOtherButton: View
    private lateinit var auctionOtherEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setListeners()
    }

    private fun init() {
        locationSpinner = findViewById(R.id.locationSpinner)
        ageSpinner = findViewById(R.id.ageSpinner)
        fuelTypeSpinner = findViewById(R.id.fuelSpinner)
        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner)

        auctionLayout = findViewById(R.id.auctionLayout)
        auctionIAAButton = findViewById(R.id.auctionIAAButton)
        auctionCopartButton = findViewById(R.id.auctionCopartButton)
        auctionOtherButton = findViewById(R.id.auctionOtherButton)
        auctionOtherEditText = findViewById(R.id.auctionOtherEditText)

        makeBackgroundBlurred()
        setupLocationSpinner()
        setupSpinnerUsingResIds(ageSpinner, agesListResIds)
        setupSpinnerUsingResIds(fuelTypeSpinner, fuelTypesListResIds)
        setupSpinnerUsingResIds(vehicleTypeSpinner, vehicleTypesListResIds)
    }

    private fun setListeners() {
        auctionIAAButton.setOnClickListener { pressedAuction(0) }
        auctionCopartButton.setOnClickListener { pressedAuction(1) }
        auctionOtherButton.setOnClickListener { pressedAuction(2) }
    }

    private fun setupLocationSpinner() {
        val items = resources.getStringArray(R.array.locations)
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item, items)
        adapter.setDropDownViewResource(R.layout.simple_dropdown_item)
        locationSpinner.adapter = adapter
    }

    private fun setupSpinnerUsingResIds(spinner: Spinner, resIds: Array<Int>) {
        val stringsList: ArrayList<String> = arrayListOf()
        resIds.forEach {
            stringsList.add(getString(it))
        }
        val adapter = ArrayAdapter(this, R.layout.simple_dropdown_item, stringsList)
        spinner.adapter = adapter
    }

    private fun makeBackgroundBlurred() {
        val backImage: ImageView = findViewById(R.id.background)
        Glide.with(this)
            .asBitmap()
            .load(R.drawable.background)
            .override(100)
            .apply(RequestOptions.centerCropTransform())
            .into(backImage)
    }

    private fun pressedAuction(index: Int) {
        if (index == 2) {
            auctionOtherEditText.visibility =
                if (auctionOtherEditText.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            ActivityUtils.showKeyBoardFor(auctionOtherEditText, this)
        } else {
            auctionOtherEditText.visibility = View.GONE
            ActivityUtils.hideKeyboard(this)
        }
        TransitionManager.beginDelayedTransition(auctionLayout)
        val selectedBackground =
            ContextCompat.getDrawable(baseContext, R.drawable.selected_background)

        auctionIAAButton.background = if (index == 0) selectedBackground else null
        auctionCopartButton.background = if (index == 1) selectedBackground else null
        auctionOtherButton.background = if (index == 2) selectedBackground else null

        val iaaLP = auctionIAAButton.layoutParams as ConstraintLayout.LayoutParams
        val copartLP = auctionCopartButton.layoutParams as ConstraintLayout.LayoutParams

        iaaLP.matchConstraintPercentWidth = if (index == 2) 0.2f else 0.3f
        copartLP.matchConstraintPercentWidth = if (index == 2) 0.2f else 0.3f

        auctionIAAButton.layoutParams = iaaLP
        auctionCopartButton.layoutParams = copartLP
    }
}