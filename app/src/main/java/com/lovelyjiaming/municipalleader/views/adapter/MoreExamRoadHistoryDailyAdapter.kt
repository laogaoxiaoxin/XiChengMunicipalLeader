package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.activitys.ExamRoadDetailHelpItem

class MoreExamRoadHistoryDailyAdapter(private val ctx: Context) : RecyclerView.Adapter<MoreExamRoadHistoryDailyAdapter.ViewHolder>() {
    private var mMapData: MutableMap<String, List<ExamRoadDetailHelpItem>>? = null

    fun setData(map: MutableMap<String, List<ExamRoadDetailHelpItem>>) {
        mMapData = map
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_history_more_examroad_daily, parent, false))

    override fun getItemCount(): Int = mMapData?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mMapData?.let {
            val title = mMapData?.keys?.toMutableList()?.get(position)
            holder.itemTitle.text = title
            val dataAdapter = FeedbackExamRoadHistoryAdapter(ctx)
            holder.itemRecyclerView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
            holder.itemRecyclerView.adapter = dataAdapter
            dataAdapter.setPicData(mMapData!![title]!!)
            //
            holder.itemTitle.setOnClickListener {
                if (holder.itemRecyclerView.visibility == View.GONE) holder.itemRecyclerView.visibility = View.VISIBLE
                else holder.itemRecyclerView.visibility = View.GONE
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val itemTitle: TextView by lazy {
            itemView.findViewById(R.id.item_history_more_exam_road_daily_month) as TextView
        }
        val itemRecyclerView: RecyclerView by lazy {
            itemView.findViewById(R.id.item_history_more_exam_road_daily_month_recyclerview) as RecyclerView
        }
    }


}