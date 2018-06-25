package com.lovelyjiaming.municipalleader.views.fragments.engineer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import kotlinx.android.synthetic.main.fragment_engineer.*

class EngineerFragment : Fragment() {
    val m_listEngineerFragments: List<Fragment> by lazy {
        listOf(EngineerCurrentFragment.newInstance(), EngineerEndFragment.newInstance())
    }
    val m_listEngineerTitles: List<String> by lazy {
        listOf("案件查询 ", "竣工工程 ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_engineer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        viewpager_engineer.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment = m_listEngineerFragments[position]

            override fun getCount(): Int = 2
            override fun getPageTitle(position: Int): CharSequence? {
                return m_listEngineerTitles[position]
            }
        }
        tbl_engineer_top.setupWithViewPager(viewpager_engineer)
    }

    companion object {
        fun newInstance() = EngineerFragment()
    }
}
