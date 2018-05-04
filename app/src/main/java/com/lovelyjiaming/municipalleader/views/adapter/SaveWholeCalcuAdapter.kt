package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils

class SaveWholeCalcuAdapter(private val ctx: Context) : RecyclerView.Adapter<SaveWholeCalcuAdapter.ViewHolder>() {
    val listData: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_save_whole_calcu, parent, false))

    override fun getItemCount(): Int = listData?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val array = listData?.get(position)?.split("@")
        holder.save_whole_calc_title1.text = array?.get(0)
        holder.save_whole_calc_title2.text = array?.get(1)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val save_whole_calc_title1: TextView by lazy {
            itemView.findViewById(R.id.save_whole_calc_title1) as TextView
        }
        val save_whole_calc_title2: TextView by lazy {
            itemView.findViewById(R.id.save_whole_calc_title2) as TextView
        }
    }
}