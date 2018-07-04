package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.*
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectUndoneItemClass
import com.lovelyjiaming.municipalleader.utils.XCNetWorkUtil.NETWORK_IMG_BASIC_ADDRESS
import com.lovelyjiaming.municipalleader.views.activitys.CheckNoEndDetailActivity
import com.lovelyjiaming.municipalleader.views.activitys.ExamRoadDetailActivity
import com.lovelyjiaming.municipalleader.views.activitys.SaveOnlineTaskActivity

//巡查-未结案件ViewHolder
//养护-在施任务ViewHolder
class CheckNoEndCaseAdapter(val ctx: Context) : RecyclerView.Adapter<CheckNoEndCaseAdapter.ViewHolder>() {
    private var listResult: MutableList<InspectUndoneItemClass>? = null
    var holderType: String = ""

    fun setData(listResult: MutableList<InspectUndoneItemClass>?) {
        this.listResult = listResult
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listResult?.let {
            holder.apply {
                check_noend_case_name.text = it[position].taskName
                check_noend_case_status.text = it[position].taskState
                check_noend_case_no.text = "编号：" + it[position].taskNumber
                check_noend_case_time.text = "时间：" + it[position].taskDate
                Glide.with(ctx).load(NETWORK_IMG_BASIC_ADDRESS + "${it[position].taskFirst}").into(check_noend_case_img)
                //
                if (it[position].taskType?.trim() ?: "" == "电力" || it[position].taskType?.trim() ?: "" == "电信" || it[position].taskType?.trim() ?: "" == "降水井"
                        || it[position].taskType?.trim() ?: "" == "雨污水" || it[position].taskType?.trim() ?: "" == "路灯" || it[position].taskType?.trim() ?: "" == "热力") {
                    check_noend_case_time.visibility = View.GONE
                    check_noend_case_status.visibility = View.GONE
                }
                //
                itemView.setOnClickListener { _ ->
                    //审批掘路的特殊页面，跳转详情页直接返回
                    if (it[position].taskType?.trim() ?: "" == "电力" || it[position].taskType?.trim() ?: "" == "电信" || it[position].taskType?.trim() ?: "" == "降水井"
                            || it[position].taskType?.trim() ?: "" == "雨污水" || it[position].taskType?.trim() ?: "" == "路灯" || it[position].taskType?.trim() ?: "" == "热力" || it[position].taskType?.trim() ?: "" == "人防工事治理") {
                        val intent = Intent(ctx, ExamRoadDetailActivity::class.java)
                        intent.putExtra("number", it[position].taskNumber)
                        ctx.startActivity(intent)
                        return@setOnClickListener
                    }

                    if (holderType == "saveonlinetask") {
                        val intent = Intent(ctx, SaveOnlineTaskActivity::class.java)
                        intent.putExtra("taskinfo", it[position])
                        ctx.startActivity(intent)
                    } else if (holderType == "checknoend") {
                        val intent = Intent(ctx, CheckNoEndDetailActivity::class.java)
                        intent.putExtra("taskinfo", it[position])
                        ctx.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_noend_case, parent, false))

    override fun getItemCount(): Int = this.listResult?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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