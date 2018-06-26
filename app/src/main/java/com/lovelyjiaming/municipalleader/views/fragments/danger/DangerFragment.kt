package com.lovelyjiaming.municipalleader.views.fragments.danger


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import kotlinx.android.synthetic.main.fragment_danger.*

class DangerFragment : Fragment() {

    private val mListDangerFragments: List<Fragment> by lazy {
        listOf(DangerRushCaseFragment.newInstance(), DangerMaterialCalcuFragment.newInstance(), DangerReadyPersonFragment.newInstance())
    }
    private val mListDangerTitles: List<String> by lazy {
        listOf("案件查询 ", "物资统计 ", "备勤人员 ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danger, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //
        viewpager_danger.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment = mListDangerFragments[position]
            override fun getCount(): Int = 3
            override fun getPageTitle(position: Int): CharSequence? = mListDangerTitles[position]
        }

        tbl_danger_top.setupWithViewPager(viewpager_danger)
        viewpager_danger.currentItem = 0
    }


    companion object {
        fun newInstance() = DangerFragment()
    }
}
