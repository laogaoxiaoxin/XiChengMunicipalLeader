package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.R

data class RoadFacilityItem(val taskName: String?, val startName: String?, val endName: String?)
data class RoadFacilityJson(val facilitiesListTask: MutableList<RoadFacilityItem>)

class RoadFacilityAdapter(private val context: Context) : RecyclerView.Adapter<RoadFacilityAdapter.ViewHolder>() {

    private var mListData: MutableList<RoadFacilityItem>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_road_facilities, p0, false))

    override fun getItemCount(): Int = mListData?.size ?: 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.apply {
            val result = mListData?.get(p1)!!
            item_road_facilities_name.text = "道路名称：" + result.taskName
            item_road_facilities_start.text = "起点名称：" + result.startName
            item_road_facilities_end.text = "终点名称：" + result.endName
        }
    }

    fun setData(data: MutableList<RoadFacilityItem>) {
        this.mListData = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        //////
        val item_road_facilities_name: TextView = itemView.findViewById(R.id.item_road_facilities_name)
        val item_road_facilities_start: TextView = itemView.findViewById(R.id.item_road_facilities_start)
        val item_road_facilities_end: TextView = itemView.findViewById(R.id.item_road_facilities_end)
    }

}