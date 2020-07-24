package com.example.tenantyapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.luseen.spacenavigation.SpaceItem
import com.luseen.spacenavigation.SpaceNavigationView
import com.luseen.spacenavigation.SpaceOnClickListener


class MainActivity : AppCompatActivity()
{
    //private var spaceNavigationView: SpaceNavigationView? = null
    //private var btnBottomNewHome: Button? = null
    //private var btnBottomNewTenant: Button? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //spaceNavigationView = findViewById(R.id.space)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        routeToFragment(HomeFragment())

        /*val adapter = ViewAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(MonthlyInstallmentFragment())
        adapter.addFragment(NewHomeFragment())
        adapter.addFragment(BillFragment())
        adapter.addFragment(ProfileFragment())
        view_pager?.adapter = adapter
        dot3?.setViewPager(view_pager)*/

        /*val routeFrag = supportFragmentManager.beginTransaction()
        routeFrag.replace(R.id.fragment_container, HomeFragment())
        routeFrag.commit()*/

        /*spaceNavigationView?.initWithSaveInstanceState(savedInstanceState)
        spaceNavigationView?.addSpaceItem(SpaceItem("", R.drawable.ic_baseline_home_24))
        spaceNavigationView?.addSpaceItem(SpaceItem("", R.drawable.icon_2nd_tab))
        spaceNavigationView?.addSpaceItem(SpaceItem("", R.drawable.icon_third_tab))
        spaceNavigationView?.addSpaceItem(SpaceItem("", R.drawable.icon_profile_tab))

        spaceNavigationView?.setSpaceOnClickListener(object : SpaceOnClickListener
        {
            override fun onCentreButtonClick()
            {
                spaceNavigationView?.setCentreButtonSelectable(true)

                val bottomSheetDialog = BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialogTheme)
                val bottomSheetView = LayoutInflater.from(applicationContext).inflate(R.layout.layout_bottom_sheet, findViewById<View>(R.id.bottomSheetContainer) as? LinearLayout)

                btnBottomNewHome = bottomSheetView.findViewById(R.id.btnNewHome)
                btnBottomNewTenant = bottomSheetView.findViewById(R.id.btnNewTenant)

                btnBottomNewHome?.setOnClickListener {
                    val intent = Intent(this@MainActivity, NewHomeActivity::class.java)
                    startActivity(intent)
                }

                btnBottomNewTenant?.setOnClickListener {
                    val intent = Intent(this@MainActivity, NewTenantActivity::class.java)
                    startActivity(intent)
                }

                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()

                *//*val routeFrag = supportFragmentManager.beginTransaction()
                routeFrag.replace(R.id.fragment_container, NewHomeFragment())
                routeFrag.commit()*//*
            }

            override fun onItemClick(itemIndex: Int, itemName: String) {
                //Toast.makeText(this@MainActivity, "$itemIndex $itemName", Toast.LENGTH_SHORT).show()

                when (itemIndex)
                {
                    0 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, HomeFragment())
                        routeFrag.commit()
                    }

                    1 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, MonthlyInstallmentFragment())
                        routeFrag.commit()
                    }

                    2 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, BillFragment())
                        routeFrag.commit()
                    }

                    3 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, ProfileFragment())
                        routeFrag.commit()
                    }
                }
            }

            override fun onItemReselected(itemIndex: Int, itemName: String) {
                //Toast.makeText(this@MainActivity, "$itemIndex $itemName", Toast.LENGTH_SHORT).show()

                when (itemIndex)
                {
                    0 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, HomeFragment())
                        routeFrag.commit()
                    }

                    1 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, MonthlyInstallmentFragment())
                        routeFrag.commit()
                    }

                    2 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, BillFragment())
                        routeFrag.commit()
                    }

                    3 ->
                    {
                        val routeFrag = supportFragmentManager.beginTransaction()
                        routeFrag.replace(R.id.fragment_container, ProfileFragment())
                        routeFrag.commit()
                    }
                }
            }
        })*/
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId)
        {
            R.id.nav_home ->
            {
                routeToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_search ->
            {
                routeToFragment(MonthlyInstallmentFragment())
                return@OnNavigationItemSelectedListener true
            }

            /*R.id.nav_add_post ->
            {
                //startActivity(Intent(this, MessageFragment::class.java))
                routeToFragment(MessageFragment())
                return@OnNavigationItemSelectedListener true
            }*/

            R.id.nav_notifications ->
            {
                routeToFragment(DefectFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile ->
            {
                routeToFragment(BillFragment())
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    private fun routeToFragment(fragment: Fragment)
    {
        val routeFrag = supportFragmentManager.beginTransaction()
        routeFrag.replace(R.id.fragment_container, fragment)
        routeFrag.commit()
    }
}