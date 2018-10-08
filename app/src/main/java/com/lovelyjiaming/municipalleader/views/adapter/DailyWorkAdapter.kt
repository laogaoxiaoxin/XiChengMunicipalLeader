package com.lovelyjiaming.municipalleader.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

data class SummaryItem(val userName: String, val savetime: String, val patrol: String?, val curing: String?, val emergency: String?, val project: String?)
data class SummaryResult(val result: Int, val summaryTask: MutableList<SummaryItem>)

class DailyWorkDateItemAdapter(private val context: Context) : RecyclerView.Adapter<DailyWorkDateItemAdapter.DailyWorkDateItemViewHolder>() {

    private var mapData: MutableMap<String, List<SummaryItem>>? = null

    fun setData(listData: MutableList<SummaryItem>) {
        this.mapData = listData.groupBy { it.savetime.substring(0, 7) }.toMutableMap()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWorkDateItemViewHolder {
        return DailyWorkDateItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_more_examroad_daily, parent, false))
    }

    override fun getItemCount(): Int = mapData?.keys?.size ?: 0

    override fun onBindViewHolder(holder: DailyWorkDateItemViewHolder, position: Int) {
        holder.apply {
            item_history_more_exam_road_daily_month.text = mapData?.keys?.toMutableList()?.get(position)
            item_history_more_exam_road_daily_month_recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val adapter = DailyWorkAdapter(context)
            item_history_more_exam_road_daily_month_recyclerview.adapter = adapter
            adapter.setData(mapData?.get(item_history_more_exam_road_daily_month.text)?.toMutableList()!!)
            //
            item_history_more_exam_road_daily_month.setOnClickListener {
                if (!mDisplay) {
                    item_history_more_exam_road_daily_month_recyclerview.visibility = View.VISIBLE
                    mDisplay = true
                } else {
                    item_history_more_exam_road_daily_month_recyclerview.visibility = View.GONE
                    mDisplay = false
                }
            }
        }
    }

    class DailyWorkDateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        var mDisplay = false
        val item_history_more_exam_road_daily_month = itemView.findViewById(R.id.item_history_more_exam_road_daily_month) as TextView
        val item_history_more_exam_road_daily_month_recyclerview = itemView.findViewById(R.id.item_history_more_exam_road_daily_month_recyclerview) as RecyclerView
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class DailyWorkAdapter(private val context: Context) : RecyclerView.Adapter<DailyWorkAdapter.DailyWorkViewHolder>() {

    private var mListData: MutableList<SummaryItem>? = null

    fun setData(listData: MutableList<SummaryItem>) {
        this.mListData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWorkViewHolder = DailyWorkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_daily_work, parent, false))

    override fun getItemCount(): Int = mListData?.size ?: 0

    private fun redText(str: String, start: Int, end: Int): SpannableStringBuilder {
        val textSpan = SpannableStringBuilder(str)
        textSpan.setSpan(ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return textSpan
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyWorkViewHolder, position: Int) {
        holder.apply {
            tv_daily_work_date.text = "日期：" + mListData?.get(position)?.savetime
            tv_daily_work_username.text = "账号：" + mListData?.get(position)?.userName
            //
            tv_daily_work_patrol.text = redText("巡查部：" + "\r\n" + if (mListData?.get(position)?.patrol.isNullOrEmpty() || mListData?.get(position)?.patrol == "null") "暂无" else mListData?.get(position)?.patrol, 0, 4)
            tv_daily_work_curing.text = redText("养护部：" + "\r\n" + if (mListData?.get(position)?.curing.isNullOrEmpty() || mListData?.get(position)?.curing == "null") "暂无" else mListData?.get(position)?.curing, 0, 4)
            tv_daily_work_emergency.text = redText("应急抢险部：" + "\r\n" + if (mListData?.get(position)?.emergency.isNullOrEmpty() || mListData?.get(position)?.emergency == "null") "暂无" else mListData?.get(position)?.emergency, 0, 6)
            tv_daily_work_project.text = redText("工程部：" + "\r\n" + if (mListData?.get(position)?.project.isNullOrEmpty() || mListData?.get(position)?.project == "null") "暂无" else mListData?.get(position)?.project, 0, 4)

            //
            tv_daily_work_displaymore.setOnClickListener {
                if (!mDisplay) {
                    tv_daily_work_username.visibility = View.VISIBLE
                    tv_daily_work_patrol.visibility = View.VISIBLE
                    tv_daily_work_curing.visibility = View.VISIBLE
                    tv_daily_work_emergency.visibility = View.VISIBLE
                    tv_daily_work_project.visibility = View.VISIBLE
                    tv_daily_work_displaymore.text = "收起"
                    mDisplay = true
                } else {
                    tv_daily_work_username.visibility = View.GONE
                    tv_daily_work_patrol.visibility = View.GONE
                    tv_daily_work_curing.visibility = View.GONE
                    tv_daily_work_emergency.visibility = View.GONE
                    tv_daily_work_project.visibility = View.GONE
                    tv_daily_work_displaymore.text = "展开"
                    mDisplay = false
                }
            }
        }
    }

    class DailyWorkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        var mDisplay = false
        val tv_daily_work_date = itemView.findViewById(R.id.tv_daily_work_date) as TextView
        val tv_daily_work_username = itemView.findViewById(R.id.tv_daily_work_username) as TextView
        val tv_daily_work_group = itemView.findViewById(R.id.tv_daily_work_group) as TextView
        val tv_daily_work_patrol = itemView.findViewById(R.id.tv_daily_work_patrol) as TextView
        val tv_daily_work_curing = itemView.findViewById(R.id.tv_daily_work_curing) as TextView
        val tv_daily_work_emergency = itemView.findViewById(R.id.tv_daily_work_emergency) as TextView
        val tv_daily_work_project = itemView.findViewById(R.id.tv_daily_work_project) as TextView

        val tv_daily_work_displaymore = itemView.findViewById(R.id.tv_daily_work_displaymore) as TextView
    }
}