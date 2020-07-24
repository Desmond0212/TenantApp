package com.example.tenantyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_bill_details.*
import kotlinx.android.synthetic.main.activity_defect_detail.*

class BillDetailsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_details)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        imgBackBillDetail.setOnClickListener {
            finish()
        }

        imgDownload.setOnClickListener {
            Toast.makeText(this, "Your bill is downloading...", Toast.LENGTH_SHORT).show()
        }

        imgMoreOptionBillDetail.setOnClickListener {
            //Creating the instance of PopupMenu
            //Creating the instance of PopupMenu
            val popup = PopupMenu(this, imgMoreOptionBillDetail)
            //Inflating the Popup using xml file
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.top_bar_billing_menu, popup.getMenu())

            //registering popup with OnMenuItemClickListener
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    Toast.makeText(applicationContext, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show()
                    return true
                }
            })

            popup.show() //showing popup menu
        }

        val billNumber = intent.getStringExtra("BillNumber")
        val username = intent.getStringExtra("Username")
        val billingStatus = intent.getStringExtra("BillingStatus")
        val billDate = intent.getStringExtra("BillDate")
        val dueDate = intent.getStringExtra("DueDate")
        val rentalAmount = intent.getStringExtra("RentalAmount")
        val receiptNumber = intent.getStringExtra("ReceiptNumber")
        val paymentDate = intent.getStringExtra("PaymentDate")
        val paymentNumber = intent.getIntExtra("PaymentNumber", 0)

        lblBillNumber.text = billNumber
        lblUsernameBillDetail.text = username
        lblBillDetailStatus.text = billingStatus
        lblBillDetailDateValue.text = billDate
        lblBillDetailDueDateValue.text = dueDate
        lblRentalAmountValue.text = rentalAmount
        lblReceiptNumber.text = receiptNumber
        lblPaymentDateValue.text = paymentDate

        if (billingStatus == "Paid")
        {
            lblBillDetailStatus.setTextColor(resources.getColor(R.color.colorAccent))
        }
        else
        {
            lblBillDetailStatus.setTextColor(resources.getColor(R.color.colorRed))
        }

        when(paymentNumber)
        {
            0 ->
            {
                imgReceipt.setImageResource(R.drawable.bank_receipt1)
            }

            1 ->
            {
                imgReceipt.setImageResource(R.drawable.bank_receipt6)
            }

            2 ->
            {
                imgReceipt.setImageResource(R.drawable.bank_receipt4)
            }

            3 ->
            {
                imgReceipt.setImageResource(R.drawable.bank_receipt3)
            }

            4 ->
            {
                imgReceipt.setImageResource(R.drawable.bank_receipt5)
            }
        }
    }
}