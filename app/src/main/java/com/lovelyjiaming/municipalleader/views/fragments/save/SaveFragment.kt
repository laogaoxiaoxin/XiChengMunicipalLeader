package com.lovelyjiaming.municipalleader.views.fragments.save


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import kotlinx.android.synthetic.main.fragment_save.*

class SaveFragment : Fragment() {

    val m_listSaveFragments: List<Fragment> by lazy {
        listOf(SaveCurrentTaskFragment.newInstance(), SaveWholeCalcuFragment.newInstance())
    }
    val m_listSaveFragementsNames: List<String> by lazy {
        listOf("在施任务 ", "综合统计 ")
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
            override fun getItem(position: Int): Fragment = m_listSaveFragments[position]

            override fun getCount(): Int = 2

            override fun getPageTitle(position: Int): CharSequence? {
                return m_listSaveFragementsNames[position]
            }
        }
        tbl_save_top.setupWithViewPager(viewpager_save)
        viewpager_save.currentItem = 0
        viewpager_save.offscreenPageLimit = 1
    }

    companion object {
        fun newInstance() = SaveFragment()
    }
}
