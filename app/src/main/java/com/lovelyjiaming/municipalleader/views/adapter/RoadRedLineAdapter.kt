package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils


data class RoadRedLineItem(val id: String, val taskName: String?, val taskStreet: String?, val infraredRoadLength: String?, val infraredRoadWidth: String?, val infraredRoadArea: String?, val infraredSidewalkLength: String?,
                           val infraredSidewalkWidth: String?, val infraredSidewalkArea: String?, val infraredStoneLength: String?, val infraredStoneWidth: String?, val infraredStoneArea: String?)

data class RoadRedLineJson(val roadRedLineTask: MutableList<RoadRedLineItem>)

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class RoadRedLineAdapter(private val context: Context) : RecyclerView.Adapter<RoadRedLineAdapter.ViewHolder>() {
    //
    private var mListData: MutableList<RoadRedLineItem>? = null

    fun setData(data: MutableList<RoadRedLineItem>) {
        mListData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_road_red_line, p0, false))

    override fun getItemCount(): Int = mListData?.size ?: 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.apply {
            val result = mListData?.get(p1)!!
            item_road_red_line_name.text = "道路名称：" + result.taskName
            item_road_red_line_street.text = "所属街道：" + result.taskStreet
            item_road_red_line_infraredRoadLength.text = "红线外路面长度：" + result.infraredRoadLength
            item_road_red_line_infraredRoadWidth.text = "红线外路面宽度：" + result.infraredRoadWidth
            item_road_red_line_infraredRoadArea.text = "红线外路面面积：" + result.infraredRoadArea
            item_road_red_line_infraredSidewalkLength.text = "红线外便道长度：" + result.infraredSidewalkLength
            item_road_red_line_infraredSidewalkWidth.text = "红线外便道宽度：" + result.infraredSidewalkWidth
            item_road_red_line_infraredSidewalkArea.text = "红线外便道面积：" + result.infraredSidewalkArea
            item_road_red_line_infraredStoneLength.text = "红线外石材长度：" + result.infraredStoneLength
            item_road_red_line_infraredStoneWidth.text = "红线外石材宽度：" + result.infraredStoneWidth
            item_road_red_line_infraredStoneArea.text = "红线外石材面积：" + result.infraredStoneArea
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val item_road_red_line_name = itemView.findViewById<TextView>(R.id.item_road_red_line_name)
        val item_road_red_line_street = itemView.findViewById<TextView>(R.id.item_road_red_line_street)
        val item_road_red_line_infraredRoadLength = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredRoadLength)
        val item_road_red_line_infraredRoadWidth = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredRoadWidth)
        val item_road_red_line_infraredRoadArea = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredRoadArea)
        val item_road_red_line_infraredSidewalkLength = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredSidewalkLength)
        val item_road_red_line_infraredSidewalkWidth = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredSidewalkWidth)
        val item_road_red_line_infraredSidewalkArea = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredSidewalkArea)
        val item_road_red_line_infraredStoneLength = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredStoneLength)
        val item_road_red_line_infraredStoneWidth = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredStoneWidth)
        val item_road_red_line_infraredStoneArea = itemView.findViewById<TextView>(R.id.item_road_red_line_infraredStoneArea)

    }
}