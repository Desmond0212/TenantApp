package com.example.tenantyapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.tenantyapp.model.MessageVO
import com.example.tenantyapp.model.UserVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_new_tenant_creating.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val adapter = GroupAdapter<ViewHolder>()
    val latestMessageMap = HashMap<String, MessageVO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)

        val adapterTop = ViewPagerAdapterMessage(childFragmentManager)
        adapterTop.addFragment(BilllsTopbarFragment(), "FragmentMessage1")
        adapterTop.addFragment(BilllsTopbarFragment(), "FragmentMessage2")
        view.view_pager_message?.adapter = adapterTop
        view.dot_message?.setViewPager(view.view_pager_message)

        view.view_pager_message.setOnPageChangeListener(object: ViewPager.OnPageChangeListener
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

        view.layoutMessageContainer.setOnTouchListener { _, _ ->
            hideSoftKeyboard(activity)
            false
        }

        view.imgNotificationMessage.setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }

        view.recyclerView_latest_message.adapter = adapter
        view.recyclerView_latest_message.layoutManager = LinearLayoutManager(activity)

        //Adapter Click Listener
        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(activity, ChatRoomActivity::class.java)
            val row = item as LatestMessageRowVO

            intent.putExtra(NewMessageActivity.USER_KEY, row.chatPartnerUser)
            startActivity(intent)
        }

        view.imgMenuMessage.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }

        verifyUserIsLoggedIn()
        fetchCurrentUser()
        latestMessageListener(view)

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
                currentUser = p0.getValue(UserVO::class.java)
                Log.d(TAG, "Current User: ${currentUser?.profileimageUrl}")
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
                adapter.add(LatestMessageRowVO(chatMessage))
                latestMessageMap[p0.key!!] = chatMessage
                Log.d(TAG, "Latest Message: ${latestMessageMap[p0.key!!]}")
                refreshLatestMessageRecyclerView()
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?)
            {
                val chatMessage = p0.getValue(MessageVO::class.java) ?: return
                adapter.add(LatestMessageRowVO(chatMessage))
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
            adapter.add(LatestMessageRowVO(it))
        }
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

    inner class ViewPagerAdapterMessage(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager)
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

    companion object
    {
        const val TAG = "LatestMessageActivity"
        var currentUser : UserVO? = null

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MessageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MessageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}