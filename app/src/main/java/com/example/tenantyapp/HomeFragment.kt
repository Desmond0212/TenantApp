package com.example.tenantyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var viewPager: ViewPager? = null
    private var btnBottomNewHome: Button? = null
    private var btnBottomNewTenant: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        /*val adapter = ViewAdapter(fragmentManager!!)

        adapter.addFragment(HomeTopbarFragment())
        adapter.addFragment(NewHomeFragment())
        adapter.addFragment(BillFragment())
        adapter.addFragment(ProfileFragment())
        view.view_pager?.adapter = adapter*/

        viewPager = view.findViewById(R.id.view_pager)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(HomeTopbarFragment(), "A-13-14")
        HomeTopbarFragment.newInstance("A-20-23", "")
        adapter.addFragment(HomeTopbarFragment(), "B-25-18")
        HomeTopbarFragment.newInstance("B-25-18", "")

        view.view_pager?.adapter = adapter
        view.dot3?.setViewPager(view.view_pager)

        view.view_pager.setOnPageChangeListener(object: ViewPager.OnPageChangeListener
        {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 1)
                {
                    notesCardView.visibility = View.VISIBLE
                    propertySummaryCardView.visibility = View.VISIBLE
                    lblMessage.visibility = View.GONE
                    messageCardView.visibility = View.GONE
                    lblBillMonth.visibility = View.GONE
                    btnViewAll.visibility = View.GONE
                    billingCardView.visibility = View.GONE
                    //HomeTopbarFragment.newInstance("A-20-23", "")
                    //lblUnitTitle.text = "A-20-23"
                    HomeTopbarFragment.newInstance("A-20-23", "A-10-13")
                }
                else
                {
                    notesCardView.visibility = View.GONE
                    propertySummaryCardView.visibility = View.GONE
                    lblMessage.visibility = View.VISIBLE
                    messageCardView.visibility = View.VISIBLE
                    lblBillMonth.visibility = View.VISIBLE
                    btnViewAll.visibility = View.VISIBLE
                    billingCardView.visibility = View.VISIBLE
                    //lblUnitTitle.text = "A-13-14"
                    HomeTopbarFragment.newInstance("A-33-89", "A-10-13")
                }
            }
        })

        view.btnFab.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(activity?.applicationContext!!).inflate(R.layout.layout_bottom_sheet, view.findViewById<View>(R.id.bottomSheetContainer) as? LinearLayout)

            btnBottomNewHome = bottomSheetView.findViewById(R.id.btnNewHome)
            btnBottomNewTenant = bottomSheetView.findViewById(R.id.btnNewTenant)

            btnBottomNewHome?.setOnClickListener {
                bottomSheetDialog.dismiss()
                val intent = Intent(activity, NewHomeActivity::class.java)
                startActivity(intent)
            }

            btnBottomNewTenant?.setOnClickListener {
                bottomSheetDialog.dismiss()
                val intent = Intent(activity, NewTenantActivity::class.java)
                startActivity(intent)
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

        view.imgNotificationHome.setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
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
}