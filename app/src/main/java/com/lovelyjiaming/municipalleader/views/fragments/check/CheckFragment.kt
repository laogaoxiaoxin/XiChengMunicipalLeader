package com.lovelyjiaming.municipalleader.views.fragments.check


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.activitys.MainActivity
import kotlinx.android.synthetic.main.fragment_check.*

/**
 * A simple [Fragment] subclass.
 * 巡查
 */
class CheckFragment : Fragment() {

    private val mListCheckFragments: List<Fragment> by lazy {
        listOf(CheckPersonLocateFragment.newInstance(), /*CheckPersonTrackFragment.newInstance(), */CheckCaseNoEndFragment.newInstance(this), CheckCaseCalcuFragment.newInstance())
    }
    private var mCurrentFraIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbl_check_top.addTab(tbl_check_top.newTab().setText("人员位置 "))
        tbl_check_top.addTab(tbl_check_top.newTab().setText("案件查询 "))
        tbl_check_top.addTab(tbl_check_top.newTab().setText("案件统计 "))
        switchDisplayFragment(0)
        setClickListener()
    }

    private fun setClickListener() {
        tbl_check_top.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                switchDisplayFragment(tab?.position!!)
            }
        })
    }

    fun displayCaseCount(caseCount: Int) {
        tbl_check_top.getTabAt(1)?.text = "案件查询 ($caseCount) "
    }

    private fun switchDisplayFragment(nDisplayIndex: Int) {
        //显示更多选项
        if (nDisplayIndex == 1)
            (activity as MainActivity).displayMoreTypeImg(View.VISIBLE, "patrol")
        else
            (activity as MainActivity).displayMoreTypeImg(View.GONE, "patrol")

        //
        val mgr = childFragmentManager.beginTransaction()
        if (mCurrentFraIndex == nDisplayIndex) {
            if (!mListCheckFragments[nDisplayIndex].isAdded)
            //就是当前，但是还没添加进栈
                mgr.add(R.id.check_fragment_container, mListCheckFragments[nDisplayIndex]).show(mListCheckFragments[nDisplayIndex])
        } else {
            if (mListCheckFragments[nDisplayIndex].isAdded) {
                mgr.hide(mListCheckFragments[mCurrentFraIndex]).show(mListCheckFragments[nDisplayIndex])
            } else {
                mgr.hide(mListCheckFragments[mCurrentFraIndex]).add(R.id.check_fragment_container, mListCheckFragments[nDisplayIndex]).show(mListCheckFragments[nDisplayIndex])
            }
        }
        mgr.commit()
        mCurrentFraIndex = nDisplayIndex
    }

    fun startSearchPatrolText(condition: String) {
        (mListCheckFragments[1] as CheckCaseNoEndFragment).startSearch(condition)
    }

    fun showMainMoreType(): Int = if (mCurrentFraIndex == 1) View.VISIBLE else View.GONE


    companion object {
        fun newInstance() = CheckFragment()
    }

}
