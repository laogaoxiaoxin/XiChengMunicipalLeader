package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.InspectCaseCountItemClass

class CheckCalcuCaseAdapter(private val ctx: Context) : RecyclerView.Adapter<CheckCalcuCaseAdapter.ViewHolder>() {
    var listResult: MutableList<InspectCaseCountItemClass>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_calcu_case, parent, false))

    override fun getItemCount(): Int = listResult?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listResult?.let {
            holder.check_calc_case_time.text = it[position].taskDate
            holder.check_calc_case_title.text = it[position].taskName
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val check_calc_case_title by lazy {
            itemView.findViewById<TextView>(R.id.check_calc_case_title)
        }
        val check_calc_case_time by lazy {
            itemView.findViewById<TextView>(R.id.check_calc_case_time)
        }
    }
}