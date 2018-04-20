package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

class EngineerSameAllAdapter constructor(private val ctx: Context) : RecyclerView.Adapter<EngineerSameAllAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_engineer_same_all, parent, false))

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }
    }
}