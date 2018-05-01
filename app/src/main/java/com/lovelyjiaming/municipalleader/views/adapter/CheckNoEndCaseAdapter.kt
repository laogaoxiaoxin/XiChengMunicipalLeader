package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS

//巡查-未结案件ViewHolder

class CheckNoEndCaseAdapter(val ctx: Context) : RecyclerView.Adapter<CheckNoEndCaseAdapter.ViewHolder>() {

    var listResult: MutableList<InspectUndoneItemClass>? = null

    fun setData(listResult: MutableList<InspectUndoneItemClass>) {
        this.listResult = listResult
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listResult?.let {
            val listTmp = it
            holder.apply {
                check_noend_case_name.text = "任务名称：" + listTmp[position].taskName
                check_noend_case_status.text = listTmp[position].taskState
                check_noend_case_no.text = "编号：" + listTmp[position].taskNumber
                check_noend_case_time.text = "时间：" + listTmp[position].taskDate
                Glide.with(ctx).load(NETWORK_IMG_BASIC_ADDRESS + "${listTmp[position].taskFirst}").into(check_noend_case_img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_noend_case, parent, false))

    override fun getItemCount(): Int = this.listResult?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll_check_noend_notime by lazy {
            itemView.findViewById<LinearLayout>(R.id.ll_check_noend_notime)
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

        init {
            AutoUtils.auto(itemView)
        }
    }
}