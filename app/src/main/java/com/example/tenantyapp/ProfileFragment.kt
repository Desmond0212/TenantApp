package com.example.tenantyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_new_tenant.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var spinner : Spinner? = null
    private var unitStatus = arrayOf("Unit rented out", "Unit available")
    private var txtUnitNumber: EditText? = null
    private var txtUnitAddress: EditText? = null
    private var btnSaveUnitDetails: Button? = null
    private var btnDeleteUnitDetails: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.btnLogout.setOnClickListener {
            activity?.onBackPressed()
        }

        view.imgMoreOne.setOnClickListener {
            setBottomDialogAction(0)
        }

        view.imgMoreTwo.setOnClickListener {
            setBottomDialogAction(1)
        }

        view.imgMoreThree.setOnClickListener {
            setBottomDialogAction(2)
        }

        view.imgMoreFour.setOnClickListener {
            setBottomDialogAction(3)
        }

        view.imgMoreFive.setOnClickListener {
            setBottomDialogAction(4)
        }

        return view
    }

    private fun setBottomDialogAction(itemPosition: Int)
    {
        val bottomSheetDialog = BottomSheetDialog(context!!, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(activity).inflate(R.layout.bottom_sheet_unit_details, view?.findViewById<View>(R.id.bottomSheetContainerUnitDetails) as? LinearLayout)

        btnSaveUnitDetails = bottomSheetView.findViewById(R.id.btnDSaveUnit)
        btnDeleteUnitDetails = bottomSheetView.findViewById(R.id.btnDeleteUnit)
        txtUnitNumber = bottomSheetView.findViewById(R.id.lblUnitNumUnitDetails)
        txtUnitAddress = bottomSheetView.findViewById(R.id.lblUnitAddressUnitDetails)

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
                    Toast.makeText(activity, "Unit A-13-14 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("A-13-14")
                txtUnitAddress?.setText("A-13-14 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
            }

            1 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()

                    hideCardViewTemporary(1)
                    Toast.makeText(activity, "Unit A-20-28 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("A-20-28")
                txtUnitAddress?.setText("A-20-28 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
            }

            2 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(2)
                    Toast.makeText(activity, "Unit B-16-17 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("B-16-17")
                txtUnitAddress?.setText("B-16-17 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
            }

            3 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(3)
                    Toast.makeText(activity, "UnitB-33-40 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("B-33-40")
                txtUnitAddress?.setText("B-33-40 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
            }

            4 ->
            {
                btnDeleteUnitDetails?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                    hideCardViewTemporary(4)
                    Toast.makeText(activity, "Unit C-01-27 successfully deleted", Toast.LENGTH_SHORT).show()
                }

                txtUnitNumber?.setText("C-01-27")
                txtUnitAddress?.setText("C-01-27 Pelangi Indah Condo, Batu 4 1/2 Jalan Ipoh, 51200 Kuala Lumpur")
            }
        }

        spinner = bottomSheetView.findViewById(R.id.spinnerUnitStatus)
        spinner!!.onItemSelectedListener = this
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(activity?.applicationContext!!, android.R.layout.simple_spinner_item, unitStatus)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}