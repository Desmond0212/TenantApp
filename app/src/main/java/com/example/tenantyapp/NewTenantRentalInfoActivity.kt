package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_new_tenant_information.*
import kotlinx.android.synthetic.main.activity_new_tenant_rental_info.*

class NewTenantRentalInfoActivity : AppCompatActivity()
{
    private var btnDoneAddAccount: Button? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant_rental_info)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        layoutNewTenantRentalInfo.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this)
            txtStartDate.clearFocus()
            txtEndDate.clearFocus()
            txtRentalPrice.clearFocus()
            txtSecurityDeposit.clearFocus()
            txtDayPaymentDue.clearFocus()
            txtLateCharges.clearFocus()
            BillsPeriod.clearFocus()

            false
        }

        btnBackNewTenantRentalInfo.setOnClickListener {
            finish()
        }

        btnNextNewTenantRentalInfo.setOnClickListener {
            val intent = Intent(this, NewTenantCreatingActivity::class.java)
            startActivity(intent)
        }

        btnCloseNewTenantRentalInfo.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        btnAddNewAccount.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(R.layout.bottom_sheet_new_account, findViewById<View>(R.id.bottomSheetContainerNewAccount) as? LinearLayout)

            btnDoneAddAccount = bottomSheetView.findViewById(R.id.btnDoneNewAccount)

            btnDoneAddAccount?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
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