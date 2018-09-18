package com.lovelyjiaming.municipalleader.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

data class SummaryItem(val userName: String, val savetime: String, val littleGroup: String, val content: String)
data class SummaryResult(val summaryTask: MutableList<SummaryItem>)

class DailyWorkAdapter(private val context: Context) : RecyclerView.Adapter<DailyWorkAdapter.DailyWorkViewHolder>() {

    private var mListData: MutableList<SummaryItem>? = null

    fun setData(listData: MutableList<SummaryItem>) {
        this.mListData = listData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWorkViewHolder = DailyWorkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_daily_work, parent, false))

    override fun getItemCount(): Int = mListData?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyWorkViewHolder, position: Int) {
        holder.apply {
            tv_daily_work_date.text = "日期：" + mListData?.get(position)?.savetime
            tv_daily_work_username.text = "账号：" + mListData?.get(position)?.userName
            tv_daily_work_group.text = "组别：" + mListData?.get(position)?.littleGroup
            tv_daily_work_content.text = "内容：" + mListData?.get(position)?.content
            //
            tv_daily_work_displaymore.setOnClickListener {
                if (!mDisplay) {
                    tv_daily_work_username.visibility = View.VISIBLE
                    tv_daily_work_group.visibility = View.VISIBLE
                    tv_daily_work_content.visibility = View.VISIBLE
                    tv_daily_work_displaymore.text = "收起"
                    mDisplay = true
                } else {
                    tv_daily_work_username.visibility = View.GONE
                    tv_daily_work_group.visibility = View.GONE
                    tv_daily_work_content.visibility = View.GONE
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
        val tv_daily_work_content = itemView.findViewById(R.id.tv_daily_work_content) as TextView
        val tv_daily_work_displaymore = itemView.findViewById(R.id.tv_daily_work_displaymore) as TextView
    }
}