package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_new_tenant_information.*

class NewTenantInformationActivity : AppCompatActivity()
{
    private var selectedUnit: String? = null
    private var unitAddress: String? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant_information)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        getExtraStringValue()

        layoutNewTenantInformation.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this)
            txtFullNameNewTenantInfo.clearFocus()
            txtEmailNewTenantInfo.clearFocus()
            txtPhoneNewTenant.clearFocus()
            txtNumOfOccupant.clearFocus()
            false
        }

        btnCloseNewTenantInfo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnBackNewTenantInfo.setOnClickListener {
            finish()
        }

        btnNextNewTenantInfo.setOnClickListener {
            if (txtFullNameNewTenantInfo.text != null && txtFullNameNewTenantInfo.text.toString() != "" &&
                txtEmailNewTenantInfo.text != null && txtEmailNewTenantInfo.text.toString() != "" &&
                txtPhoneNewTenant.text != null && txtPhoneNewTenant.text.toString() != "" &&
                txtNumOfOccupant.text != null && txtPhoneNewTenant.text.toString() != "")
            {
                val intent = Intent(this, NewTenantRentalInfoActivity::class.java)
                intent.putExtra("SelectedUnit", selectedUnit)
                intent.putExtra("UnitAddress", unitAddress)
                intent.putExtra("FullName", txtFullNameNewTenantInfo.text.toString())
                intent.putExtra("EmailAddress", txtEmailNewTenantInfo.text.toString())
                intent.putExtra("PhoneNumber", txtPhoneNewTenant.text.toString())
                intent.putExtra("NumberOfOccupant", txtNumOfOccupant.text.toString())
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "Please complete the form before proceed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getExtraStringValue()
    {
        selectedUnit = intent.getStringExtra("SelectedUnit")
        unitAddress = intent.getStringExtra("UnitAddress")
    }

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