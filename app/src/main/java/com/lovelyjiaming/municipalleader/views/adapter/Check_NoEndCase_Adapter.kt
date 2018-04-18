package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.LinearLayout
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.R.id.ll_check_noend_notime
import com.lovelyjiaming.municipalleader.utils.AutoUtils

class Check_NoEndCase_Adapter(val ctx: Context) : RecyclerView.Adapter<Check_NoEndCase_Adapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_check_noend_case, parent, false))

    override fun getItemCount(): Int = 30


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll_check_noend_notime by lazy {
            itemView.findViewById<LinearLayout>(R.id.ll_check_noend_notime)
        }

        init {
            AutoUtils.auto(itemView)
        }
    }
}