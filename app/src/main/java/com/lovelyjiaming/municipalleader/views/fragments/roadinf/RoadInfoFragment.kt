package com.lovelyjiaming.municipalleader.views.fragments.roadinf


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import kotlinx.android.synthetic.main.fragment_road_info.*

class RoadInfoFragment : Fragment() {
    //
    private val mListFragment: MutableList<Fragment> by lazy {
        mutableListOf(RoadInfoWholeFragment.newInstance(), RoadRedLineFragment.newInstance(), RoadFacilityFragment.newInstance())
    }
    private val mListTitles: MutableList<String> = mutableListOf("道路信息列表 ", "红线外道路列表 ", "21项服务设施 ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_road_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AutoUtils.auto(view)
        //
        viewpager_road_info.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(p0: Int): Fragment = mListFragment[p0]
            override fun getCount(): Int = 3
            override fun getPageTitle(position: Int): CharSequence? {
                return mListTitles[position]
            }
        }
        road_tablayout.setupWithViewPager(viewpager_road_info)
    }


    companion object {
        fun newInstance() =
                RoadInfoFragment()
    }
}
