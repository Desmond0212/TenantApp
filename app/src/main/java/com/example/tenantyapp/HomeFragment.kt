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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.tenantyapp.model.MessageVO
import com.example.tenantyapp.model.UserVO
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlinx.android.synthetic.main.fragment_defect.view.*
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

    val adapter = GroupAdapter<ViewHolder>()
    val latestMessageMap = HashMap<String, MessageVO>()

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

        viewPager = view.findViewById(R.id.view_pager)

        val adapter2 = ViewPagerAdapter(childFragmentManager)
        adapter2.addFragment(HomeTopbarFragment(), "A-13-14")
        HomeTopbarFragment.newInstance("A-20-23", "")
        adapter2.addFragment(HomeTopbarFragment(), "B-25-18")
        HomeTopbarFragment.newInstance("B-25-18", "")

        view.view_pager?.adapter = adapter2
        view.dot3?.setViewPager(view.view_pager)

        view.recyclerView_latest_home.adapter = adapter
        view.recyclerView_latest_home.layoutManager = LinearLayoutManager(activity)
        view.recyclerView_latest_home2.adapter = adapter
        view.recyclerView_latest_home2.layoutManager = LinearLayoutManager(activity)

        //Adapter Click Listener
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(activity, ChatRoomActivity::class.java)
            val row = item as LatestMessageRowVO

            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        verifyUserIsLoggedIn()
        fetchCurrentUser()
        latestMessageListener(view)

        view.layoutBilling1.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0033")
            intent.putExtra("Username", "Sharlin So")
            intent.putExtra("BillingStatus", "Bill")
            intent.putExtra("BillDate", "July 02, 2020")
            intent.putExtra("DueDate", "July 16, 2020")
            intent.putExtra("RentalAmount", "RM 1,200.00")
            intent.putExtra("ReceiptNumber", "RECEIPT - REC0001")
            intent.putExtra("PaymentDate", "July 15, 2020")
            intent.putExtra("PaymentNumber", 1)
            startActivity(intent)
        }

        view.layoutBilling2.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0012")
            intent.putExtra("Username", "Chloe Chua")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "June 12, 2020")
            intent.putExtra("DueDate", "June 30, 2020")
            intent.putExtra("RentalAmount", "RM 1,200.00")
            intent.putExtra("ReceiptNumber", "RECEIPT - REC0006")
            intent.putExtra("PaymentDate", "June 30, 2020")
            intent.putExtra("PaymentNumber", 2)
            startActivity(intent)
        }

        view.layoutBilling3.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0018")
            intent.putExtra("Username", "Vinny Chong")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "May 01, 2020")
            intent.putExtra("DueDate", "May 27, 2020")
            intent.putExtra("RentalAmount", "RM 1,500.00")
            intent.putExtra("ReceiptNumber", "RECEIPT - REC0022")
            intent.putExtra("PaymentDate", "May 18, 2020")
            intent.putExtra("PaymentNumber", 3)
            startActivity(intent)
        }

        view.layoutBilling4.setOnClickListener {
            val intent = Intent(activity, BillDetailsActivity::class.java)
            intent.putExtra("BillNumber", "BILL0038")
            intent.putExtra("Username", "Eddie Zhang")
            intent.putExtra("BillingStatus", "Paid")
            intent.putExtra("BillDate", "April 21, 2020")
            intent.putExtra("DueDate", "May 12, 2020")
            intent.putExtra("RentalAmount", "RM 700.00")
            intent.putExtra("ReceiptNumber", "RECEIPT - REC0045")
            intent.putExtra("PaymentDate", "May 04, 2020")
            intent.putExtra("PaymentNumber", 4)
            startActivity(intent)
        }

        view.cardViewDefectMessageHome.setOnClickListener {
            val intent = Intent(activity, DefectDetailActivity::class.java)
            intent.putExtra("DefectTitle", "Wifi is not working")
            intent.putExtra("PostDate", "July 20 2020")
            intent.putExtra("DefectStatus", "Open")
            intent.putExtra("DefectFrom", "Vinny Chong")
            intent.putExtra("cardNumber", 0)
            intent.putExtra("gotVideo", true)
            startActivity(intent)
        }

        view.layoutMessage1.visibility = View.GONE
        view.messageDivider.visibility = View.GONE
        view.layoutMessage2.visibility = View.GONE
        view.layoutBilling1.visibility = View.VISIBLE
        view.billingDivider.visibility = View.VISIBLE
        view.layoutBilling2.visibility = View.VISIBLE

        view.messageDivider3.visibility = View.GONE
        view.layoutMessage4.visibility = View.GONE
        view.messageDivider2.visibility = View.GONE
        view.layoutMessage3.visibility = View.GONE
        view.layoutBilling4.visibility = View.GONE
        view.billingDivider3.visibility = View.GONE
        view.layoutBilling3.visibility = View.GONE
        view.billingDivider2.visibility = View.GONE
        view.layoutMessage5.visibility = View.GONE
        view.messageDivider4.visibility = View.GONE

        view.view_pager.setOnPageChangeListener(object: ViewPager.OnPageChangeListener
        {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int)
            {
                if (position == 1)
                {
                    adapter.setOnItemClickListener { item, view ->
                        val intent = Intent(activity, ChatRoomActivity::class.java)
                        val row = item as LatestMessageRowVO2

                        intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
                        startActivity(intent)
                    }

                    fetchCurrentUser()
                    latestMessageListener2(view)

                    recyclerView_latest_home2.visibility = View.VISIBLE
                    recyclerView_latest_home.visibility = View.GONE

                    notesCardView.visibility = View.GONE
                    propertySummaryCardView.visibility = View.GONE

                    billingDivider2.visibility = View.GONE
                    messageDivider2.visibility = View.GONE
                    billingDivider.visibility = View.GONE
                    layoutMessage1.visibility = View.GONE
                    messageDivider.visibility = View.GONE
                    layoutMessage2.visibility = View.GONE
                    layoutBilling1.visibility = View.GONE
                    layoutBilling2.visibility = View.GONE

                    messageDivider3.visibility = View.GONE
                    layoutMessage4.visibility = View.GONE
                    layoutMessage3.visibility = View.GONE
                    layoutBilling4.visibility = View.VISIBLE
                    billingDivider3.visibility = View.VISIBLE
                    layoutBilling3.visibility = View.VISIBLE
                    layoutMessage5.visibility = View.VISIBLE
                    messageDivider4.visibility = View.GONE

                    lblBillMonth.text = "Billing - May"
                    lblUsernameAndDate1.text = "Eddie Zhang | July 15, 2020"
                    lblMessageTitle1.text = "Cracks on walls..."
                    imgDefectImage1.setImageDrawable(resources.getDrawable(R.drawable.defect_image4))
                    imgDefectImage2.setImageDrawable(resources.getDrawable(R.drawable.defect_image5))
                    imgDefectImage3.setImageDrawable(resources.getDrawable(R.drawable.defect_image6))

                    HomeTopbarFragment.newInstance("A-20-23", "A-10-13")

                    view.cardViewDefectMessageHome.setOnClickListener {
                        val intent = Intent(activity, DefectDetailActivity::class.java)
                        intent.putExtra("DefectTitle", "Cracks on the walls!!!")
                        intent.putExtra("PostDate", "July 15 2020")
                        intent.putExtra("DefectStatus", "Open")
                        intent.putExtra("DefectFrom", "Eddie Zhang")
                        intent.putExtra("cardNumber", 1)
                        intent.putExtra("gotVideo", false)
                        startActivity(intent)
                    }
                }
                else
                {
                    adapter.setOnItemClickListener { item, view ->
                        val intent = Intent(activity, ChatRoomActivity::class.java)
                        val row = item as LatestMessageRowVO

                        intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
                        startActivity(intent)
                    }

                    fetchCurrentUser()
                    latestMessageListener(view)

                    recyclerView_latest_home.visibility = View.VISIBLE
                    recyclerView_latest_home2.visibility = View.GONE

                    notesCardView.visibility = View.GONE
                    propertySummaryCardView.visibility = View.GONE

                    layoutMessage1.visibility = View.GONE
                    messageDivider.visibility = View.GONE
                    layoutMessage2.visibility = View.GONE
                    layoutBilling1.visibility = View.VISIBLE
                    billingDivider.visibility = View.VISIBLE
                    layoutBilling2.visibility = View.VISIBLE

                    messageDivider3.visibility = View.GONE
                    layoutMessage4.visibility = View.GONE
                    messageDivider2.visibility = View.GONE
                    layoutMessage3.visibility = View.GONE
                    layoutBilling4.visibility = View.GONE
                    billingDivider3.visibility = View.GONE
                    layoutBilling3.visibility = View.GONE
                    billingDivider2.visibility = View.GONE
                    layoutMessage5.visibility = View.GONE
                    messageDivider4.visibility = View.GONE

                    lblBillMonth.text = "Billing - June"
                    lblUsernameAndDate1.text = "Vinny Chong | July 20, 2020"
                    lblMessageTitle1.text = "Wifi is not working"
                    imgDefectImage1.setImageDrawable(resources.getDrawable(R.drawable.defect_image1))
                    imgDefectImage2.setImageDrawable(resources.getDrawable(R.drawable.defect_image2))
                    imgDefectImage3.setImageDrawable(resources.getDrawable(R.drawable.defect_image3))

                    //lblUnitTitle.text = "A-13-14"
                    HomeTopbarFragment.newInstance("A-33-89", "A-10-13")

                    view.cardViewDefectMessageHome.setOnClickListener {
                        val intent = Intent(activity, DefectDetailActivity::class.java)
                        intent.putExtra("DefectTitle", "Wifi is not working")
                        intent.putExtra("PostDate", "July 20 2020")
                        intent.putExtra("DefectStatus", "Open")
                        intent.putExtra("DefectFrom", "Vinny Chong")
                        intent.putExtra("cardNumber", 0)
                        intent.putExtra("gotVideo", true)
                        startActivity(intent)
                    }
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

        view.imgMenuHome.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun verifyUserIsLoggedIn()
    {
        val uid = FirebaseAuth.getInstance().uid

        if (uid == null)
        {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun fetchCurrentUser()
    {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot)
            {
                MessageFragment.currentUser = p0.getValue(UserVO::class.java)
                Log.d(MessageFragment.TAG, "Current User: ${MessageFragment.currentUser?.profileimageUrl}")
            }

            override fun onCancelled(p0: DatabaseError) {}
        })
    }

    private fun latestMessageListener(view: View)
    {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener
        {
            override fun onChildAdded(p0: DataSnapshot, p1: String?)
            {
                val chatMessage = p0.getValue(MessageVO::class.java) ?: return
                adapter.add(LatestMessageRowVO(chatMessage, 0))
                latestMessageMap[p0.key!!] = chatMessage
                Log.d(MessageFragment.TAG, "Latest Message: ${latestMessageMap[p0.key!!]}")
                refreshLatestMessageRecyclerView()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?)
            {
                val chatMessage = p0.getValue(MessageVO::class.java) ?: return
                adapter.add(LatestMessageRowVO(chatMessage, 0))
                latestMessageMap[p0.key!!] = chatMessage
                refreshLatestMessageRecyclerView()
            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
        })
    }

    private fun refreshLatestMessageRecyclerView()
    {
        adapter.clear()
        latestMessageMap.values.forEach {
            adapter.add(LatestMessageRowVO(it, 0))
        }
    }

    private fun latestMessageListener2(view: View)
    {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener
        {
            override fun onChildAdded(p0: DataSnapshot, p1: String?)
            {
                val chatMessage = p0.getValue(MessageVO::class.java) ?: return
                adapter.add(LatestMessageRowVO2(chatMessage, 1))
                latestMessageMap[p0.key!!] = chatMessage
                Log.d(MessageFragment.TAG, "Latest Message: ${latestMessageMap[p0.key!!]}")
                refreshLatestMessageRecyclerView2()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?)
            {
                val chatMessage = p0.getValue(MessageVO::class.java) ?: return
                adapter.add(LatestMessageRowVO2(chatMessage, 1))
                latestMessageMap[p0.key!!] = chatMessage
                refreshLatestMessageRecyclerView2()
            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}
        })
    }

    private fun refreshLatestMessageRecyclerView2()
    {
        adapter.clear()
        latestMessageMap.values.forEach {
            adapter.add(LatestMessageRowVO2(it, 1))
        }
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