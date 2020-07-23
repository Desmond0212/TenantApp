package com.example.tenantyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.btnLogout
import kotlinx.android.synthetic.main.activity_profile.cardViewFive
import kotlinx.android.synthetic.main.activity_profile.cardViewFour
import kotlinx.android.synthetic.main.activity_profile.cardViewOne
import kotlinx.android.synthetic.main.activity_profile.cardViewThree
import kotlinx.android.synthetic.main.activity_profile.cardViewTwo
import kotlinx.android.synthetic.main.activity_profile.imgMoreFive
import kotlinx.android.synthetic.main.activity_profile.imgMoreFour
import kotlinx.android.synthetic.main.activity_profile.imgMoreOne
import kotlinx.android.synthetic.main.activity_profile.imgMoreThree
import kotlinx.android.synthetic.main.activity_profile.imgMoreTwo
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    private var spinner : Spinner? = null
    private var unitStatus = arrayOf("Unit rented out", "Unit available")
    private var txtUnitNumber: EditText? = null
    private var txtUnitAddress: EditText? = null
    private var btnSaveUnitDetails: Button? = null
    private var btnDeleteUnitDetails: Button? = null
    private var lblAvailableUnit: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        imgButtonBackProfile.setOnClickListener {
            finish()
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        imgMoreOne.setOnClickListener {
            setBottomDialogAction(0)
        }

        imgMoreTwo.setOnClickListener {
            setBottomDialogAction(1)
        }

        imgMoreThree.setOnClickListener {
            setBottomDialogAction(2)
        }

        imgMoreFour.setOnClickListener {
            setBottomDialogAction(3)
        }

        imgMoreFive.setOnClickListener {
            setBottomDialogAction(4)
        }
    }

    private fun setBottomDialogAction(itemPosition: Int)
    {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_unit_details, findViewById<View>(R.id.bottomSheetContainerUnitDetails) as? LinearLayout)

        btnSaveUnitDetails = bottomSheetView.findViewById(R.id.btnDSaveUnit)
        btnDeleteUnitDetails = bottomSheetView.findViewById(R.id.btnDeleteUnit)
        txtUnitNumber = bottomSheetView.findViewById(R.id.lblUnitNumUnitDetails)
        txtUnitAddress = bottomSheetView.findViewById(R.id.lblUnitAddressUnitDetails)
        lblAvailableUnit = bottomSheetView.findViewById(R.id.lblAvailableRooms)

        btnSaveUnitDetails?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        when (itemPosition)
        {
            0 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(0)
                    Toast.makeText(this, "Unit A-13-14 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("A-13-14")
                txtUnitAddress?.setText("A-13-14 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
                lblAvailableUnit?.text = "No rooms available"
            }

            1 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()

                    hideCardViewTemporary(1)
                    Toast.makeText(this, "Unit A-20-28 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("A-20-28")
                txtUnitAddress?.setText("A-20-28 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
                lblAvailableUnit?.text = "No rooms available"
            }

            2 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(2)
                    Toast.makeText(this, "Unit B-16-17 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("B-16-17")
                txtUnitAddress?.setText("B-16-17 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
                lblAvailableUnit?.text = "No rooms available"
            }

            3 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(3)
                    Toast.makeText(this, "UnitB-33-40 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("B-33-40")
                txtUnitAddress?.setText("B-33-40 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
                lblAvailableUnit?.text = "3 rooms available"
            }

            4 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(4)
                    Toast.makeText(this, "Unit C-01-27 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("C-01-27")
                txtUnitAddress?.setText("C-01-27 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
                lblAvailableUnit?.text = "5 rooms available"
            }
        }

        spinner = bottomSheetView.findViewById(R.id.spinnerUnitStatus)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this?.applicationContext!!, android.R.layout.simple_spinner_item, unitStatus)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }

    private fun hideCardViewTemporary(unitNum: Int)
    {
        when (unitNum)
        {
            0 ->
            {
                cardViewOne.visibility = View.GONE
            }

            1 ->
            {
                cardViewTwo.visibility = View.GONE
            }

            2 ->
            {
                cardViewThree.visibility = View.GONE
            }

            3 ->
            {
                cardViewFour.visibility = View.GONE
            }

            4 ->
            {
                cardViewFive.visibility = View.GONE
            }
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {}

    override fun onNothingSelected(arg0: AdapterView<*>) {}
}