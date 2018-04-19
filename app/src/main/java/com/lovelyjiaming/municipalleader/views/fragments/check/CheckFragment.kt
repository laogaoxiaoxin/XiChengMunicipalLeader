package com.lovelyjiaming.municipalleader.views.fragments.check


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import kotlinx.android.synthetic.main.fragment_check.*

/**
 * A simple [Fragment] subclass.
 * 巡查
 */
class CheckFragment : Fragment() {

    private val m_listCheckFragments: List<Fragment> by lazy {
        listOf(CheckPersonLocateFragment.newInstance(), CheckCaseNoEndFragment.newInstance(), CheckCaseCalcuFragment.newInstance())
    }
    private var mCurrentFraIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbl_check_top.addTab(tbl_check_top.newTab().setText("人员位置"))
        tbl_check_top.addTab(tbl_check_top.newTab().setText("未结案件"))
        tbl_check_top.addTab(tbl_check_top.newTab().setText("案件统计"))
        switchDisplayFragment(0)
        setClickListener()
    }

    private fun setClickListener() {
        tbl_check_top.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> switchDisplayFragment(0)
                    1 -> switchDisplayFragment(1)
                    2 -> switchDisplayFragment(2)
                }
            }

        })
    }

    private fun switchDisplayFragment(nDisplayIndex: Int) {
        val mgr = childFragmentManager.beginTransaction()
        //
        if (mCurrentFraIndex == nDisplayIndex) {
            if (!m_listCheckFragments[nDisplayIndex].isAdded)
            //就是当前，但是还没添加进栈
                mgr.add(R.id.check_fragment_container, m_listCheckFragments[nDisplayIndex]).show(m_listCheckFragments[nDisplayIndex])
        } else {
            if (m_listCheckFragments[nDisplayIndex].isAdded) {
                mgr.hide(m_listCheckFragments[mCurrentFraIndex]).show(m_listCheckFragments[nDisplayIndex])
            } else {
                mgr.hide(m_listCheckFragments[mCurrentFraIndex]).add(R.id.check_fragment_container, m_listCheckFragments[nDisplayIndex]).show(m_listCheckFragments[nDisplayIndex])
            }
        }
        mgr.commit()
        mCurrentFraIndex = nDisplayIndex
    }

    companion object {
        fun newInstance() = CheckFragment()
    }

}
