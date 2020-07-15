package com.example.tenantyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ViewAdapter(manager: FragmentManager): FragmentPagerAdapter(manager)
{
    private var context: Context? = null
    private var layoutInflater: LayoutInflater? = null
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment/*, title: String*/)
    {
        fragmentList.add(fragment)
        //titleList.add(title)
    }

    /*override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }*/

}