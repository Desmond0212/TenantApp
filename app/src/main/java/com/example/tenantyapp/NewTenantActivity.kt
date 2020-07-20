package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_new_tenant.*

class NewTenantActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private var spinner : Spinner? = null
    private var units = arrayOf("A-13-14", "A-20-22", "B-10-29")

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        spinner = findViewById(R.id.spinnerNewTenant)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        layoutNewTenant.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this)
            txtUnitAddress.clearFocus()
            false
        }

        btnBackNewTenant.setOnClickListener {
            finish()
        }

        btnNextNewTenantOne.setOnClickListener {
            val intent = Intent(this, NewTenantInformationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        //textView_msg!!.text = "Selected : "+languages[position]
        Toast.makeText(this, "Unit Selected: " + units[position], Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {}

    private fun hideSoftKeyboard(activity: Activity)
    {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }
}