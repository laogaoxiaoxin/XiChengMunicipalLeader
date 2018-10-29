package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.activitys.RoadInfoDetailActivity

data class RoadListTaskItem(val id: String?, val taskName: String?, val startName: String?, val endName: String?, val roadLength: String?, val roadWidth: String?, val roadArea: String?,
                            val motorwayNumber: String?, val motorwayLength: String?, val motorwayWidth: String?, val motorwayArea: String?, val nonMotorwayLength: String?, val nonMotorwayWidth: String?,
                            val nonMotorwayArea: String?)

data class RoadListJson(val roadListTask: MutableList<RoadListTaskItem>)

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class RoadInfoAdapter(private val context: Context) : RecyclerView.Adapter<RoadInfoAdapter.ViewHolder>() {
    private var mListData: MutableList<RoadListTaskItem>? = null

    fun setData(data: MutableList<RoadListTaskItem>) {
        mListData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_road_road_info, p0, false))
    }

    override fun getItemCount(): Int = mListData?.size ?: 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.apply {
            val result = mListData?.get(p1)
            item_road_road_name.text = "道路名称：" + result?.taskName
            item_road_road_start.text = "起点名称：" + result?.startName
            item_road_road_end.text = "终点名称：" + result?.endName
            item_road_road_lumianchangdu.text = "路面长度：" + result?.roadLength
            item_road_road_lumiankuandu.text = "路面宽度：" + result?.roadWidth
            item_road_road_lumianmianji.text = "路面面积：" + result?.roadArea
            item_road_road_jidongchedaochangdu.text = "机动车道长度：" + result?.motorwayLength
            item_road_road_jidongchedaokuandu.text = "机动车道宽度：" + result?.motorwayWidth
            item_road_road_jidongchedaomianji.text = "机动车道面积：" + result?.motorwayArea
            item_road_road_feijidongchedaochangdu.text = "非机动车道长度：" + result?.nonMotorwayLength
            item_road_road_feijidongchedaokuandu.text = "非机动车道宽度：" + result?.nonMotorwayWidth
            item_road_road_feijidongchedaomianji.text = "非机动车道面积：" + result?.nonMotorwayArea
            //
            itemView.setOnClickListener {
                val intent = Intent(context, RoadInfoDetailActivity::class.java)
                intent.putExtra("id", result?.id)
                context.startActivity(intent)
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val item_road_road_name: TextView = itemView.findViewById(R.id.item_road_road_name)
        val item_road_road_start: TextView = itemView.findViewById(R.id.item_road_road_start)
        val item_road_road_end: TextView = itemView.findViewById(R.id.item_road_road_end)
        val item_road_road_lumianchangdu: TextView = itemView.findViewById(R.id.item_road_road_lumianchangdu)
        val item_road_road_lumiankuandu: TextView = itemView.findViewById(R.id.item_road_road_lumiankuandu)
        val item_road_road_lumianmianji: TextView = itemView.findViewById(R.id.item_road_road_lumianmianji)
        val item_road_road_jidongchedaochangdu: TextView = itemView.findViewById(R.id.item_road_road_jidongchedaochangdu)
        val item_road_road_jidongchedaokuandu: TextView = itemView.findViewById(R.id.item_road_road_jidongchedaokuandu)
        val item_road_road_jidongchedaomianji: TextView = itemView.findViewById(R.id.item_road_road_jidongchedaomianji)
        val item_road_road_feijidongchedaochangdu: TextView = itemView.findViewById(R.id.item_road_road_feijidongchedaochangdu)
        val item_road_road_feijidongchedaokuandu: TextView = itemView.findViewById(R.id.item_road_road_feijidongchedaokuandu)
        val item_road_road_feijidongchedaomianji: TextView = itemView.findViewById(R.id.item_road_road_feijidongchedaomianji)

    }

}