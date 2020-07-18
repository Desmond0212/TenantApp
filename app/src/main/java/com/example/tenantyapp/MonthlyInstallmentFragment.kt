package com.example.tenantyapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlinx.android.synthetic.main.fragment_home_topbar.*
import kotlinx.android.synthetic.main.fragment_monthly_installment.*
import kotlinx.android.synthetic.main.fragment_monthly_installment.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonthlyInstallmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MonthlyInstallmentFragment : Fragment()
{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var cardViewOne: CardView? = null
    private var cardViewTwo: CardView? = null
    private var cardViewThree: CardView? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_monthly_installment, container, false)

        val adapter = ViewPagerAdapterTenants(childFragmentManager)
        adapter.addFragment(TenantsTopbarFragment(), "FragmentTenants1")
        adapter.addFragment(TenantsTopbarFragment(), "FragmentTenants2")
        view.view_pager_tenants?.adapter = adapter
        view.dot3_tenants?.setViewPager(view.view_pager_tenants)

        cardViewOne = view.findViewById(R.id.billsCardView1)
        cardViewTwo = view.findViewById(R.id.billsCardView2)
        cardViewThree = view.findViewById(R.id.billsCardView3)

        cardViewOne?.setOnClickListener {
            val intent = Intent(activity, TenantDetailActivity::class.java)
            intent.putExtra("Username", "Darren Chong")
            intent.putExtra("ContactDate", "2 years contact")
            intent.putExtra("DayLeft", "320 days left")
            intent.putExtra("RentalPrice", "RM 1200.00 / month")
            intent.putExtra("BillsStatus", "Overdue")
            intent.putExtra("StartDate", "27 July 2018")
            intent.putExtra("EndDate", "28 July 2020")
            intent.putExtra("Email", "Darren@gmail.com")
            intent.putExtra("PhoneNumber", "+6018 2233987")
            intent.putExtra("TotalContactAmount", "RM 28,800.00")
            intent.putExtra("PaymentDue", "5 days")
            intent.putExtra("LateCharges", "RM 500.00")
            intent.putExtra("BillsPeriod", "Every 27th of the month")
            intent.putExtra("AccountReceive", "Maybank")

            startActivity(intent)
        }

        cardViewTwo?.setOnClickListener {
            val intent = Intent(activity, TenantDetailActivity::class.java)
            intent.putExtra("Username", "Jun Wei Chow")
            intent.putExtra("ContactDate", "3 months contact")
            intent.putExtra("DayLeft", "60 days left")
            intent.putExtra("RentalPrice", "RM 1500.00 / month")
            intent.putExtra("BillsStatus", "Paid")
            intent.putExtra("StartDate", "05 May 2020")
            intent.putExtra("EndDate", "06 August 2020")
            intent.putExtra("Email", "JunWei@gmail.com")
            intent.putExtra("PhoneNumber", "+6017 1212546")
            intent.putExtra("TotalContactAmount", "RM 4,500.00")
            intent.putExtra("PaymentDue", "2 days")
            intent.putExtra("LateCharges", "RM 500.00")
            intent.putExtra("BillsPeriod", "Every 5th of the month")
            intent.putExtra("AccountReceive", "Hong Leong Bank")

            startActivity(intent)
        }

        cardViewThree?.setOnClickListener {
            val intent = Intent(activity, TenantDetailActivity::class.java)
            intent.putExtra("Username", "Jaslyn Shu")
            intent.putExtra("ContactDate", "5 months contact")
            intent.putExtra("DayLeft", "Ended")
            intent.putExtra("RentalPrice", "RM 1200.00 / month")
            intent.putExtra("BillsStatus", "Paid")
            intent.putExtra("StartDate", "12 March 2020")
            intent.putExtra("EndDate", "13 August 2020")
            intent.putExtra("Email", "Jaslyn@gmail.com")
            intent.putExtra("PhoneNumber", "+6017 3455432")
            intent.putExtra("TotalContactAmount", "RM 6,000.00")
            intent.putExtra("PaymentDue", "10 days")
            intent.putExtra("LateCharges", "RM 500.00")
            intent.putExtra("BillsPeriod", "Every 12th of the month")
            intent.putExtra("AccountReceive", "Public Bank")

            startActivity(intent)
        }

        view.view_pager_tenants.setOnPageChangeListener(object: ViewPager.OnPageChangeListener
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

        return view
    }

    inner class ViewPagerAdapterTenants(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
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
         * @return A new instance of fragment MonthlyInstallmentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MonthlyInstallmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}