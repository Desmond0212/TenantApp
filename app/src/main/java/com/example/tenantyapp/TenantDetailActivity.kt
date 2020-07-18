package com.example.tenantyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_tenant_detail.*

class TenantDetailActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenant_detail)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        getExtraString()

        imgBackButton.setOnClickListener {
            finish()
        }

        imgButtonCopy.setOnClickListener {
            Toast.makeText(this, "Link Copied...", Toast.LENGTH_SHORT).show()
        }

        layoutCopyButton.setOnClickListener {
            Toast.makeText(this, "Link Copied...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getExtraString()
    {
        val username: String? = intent.getStringExtra("Username")
        val contactDate: String? = intent.getStringExtra("ContactDate")
        val dayLeft: String? = intent.getStringExtra("DayLeft")
        val rentalPrice: String? = intent.getStringExtra("RentalPrice")
        val billsStatus: String? = intent.getStringExtra("BillsStatus")
        val startDate: String? = intent.getStringExtra("StartDate")
        val endDate: String? = intent.getStringExtra("EndDate")
        val email: String? = intent.getStringExtra("Email")
        val phoneNumber: String? = intent.getStringExtra("PhoneNumber")
        val totalContactAmount: String? = intent.getStringExtra("TotalContactAmount")
        val paymentDue: String? = intent.getStringExtra("PaymentDue")
        val lateCharges: String? = intent.getStringExtra("LateCharges")
        val billsPeriod: String? = intent.getStringExtra("BillsPeriod")
        val accountReceive: String? = intent.getStringExtra("AccountReceive")

        lblUsername.text = username
        lblContact.text = contactDate
        lblStartDateValue.text = startDate
        lblEmailTitleValue.text = email
        lblEndDateValue.text = endDate
        lblPhoneNumberValue.text = phoneNumber
        lblRentalPriceValue.text = rentalPrice
        lblAfterPaymentDueValue.text = paymentDue
        lblBillPeriodValue.text = billsPeriod
        lblReceiveAccountValue.text = accountReceive
        lblContractAmountTitleValue.text = totalContactAmount
        lblLateChangesValue.text = lateCharges
    }
}