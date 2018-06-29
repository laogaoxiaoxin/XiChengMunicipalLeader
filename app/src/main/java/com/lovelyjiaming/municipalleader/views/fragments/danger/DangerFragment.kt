package com.lovelyjiaming.municipalleader.views.fragments.danger

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import com.lovelyjiaming.municipalleader.R
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.views.activitys.MainActivity
import kotlinx.android.synthetic.main.fragment_danger.*

class DangerFragment : Fragment() {

    private val mListDangerFragments: List<Fragment> by lazy {
        listOf(DangerRushCaseFragment.newInstance(this), DangerMaterialCalcuFragment.newInstance(), DangerReadyPersonFragment.newInstance())
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
        viewpager_danger.offscreenPageLimit = 3
        //
        viewpager_danger.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0)
                    (activity as MainActivity).displayMoreTypeImg(View.VISIBLE, "emergency")
                else
                    (activity as MainActivity).displayMoreTypeImg(View.GONE, "emergency")
            }

        })
    }

    fun displayCaseCount(size: Int) {
        tbl_danger_top.getTabAt(0)?.text = "案件查询 ($size) "
    }

    fun startSearchEmergencyText(condition: HashMap<String, String>) {
        (mListDangerFragments[0] as DangerRushCaseFragment).startSearch(condition)
    }

    fun showMainMoreType(): Int = if (viewpager_danger == null || viewpager_danger.currentItem == 0) View.VISIBLE else View.GONE

    companion object {
        fun newInstance() = DangerFragment()
    }
}
