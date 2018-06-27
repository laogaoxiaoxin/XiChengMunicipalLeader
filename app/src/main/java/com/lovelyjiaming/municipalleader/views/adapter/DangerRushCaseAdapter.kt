package com.lovelyjiaming.municipalleader.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EmergencyTaskItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil
import com.lovelyjiaming.municipalleader.views.activitys.DangerRushCaseDetailActivity

class DangerRushCaseAdapter(private val ctx: Context) : RecyclerView.Adapter<DangerRushCaseAdapter.ViewHolder>() {
    var listData: List<EmergencyTaskItemClass>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_noend_case, parent, false))

    override fun getItemCount(): Int = listData?.size ?: 0

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData?.let {
            holder.check_noend_case_name.text = listData?.get(position)?.taskName
            holder.check_noend_case_no.text = "编号：" + listData?.get(position)?.taskNumber
            holder.check_noend_case_status.text = listData?.get(position)?.taskState
            holder.check_noend_case_time.text = "时间：" + listData?.get(position)?.taskDate
            Glide.with(ctx).load(XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS + listData?.get(position)?.taskFirst).into(holder.check_noend_case_img)
            //
            holder.itemView.setOnClickListener {
                val intent = Intent(ctx, DangerRushCaseDetailActivity::class.java)
                intent.putExtra("caseinfo", listData?.get(position))
                ctx.startActivity(intent)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val check_noend_case_name by lazy {
            itemView.findViewById<TextView>(R.id.check_noend_case_name)
        }
        val check_noend_case_status by lazy {
            itemView.findViewById<TextView>(R.id.check_noend_case_status)
        }
        val check_noend_case_no by lazy {
            itemView.findViewById<TextView>(R.id.check_noend_case_no)
        }
        val check_noend_case_time by lazy {
            itemView.findViewById<TextView>(R.id.check_noend_case_time)
        }
        val check_noend_case_img by lazy {
            itemView.findViewById<ImageView>(R.id.check_noend_case_img)
        }

    }
}