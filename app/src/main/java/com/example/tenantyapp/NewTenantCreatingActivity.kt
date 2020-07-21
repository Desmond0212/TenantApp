package com.example.tenantyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_new_tenant_creating.*
import kotlinx.android.synthetic.main.activity_new_tenant_information.*
import kotlinx.android.synthetic.main.activity_new_tenant_rental_info.*

class NewTenantCreatingActivity : AppCompatActivity()
{
    private var selectedUnit: String? = null
    private var unitAddress: String? = null
    private var fullName: String? = null
    private var emailAddress: String? = null
    private var phoneNumber: String? = null
    private var numberOfOccupant: String? = null
    private var startDate: String? = null
    private var endDate: String? = null
    private var rentalPrice: String? = null
    private var securityDeposit: String? = null
    private var dayPaymentDue: String? = null
    private var lateCharges: String? = null
    private var billsPeriod: String? = null
    private var bankName: String? = null
    private var contractPeriod: String? = null
    private var accountNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tenant_creating)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        getExtraStringValue()

        animCreatingNewTenant.setAnimation("animation_1.json")
        animCreatingNewTenant.playAnimation()
        animCreatingNewTenant.loop(true)

        val signInIntent = Intent(this, TenantDetailActivity::class.java)

        val finalRentalPrice: String? = "RM$rentalPrice.00"
        val prefixedPhoneNumber: String? = "+6$phoneNumber"
        val finalSecurityDeposit: String? = "RM$securityDeposit.00"
        val finalDueDay: String? = "$dayPaymentDue days"
        val finalLateCharges: String? = "RM$lateCharges.00"
        val finalBillsPeriod: String? = "Every $billsPeriod of the month"

        signInIntent.putExtra("SelectedUnit", selectedUnit)
        signInIntent.putExtra("UnitAddress", unitAddress)
        signInIntent.putExtra("Username", fullName)
        signInIntent.putExtra("Email", emailAddress)
        signInIntent.putExtra("PhoneNumber", prefixedPhoneNumber)
        signInIntent.putExtra("NumberOfOccupant", numberOfOccupant)
        signInIntent.putExtra("StartDate", startDate)
        signInIntent.putExtra("EndDate", endDate)
        signInIntent.putExtra("RentalPrice", finalRentalPrice)
        signInIntent.putExtra("SecurityDeposit", finalSecurityDeposit)
        signInIntent.putExtra("PaymentDue", finalDueDay)
        signInIntent.putExtra("LateCharges", finalLateCharges)
        signInIntent.putExtra("BillsPeriod", billsPeriod)
        signInIntent.putExtra("AccountReceive", bankName)
        signInIntent.putExtra("ContactDate", contractPeriod)
        signInIntent.putExtra("TotalContactAmount", "RM 12,000.00")
        signInIntent.putExtra("BillsPeriod", finalBillsPeriod)
        signInIntent.putExtra("isFromNewTenant", true)
        signInIntent.putExtra("BankAccountNumber", accountNumber)


        val timer = object : Thread()
        {
            override fun run()
            {
                try
                {
                    sleep(8000)
                } catch (e: InterruptedException)
                {
                    e.printStackTrace()
                } finally
                {
                    startActivity(signInIntent)
                    finish()
                }
            }
        }
        timer.start()
    }

    private fun getExtraStringValue()
    {
        selectedUnit = intent.getStringExtra("SelectedUnit")
        unitAddress = intent.getStringExtra("UnitAddress")
        fullName = intent.getStringExtra("FullName")
        emailAddress = intent.getStringExtra("EmailAddress")
        phoneNumber = intent.getStringExtra("PhoneNumber")
        startDate = intent.getStringExtra("StartDate")
        endDate = intent.getStringExtra("EndDate")
        rentalPrice = intent.getStringExtra("RentalPrice")
        securityDeposit = intent.getStringExtra("SecurityDeposit")
        dayPaymentDue = intent.getStringExtra("DayPaymentDue")
        lateCharges = intent.getStringExtra("LateCharges")
        billsPeriod = intent.getStringExtra("BillsPeriod")
        bankName = intent.getStringExtra("BankName")
        contractPeriod = intent.getStringExtra("ContractPeriod")
        accountNumber = intent.getStringExtra("BankAccountNumber")
    }
}