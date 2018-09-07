package com.lovelyjiaming.municipalleader.views.fragments.save


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.CureAppliedTaskClass
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.views.adapter.CheckNoEndCaseAdapter
import kotlinx.android.synthetic.main.common_search_layout.*
import kotlinx.android.synthetic.main.common_top_del_condition.*
import kotlinx.android.synthetic.main.fragment_save_finish_task.*
import java.text.SimpleDateFormat
import java.util.*

class SaveFinishTaskFragment : Fragment() {
    private val mAdapter: CheckNoEndCaseAdapter by lazy {
        CheckNoEndCaseAdapter(activity as Context)
    }
    private var mbFirstTime = true
    private val mPresenter: CureFinishTaskPresenter = CureFinishTaskPresenter(this)
    //返回列表
    private var mListResult: List<InspectUndoneItemClass>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_finish_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        save_finish_swiperefresh.isRefreshing = true
        save_finish_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        save_finish_recyclerview.adapter = mAdapter
        //
        save_finish_swiperefresh.setOnRefreshListener {
            mPresenter.requestData()
            save_finish_del_condition.visibility = View.GONE
        }
        //
        edt_search_condition.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val list = mListResult?.filter {
                    it.taskName?.contains(s.toString()) ?: false
                }?.toMutableList()
                mAdapter.setData(list)
                mParentFragment.displayFinishedTaskCount(list?.size ?: 0)
                save_finish_del_condition.visibility = View.GONE//与筛选项互斥
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint && mbFirstTime) {
            mPresenter.requestData()
            mbFirstTime = false
        }
    }

    //第一次从网络来的数据
    fun setNetResultData(strResult: String) {
        save_finish_swiperefresh.isRefreshing = false
        mListResult = Gson().fromJson(strResult, CureAppliedTaskClass::class.java).CureAppliedList
        mAdapter.setData(mListResult?.toMutableList())
        mAdapter.holderType = "saveonlinetask"
        mParentFragment.displayFinishedTaskCount(mListResult?.size ?: 0)
    }

    private fun getSeason(): Int =
            when (Calendar.getInstance().get(Calendar.MONTH)) {
                Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH -> 1
                Calendar.APRIL, Calendar.MAY, Calendar.JUNE -> 2
                Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER -> 3
                Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER -> 4
                else -> -1
            }

    //
    private val mMapCondition: MutableMap<String, String> = mutableMapOf()

    fun startSearchConditionText(searchParam: MutableMap<String, String>) {
        mMapCondition.putAll(searchParam)
        //
        var listTmp: List<InspectUndoneItemClass>? = null
        save_finish_del_condition.visibility = View.GONE
        first_del_condition.visibility = View.GONE
        third_del_condition.visibility = View.GONE
        second_del_condition.visibility = View.GONE
        edt_search_condition.setText("")

        //先过滤时间
        if (mMapCondition.containsKey("startdate") && mMapCondition.containsKey("enddate")) {
            fourth_del_condition.text = mMapCondition["startdate"] + "~" + mMapCondition["enddate"]
            fourth_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
            //比对时间
            val spFormat = SimpleDateFormat("yyyy-MM-dd")
            val start = spFormat.parse(mMapCondition["startdate"])
            val end = spFormat.parse(mMapCondition["enddate"])
            listTmp = mListResult?.filter { start.time <= spFormat.parse(it.taskDate).time && spFormat.parse(it.taskDate).time <= end.time }?.toMutableList()
        }

        if (mMapCondition.containsKey("rank")) {
            listTmp = listTmp?.filter {
                it.taskRank?.contains(mMapCondition["rank"].toString()) ?: false
            } ?: mListResult?.filter {
                it.taskRank?.contains(mMapCondition["rank"].toString()) ?: false
            }
            first_del_condition.text = mMapCondition["rank"]
            first_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        if (mMapCondition.containsKey("type")) {
            listTmp = listTmp?.filter { it.taskType == mMapCondition["type"] } ?: mListResult?.filter { it.taskType == mMapCondition["type"] }
            third_del_condition.text = mMapCondition["type"]
            third_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        if (mMapCondition.containsKey("office")) {
            listTmp = listTmp?.filter { it.taskOffice == mMapCondition["office"] } ?: mListResult?.filter { it.taskOffice == mMapCondition["office"] }
            second_del_condition.text = mMapCondition["office"]
            second_del_condition.visibility = View.VISIBLE
            save_finish_del_condition.visibility = View.VISIBLE
        }
        mAdapter.setData(listTmp?.toMutableList())
        mParentFragment.displayFinishedTaskCount(listTmp?.size ?: 0)
    }

    companion object {
        lateinit var mParentFragment: SaveFragment
        fun newInstance(fragment: SaveFragment): SaveFinishTaskFragment {
            mParentFragment = fragment
            return SaveFinishTaskFragment()
        }
    }
}
