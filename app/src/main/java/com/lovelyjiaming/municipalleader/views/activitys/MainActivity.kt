package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.fragments.check.CheckFragment
import com.lovelyjiaming.municipalleader.views.fragments.danger.DangerFragment
import com.lovelyjiaming.municipalleader.views.fragments.engineer.EngineerFragment
import com.lovelyjiaming.municipalleader.views.fragments.roadinf.RoadInfoFragment
import com.lovelyjiaming.municipalleader.views.fragments.save.SaveFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //publish ,me,check,save,danger,engineer
    private val mListFragments: MutableList<Fragment> by lazy {
        mutableListOf(CheckFragment.newInstance(), SaveFragment.newInstance(), DangerFragment.newInstance(), EngineerFragment.newInstance(), RoadInfoFragment.newInstance())
    }
    private var mCurrentFraIndex = 0
    //
    private var mPageType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AutoUtils.auto(this)
        switchDisplayFragment(0)
        setClickListener()
    }

    private fun setClickListener() {
        ll_main_bottom_check.setOnClickListener {
            switchDisplayFragment(0)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check_selected)
            tv_main_bottom_tools_check.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save)
            tv_main_bottom_tools_save.setTextColor(Color.BLACK)
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger)
            tv_main_bottom_tools_danger.setTextColor(Color.BLACK)
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
            iv_main_bottom_tools_road.setImageResource(R.drawable.main_bottom_tools_road)
            tv_main_bottom_tools_road.setTextColor(Color.BLACK)
            //
            mPageType = if ((mListFragments[0] as CheckFragment).getCurrentIndex() == 1) "patrol1" else "patrol2"
            displayMoreTypeImg((mListFragments[0] as CheckFragment).showMainMoreType(), mPageType)
            iv_daily_work.visibility = View.VISIBLE
        }
        //
        ll_main_bottom_save.setOnClickListener {
            mPageType = "cure"
            switchDisplayFragment(1)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save_selected)
            tv_main_bottom_tools_save.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger)
            tv_main_bottom_tools_danger.setTextColor(Color.BLACK)
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
            iv_main_bottom_tools_road.setImageResource(R.drawable.main_bottom_tools_road)
            tv_main_bottom_tools_road.setTextColor(Color.BLACK)
            displayMoreTypeImg((mListFragments[1] as SaveFragment).showMainMoreType(), mPageType)
            iv_daily_work.visibility = View.GONE
        }
        //
        ll_main_bottom_danger.setOnClickListener {
            mPageType = "emergency"
            switchDisplayFragment(2)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save)
            tv_main_bottom_tools_save.setTextColor(Color.BLACK)
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger_selected)
            tv_main_bottom_tools_danger.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
            iv_main_bottom_tools_road.setImageResource(R.drawable.main_bottom_tools_road)
            tv_main_bottom_tools_road.setTextColor(Color.BLACK)
            displayMoreTypeImg((mListFragments[2] as DangerFragment).showMainMoreType(), mPageType)
            iv_daily_work.visibility = View.GONE
        }
        //
        ll_main_bottom_engineer.setOnClickListener {
            switchDisplayFragment(3)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save)
            tv_main_bottom_tools_save.setTextColor(Color.BLACK)
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger)
            tv_main_bottom_tools_danger.setTextColor(Color.BLACK)
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer_selected)
            tv_main_bottom_tools_engineer.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_road.setImageResource(R.drawable.main_bottom_tools_road)
            tv_main_bottom_tools_road.setTextColor(Color.BLACK)
            iv_daily_work.visibility = View.GONE
        }
        //
        ll_main_bottom_road.setOnClickListener {
            mPageType = "road"
            switchDisplayFragment(4)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save)
            tv_main_bottom_tools_save.setTextColor(Color.BLACK)
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger)
            tv_main_bottom_tools_danger.setTextColor(Color.BLACK)
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
            iv_main_bottom_tools_road.setImageResource(R.drawable.main_bottom_tools_road_selected)
            tv_main_bottom_tools_road.setTextColor(Color.parseColor("#ffcc0000"))
            iv_daily_work.visibility = View.GONE
            displayMoreTypeImg(View.GONE, mPageType)
        }
        //
        iv_daily_work.setOnClickListener {
            val intent = Intent(this, DailyWorkListActivity::class.java)
            startActivity(intent)
        }
    }

    //是否显示更多图标
    fun displayMoreTypeImg(visibility: Int, type: String) {
        iv_more_type_choose.visibility = visibility
        this.mPageType = type
        iv_more_type_choose.setOnClickListener {
            val intent = Intent(this, ChooseConditionActivity::class.java)
            intent.putExtra("type", type)
            startActivityForResult(intent, 1045)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1045 && resultCode == 1046 && data != null) {
            when (mPageType) {
                "cure" -> {
                    val dataResult = data.getSerializableExtra("condition") as MutableMap<String, String>
                    (mListFragments[1] as SaveFragment).startSearchSaveText(dataResult)
                }
                "emergency" -> {
                    val dataResult = data.getSerializableExtra("condition") as MutableMap<String, String>
                    (mListFragments[2] as DangerFragment).startSearchEmergencyText(dataResult)
                }
                "patrol1" -> {
                    val dataResult = data.getSerializableExtra("condition") as MutableMap<String, String>
                    (mListFragments[0] as CheckFragment).startSearchPatrolText(mPageType, dataResult)
                }
                "patrol2" -> {
                    val dataResult = data.getSerializableExtra("condition") as MutableMap<String, String>
                    (mListFragments[0] as CheckFragment).startSearchPatrolText(mPageType, dataResult)
                }
            }
        }
    }

    private fun switchDisplayFragment(nDisplayIndex: Int) {
        val mgr = supportFragmentManager.beginTransaction()
        //
        if (mCurrentFraIndex == nDisplayIndex) {
            if (!mListFragments[nDisplayIndex].isAdded)
            //就是当前，但是还没添加进栈
                mgr.add(R.id.main_fragment_container, mListFragments[nDisplayIndex]).show(mListFragments[nDisplayIndex])
        } else {
            if (mListFragments[nDisplayIndex].isAdded) {
                mgr.hide(mListFragments[mCurrentFraIndex]).show(mListFragments[nDisplayIndex])
            } else {
                mgr.hide(mListFragments[mCurrentFraIndex]).add(R.id.main_fragment_container, mListFragments[nDisplayIndex]).show(mListFragments[nDisplayIndex])
            }
        }
        mgr.commit()
        mCurrentFraIndex = nDisplayIndex
    }
}
