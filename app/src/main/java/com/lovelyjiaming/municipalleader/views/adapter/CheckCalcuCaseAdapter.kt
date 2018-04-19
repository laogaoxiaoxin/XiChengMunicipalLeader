package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

class CheckCalcuCaseAdapter(private val ctx: Context) : RecyclerView.Adapter<CheckCalcuCaseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_calcu_case, parent, false))

    override fun getItemCount(): Int = 20

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }
    }
}