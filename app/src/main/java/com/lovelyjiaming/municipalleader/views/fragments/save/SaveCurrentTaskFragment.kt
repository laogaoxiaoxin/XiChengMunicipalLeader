package com.lovelyjiaming.municipalleader.views.fragments.save

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.CureOnLineTaskClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_save_current_task.*
import java.text.SimpleDateFormat
import java.util.*

class SaveCurrentTaskFragment : Fragment() {

    val adapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)//复用巡查未结案item
    }
    private var mDetailInfoList: MutableList<InspectUndoneItemClass>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_current_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save_current_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        save_current_recyclerview.adapter = adapter
        save_current_swiperefresh.isRefreshing = true
        requestData()
        //
        save_current_swiperefresh.setOnRefreshListener {
            save_current_del_condition.visibility = View.GONE
            requestData()
        }
        //默认按照名称查询
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                save_current_del_condition.visibility = View.GONE
                //如果已经筛选过一次了，比如按照tasktype筛过一次了
                val list = mDetailInfoList?.filter { it.taskName?.contains(p0.toString())!! }?.toMutableList()
                mParentFragment.displayOnLineCountText(list?.size ?: 0)
                adapter.setData(list)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //
        cv_choose_fix_month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            //组串
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val month = if ("${Calendar.getInstance().get(Calendar.MONTH) + 1}".length == 2) Calendar.getInstance().get(Calendar.MONTH) + 1 else "0${Calendar.getInstance().get(Calendar.MONTH) + 1}"
            val lastDay = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)
            mMapCondition["startdate"] = "$year-$month-01 00:00:00"
            mMapCondition["enddate"] = "$year-$month-$lastDay 23:59:59"
            startSearchConditionText(mMapCondition)
        }
        //
        cv_choose_fix_3month.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            val year = Calendar.getInstance().get(Calendar.YEAR)
            when (getSeason()) {
                1 -> {
                    mMapCondition["startdate"] = "$year-01-01 00:00:00"
                    mMapCondition["enddate"] = "$year-03-31 23:59:59"
                }
                2 -> {
                    mMapCondition["startdate"] = "$year-04-01 00:00:00"
                    mMapCondition["enddate"] = "$year-06-30 23:59:59"
                }
                3 -> {
                    mMapCondition["startdate"] = "$year-07-01 00:00:00"
                    mMapCondition["enddate"] = "$year-09-30 23:59:59"
                }
                4 -> {
                    mMapCondition["startdate"] = "$year-10-01 00:00:00"
                    mMapCondition["enddate"] = "$year-12-31 23:59:59"
                }
            }
            startSearchConditionText(mMapCondition)
        }
        //
        cv_choose_fix_year.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            mMapCondition["startdate"] = "${Calendar.getInstance().get(Calendar.YEAR)}-01-01 00:00:00"
            mMapCondition["enddate"] = "${Calendar.getInstance().get(Calendar.YEAR)}-12-31 23:59:59"
            startSearchConditionText(mMapCondition)

        }
        //
        cv_choose_fix_week.setOnClickListener {
            cv_choose_fix_month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_3month.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_year.setCardBackgroundColor(Color.parseColor("#efefef"))
            cv_choose_fix_week.setCardBackgroundColor(Color.parseColor("#ffd2d2"))
            //
            val sdf = SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            val nowCal = Calendar.getInstance()
            val dayWeek = nowCal.get(Calendar.DAY_OF_WEEK)
            if (1 == dayWeek) {
                nowCal.add(Calendar.DAY_OF_MONTH, -1);
            }
            nowCal.firstDayOfWeek = Calendar.MONDAY;//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            val day = nowCal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
            nowCal.add(Calendar.DATE, nowCal.firstDayOfWeek - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            val start = sdf.format(nowCal.time)
            nowCal.add(Calendar.DATE, 6);
            val end = sdf.format(nowCal.time)
            mMapCondition["startdate"] = "$start 00:00:00"
            mMapCondition["enddate"] = "$end 23:59:59"
            startSearchConditionText(mMapCondition)
        }
    }

    private fun getSeason(): Int =
            when (Calendar.getInstance().get(Calendar.MONTH)) {
                Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> 1
                Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> 2
                Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> 3
                Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> 4
                else -> -1
            }

    private fun requestData() {
        XCNetWorkUtil.invokeGetRequest(activity!!, XCNetWorkUtil.NETWORK_BASIC_SAVE_ADDRESS + "getOnLineList", {
            val result = Gson().fromJson(it, CureOnLineTaskClass::class.java)
            mDetailInfoList = result.CureOnLineList.toMutableList()
            adapter.holderType = "saveonlinetask"
            adapter.setData(mDetailInfoList)//复用
            mParentFragment.displayOnLineCountText(mDetailInfoList?.size ?: 0)
            save_current_swiperefresh.isRefreshing = false
        })
    }

    //
    private val mMapCondition: MutableMap<String, String> = mutableMapOf()

    @SuppressLint("SetTextI18n")
    fun startSearchConditionText(condition: MutableMap<String, String>) {
        this.mMapCondition.putAll(condition)
        //缓存，保证成员变量mDetailInfoList总是完整的
        var localTmpDetailInfo = mDetailInfoList
        //
        first_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        fourth_del_condition.visibility = View.GONE

        //先过滤时间
        if (mMapCondition.containsKey("startdate") && mMapCondition.containsKey("enddate")) {
            fourth_del_condition.text = mMapCondition["startdate"] + "~" + mMapCondition["enddate"]
            save_current_del_condition.visibility = View.VISIBLE
            fourth_del_condition.visibility = View.VISIBLE
            //比对时间
            val spFormat = SimpleDateFormat("yyyy-MM-dd")
            val start = spFormat.parse(mMapCondition["startdate"])
            val end = spFormat.parse(mMapCondition["enddate"])
            localTmpDetailInfo = localTmpDetailInfo?.filter { start.time <= spFormat.parse(it.taskDate).time && spFormat.parse(it.taskDate).time <= end.time }?.toMutableList()
        }
        //type
        if (mMapCondition.containsKey("type")) {
            third_del_condition.text = mMapCondition["type"]
            third_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter { it.taskType == mMapCondition["type"] }?.toMutableList()
        }
        //rank
        if (mMapCondition.containsKey("rank")) {
            first_del_condition.text = mMapCondition["rank"]
            first_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter {
                it.taskRank?.contains(mMapCondition["rank"].toString()) ?: false
            }?.toMutableList()
        }
        //office
        if (mMapCondition.containsKey("office")) {
            second_del_condition.text = mMapCondition["office"]
            second_del_condition.visibility = View.VISIBLE
            save_current_del_condition.visibility = View.VISIBLE
            localTmpDetailInfo = localTmpDetailInfo?.filter { it.taskOffice == mMapCondition["office"] }?.toMutableList()
        }
        //
        mParentFragment.displayOnLineCountText(localTmpDetailInfo?.size ?: 0)
        adapter.setData(localTmpDetailInfo)
        Toast.makeText(activity, "共查找出案件${localTmpDetailInfo?.size
                ?: 0}件", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveCurrentTaskFragment {
            mParentFragment = fragment
            return SaveCurrentTaskFragment()
        }
    }
}
