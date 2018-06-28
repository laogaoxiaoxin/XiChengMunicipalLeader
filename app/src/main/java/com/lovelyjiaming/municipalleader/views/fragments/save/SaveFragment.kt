package com.lovelyjiaming.municipalleader.views.fragments.save

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.views.activitys.MainActivity
import kotlinx.android.synthetic.main.activity_choose_condition.view.*
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment() {

    val mListSaveFragments: List<Fragment> by lazy {
        listOf(SaveCurrentTaskFragment.newInstance(this), SaveWholeCalcuFragment.newInstance())
    }
    val mListSaveFragmentNames: List<String> by lazy {
        listOf("案件查询 ", "综合统计 ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        viewpager_save.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment = mListSaveFragments[position]
            override fun getCount(): Int = 2
            override fun getPageTitle(position: Int): CharSequence? {
                return mListSaveFragmentNames[position]
            }
        }
        tbl_save_top.setupWithViewPager(viewpager_save)
        viewpager_save.currentItem = 0
        viewpager_save.offscreenPageLimit = 1
        //
        viewpager_save.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 0)
                    (activity as MainActivity).displayMoreTypeImg(View.VISIBLE, "cure")
                else
                    (activity as MainActivity).displayMoreTypeImg(View.GONE, "cure")
            }
        })
    }

    fun startSearchSaveText(sConditionText: HashMap<String, String>) {
        (mListSaveFragments[0] as SaveCurrentTaskFragment).startSearchConditionText(sConditionText)
    }

    fun displayCountText(size: Int) {
        tbl_save_top.getTabAt(0)?.text = "案件查询 ($size) "
    }

    fun showMainMoreType(): Int = if (viewpager_save == null || viewpager_save.currentItem == 0) View.VISIBLE else View.GONE

    companion object {
        fun newInstance() = SaveFragment()
    }
}
