package com.lovelyjiaming.municipalleader.views.activitys

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.fragments.check.CheckFragment
import com.lovelyjiaming.municipalleader.views.fragments.danger.DangerFragment
import com.lovelyjiaming.municipalleader.views.fragments.engineer.EngineerFragment
import com.lovelyjiaming.municipalleader.views.fragments.save.SaveFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //publish ,me,check,save,danger,engineer
    private val m_listFragments: MutableList<Fragment> by lazy {
        mutableListOf(CheckFragment.newInstance(), SaveFragment.newInstance(), DangerFragment.newInstance(), EngineerFragment.newInstance())
    }
    private var mCurrentFraIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AutoUtils.setSize(this, false, 1080, 1920)
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
        }
        //
        ll_main_bottom_save.setOnClickListener {
            switchDisplayFragment(1)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save_selected)
            tv_main_bottom_tools_save.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger)
            tv_main_bottom_tools_danger.setTextColor(Color.BLACK)
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
        }
        //
        ll_main_bottom_danger.setOnClickListener {
            switchDisplayFragment(2)
            iv_main_bottom_tools_check.setImageResource(R.drawable.main_bottom_tools_check)
            tv_main_bottom_tools_check.setTextColor(Color.BLACK)
            iv_main_bottom_tools_save.setImageResource(R.drawable.main_bottom_tools_save)
            tv_main_bottom_tools_save.setTextColor(Color.BLACK)
            iv_main_bottom_tools_danger.setImageResource(R.drawable.main_bottom_tools_danger_selected)
            tv_main_bottom_tools_danger.setTextColor(Color.parseColor("#ffcc0000"))
            iv_main_bottom_tools_engineer.setImageResource(R.drawable.main_bottom_tools_engineer)
            tv_main_bottom_tools_engineer.setTextColor(Color.BLACK)
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
        }
    }

    //是否显示更多图标
    fun displayMoreTypeImg(visibility: Int, type: String) {
        iv_more_type_choose.visibility = visibility
        iv_more_type_choose.setOnClickListener {
            startActivity(Intent(this, ChooseConditionActivity::class.java))
        }
    }

    private fun switchDisplayFragment(nDisplayIndex: Int) {
        val mgr = supportFragmentManager.beginTransaction()
        //
        if (mCurrentFraIndex == nDisplayIndex) {
            if (!m_listFragments[nDisplayIndex].isAdded)
            //就是当前，但是还没添加进栈
                mgr.add(R.id.main_fragment_container, m_listFragments[nDisplayIndex]).show(m_listFragments[nDisplayIndex])
        } else {
            if (m_listFragments[nDisplayIndex].isAdded) {
                mgr.hide(m_listFragments[mCurrentFraIndex]).show(m_listFragments[nDisplayIndex])
            } else {
                mgr.hide(m_listFragments[mCurrentFraIndex]).add(R.id.main_fragment_container, m_listFragments[nDisplayIndex]).show(m_listFragments[nDisplayIndex])
            }
        }
        mgr.commit()
        mCurrentFraIndex = nDisplayIndex
    }
}
