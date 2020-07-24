package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BillFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bill, container, false)

        val adapter = ViewPagerAdapterBills(childFragmentManager)
        adapter.addFragment(BilllsTopbarFragment(), "FragmentBills1")
        adapter.addFragment(BilllsTopbarFragment(), "FragmentBills2")
        view.view_pager_bills?.adapter = adapter
        view.dot3_bills?.setViewPager(view.view_pager_bills)

        view.view_pager_bills.setOnPageChangeListener(object: ViewPager.OnPageChangeListener
        {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 1)
                {
                    //lblUnitTitle.text = "A-20-23"
                }
                else
                {

                    //lblUnitTitle.text = "A-13-14"
                }
            }

        })

        view.fragmentBillLayout.setOnTouchListener { _, _ ->
            hideSoftKeyboard(activity)
            false
        }

        view.layoutConstraintContainer.setOnTouchListener { _, _ ->
            hideSoftKeyboard(activity)
            false
        }

        view.imgNotificationBills.setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }

        view.imgMenuBills.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        view.billsCardView1.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0033")
            intent.putExtra("Username", "Chow Jun Wei")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "July 02, 2020")
            intent.putExtra("DueDate", "July 16, 2020")
            intent.putExtra("RentalAmount", "RM 1,200.00")
            intent.putExtra("ReceiptNumber", "RECEIPT = REC0001")
            intent.putExtra("PaymentDate", "July 11, 2020")
            intent.putExtra("PaymentNumber", 0)
            startActivity(intent)
        }

        view.billsCardView2.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0032")
            intent.putExtra("Username", "Darren Chong")
            intent.putExtra("BillingStatus", "Overdue")
            intent.putExtra("BillDate", "June 18, 2020")
            intent.putExtra("DueDate", "July 02, 2020")
            intent.putExtra("RentalAmount", "RM 1,500.00")
            intent.putExtra("ReceiptNumber", "RECEIPT = REC0019")
            intent.putExtra("PaymentDate", "July 18, 2020")
            intent.putExtra("PaymentNumber", 1)
            startActivity(intent)
        }

        view.billsCardView3.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0031")
            intent.putExtra("Username", "Desmond Wong")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "July 01, 2020")
            intent.putExtra("DueDate", "July 15, 2020")
            intent.putExtra("RentalAmount", "RM 700.00")
            intent.putExtra("ReceiptNumber", "RECEIPT = REC0023")
            intent.putExtra("PaymentDate", "July 03, 2020")
            intent.putExtra("PaymentNumber", 2)
            startActivity(intent)
        }

        view.billsCardView4.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0030")
            intent.putExtra("Username", "Jaslyn Shu")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "May 25, 2020")
            intent.putExtra("DueDate", "June 18, 2020")
            intent.putExtra("RentalAmount", "RM 1,200.00")
            intent.putExtra("ReceiptNumber", "RECEIPT = REC0088")
            intent.putExtra("PaymentDate", "June 16, 2020")
            intent.putExtra("PaymentNumber", 3)
            startActivity(intent)
        }

        view.billsCardView5.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0029")
            intent.putExtra("Username", "Crystal So")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "June 08, 2020")
            intent.putExtra("DueDate", "June 24, 2020")
            intent.putExtra("RentalAmount", "RM 1,700.00")
            intent.putExtra("ReceiptNumber", "RECEIPT = REC0005")
            intent.putExtra("PaymentDate", "June 10, 2020")
            intent.putExtra("PaymentNumber", 4)
            startActivity(intent)
        }

        return view
    }

    private fun hideSoftKeyboard(activity: FragmentActivity?)
    {
        val inputMethodManager: InputMethodManager = activity?.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    inner class ViewPagerAdapterBills(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
    {
        val fragments: MutableList<Fragment> = ArrayList()
        val titles: MutableList<String> = ArrayList()

        fun addFragment(fragment: Fragment, title:String)
        {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getItem(p0: Int): Fragment = fragments[p0]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}