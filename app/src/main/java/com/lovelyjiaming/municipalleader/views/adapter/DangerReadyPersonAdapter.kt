package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EmergencyOndutyMemberItem

class DangerReadyPersonAdapter constructor(private val ctx: Context) : RecyclerView.Adapter<DangerReadyPersonAdapter.ViewHolder>() {
    var listData: List<EmergencyOndutyMemberItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_danger_ready_person, parent, false))

    override fun getItemCount(): Int = listData?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData?.let {
            holder.item_danger_ready_personname.text = listData?.get(position)?.Name
            holder.item_danger_ready_personjob.text = listData?.get(position)?.Job
            holder.item_danger_ready_personphone.text = listData?.get(position)?.Telephone
            holder.item_danger_ready_personsex.text = listData?.get(position)?.Male
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
        }

        val item_danger_ready_personname: TextView by lazy {
            itemView.findViewById(R.id.item_danger_ready_personname) as TextView
        }
        val item_danger_ready_personsex: TextView by lazy {
            itemView.findViewById(R.id.item_danger_ready_personsex) as TextView
        }
        val item_danger_ready_personjob: TextView by lazy {
            itemView.findViewById(R.id.item_danger_ready_personjob) as TextView
        }
        val item_danger_ready_personphone: TextView by lazy {
            itemView.findViewById(R.id.item_danger_ready_personphone) as TextView
        }
    }
}