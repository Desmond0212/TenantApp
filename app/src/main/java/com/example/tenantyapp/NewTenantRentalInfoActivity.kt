package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_new_tenant_rental_info.*
import java.text.SimpleDateFormat
import java.util.*


class NewTenantRentalInfoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private var btnDoneAddAccount: Button? = null
    private var selectedUnit: String? = null
    private var unitAddress: String? = null
    private var fullName: String? = null
    private var emailAddress: String? = null
    private var phoneNumber: String? = null
    private var numberOfOccupant: String? = null
    private var spinner : Spinner? = null
    private var contractPeriod = arrayOf("1 month contract", "2 months contract", "3 months contract", "4 months contract", "5 months contract", "6 months contract", "7 months contract", "8 months contract", "9 months contract", "10 months contract", "11 months contract", "1 year contract", "2 years contract", "3 years contract")
    private var contractPeriodValue: String? = null
    private val myCalendar: Calendar = Calendar.getInstance()

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

        getExtraStringValue()

        /*spinner = findViewById(R.id.spinnerTenantRentalInfo)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, contractPeriod)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        spinner?.setOnTouchListener {_, _ ->
            hideSoftKeyboard(this)
            txtStartDate.clearFocus()
            txtEndDate.clearFocus()
            txtRentalPrice.clearFocus()
            txtSecurityDeposit.clearFocus()
            txtDayPaymentDue.clearFocus()
            txtLateCharges.clearFocus()
            txtBillsPeriod.clearFocus()

            false
        }*/

        layoutNewTenantRentalInfo.setOnTouchListener { _, _ ->
            hideSoftKeyboard(this)
            txtStartDate.clearFocus()
            txtEndDate.clearFocus()
            txtRentalPrice.clearFocus()
            txtSecurityDeposit.clearFocus()
            txtDayPaymentDue.clearFocus()
            txtLateCharges.clearFocus()
            txtBillsPeriod.clearFocus()

            false
        }

        btnBackNewTenantRentalInfo.setOnClickListener {
            finish()
        }

        btnNextNewTenantRentalInfo.setOnClickListener {
            if (txtStartDate.text != null && txtStartDate.text.toString() != "" &&
                txtEndDate.text != null && txtEndDate.text.toString() != "" &&
                txtRentalPrice.text != null && txtRentalPrice.text.toString() != "" &&
                txtSecurityDeposit.text != null && txtSecurityDeposit.text.toString() != "" &&
                txtDayPaymentDue.text != null && txtDayPaymentDue.text.toString() != "" &&
                txtLateCharges.text != null && txtLateCharges.text.toString() != "" &&
                txtBillsPeriod.text != null && txtStartDate.text.toString() != "" &&
                lblBankName.text != null && lblBankName.text.toString() != "" &&
                lblAccountNumber.text != null && lblAccountNumber.text.toString() != "")
            {
                val intent = Intent(this, NewTenantCreatingActivity::class.java)
                intent.putExtra("SelectedUnit", selectedUnit)
                intent.putExtra("UnitAddress", unitAddress)
                intent.putExtra("FullName", fullName)
                intent.putExtra("EmailAddress", emailAddress)
                intent.putExtra("PhoneNumber", phoneNumber)
                intent.putExtra("NumberOfOccupant", numberOfOccupant)
                intent.putExtra("StartDate", txtStartDate.text.toString())
                intent.putExtra("EndDate", txtEndDate.text.toString())
                intent.putExtra("RentalPrice", txtRentalPrice.text.toString())
                intent.putExtra("SecurityDeposit", txtSecurityDeposit.text.toString())
                intent.putExtra("DayPaymentDue", txtDayPaymentDue.text.toString())
                intent.putExtra("LateCharges", txtLateCharges.text.toString())
                intent.putExtra("BillsPeriod", txtBillsPeriod.text.toString())
                intent.putExtra("BankName", lblBankName.text.toString())
                intent.putExtra("ContractPeriod", "2 year contract")
                intent.putExtra("BankAccountNumber", lblAccountNumber.text.toString())
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this, "Please complete the form before proceed.", Toast.LENGTH_SHORT).show()
            }
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

        val date = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }

        val date2 = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel2()
        }

        txtStartDate.setOnClickListener {
            DatePickerDialog(this, date, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show()
        }

        txtEndDate.setOnClickListener {
            DatePickerDialog(this, date2, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH]).show()
        }
    }

    private fun updateLabel()
    {
        val myFormat = "dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtStartDate.setText(sdf.format(myCalendar.time))
    }

    private fun updateLabel2()
    {
        val myFormat = "dd MMM yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtEndDate.setText(sdf.format(myCalendar.time))
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        contractPeriodValue = contractPeriod[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {}

    private fun getExtraStringValue()
    {
        selectedUnit = intent.getStringExtra("SelectedUnit")
        unitAddress = intent.getStringExtra("UnitAddress")
        fullName = intent.getStringExtra("FullName")
        emailAddress = intent.getStringExtra("EmailAddress")
        phoneNumber = intent.getStringExtra("PhoneNumber")
        numberOfOccupant = intent.getStringExtra("NumberOfOccupant")
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