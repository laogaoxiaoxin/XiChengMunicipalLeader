package com.lovelyjiaming.municipalleader.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.views.fragments.danger.EmergencyWorkerItem


class DangerReadyPersonAdapter constructor(private val ctx: Context) : RecyclerView.Adapter<DangerReadyPersonAdapter.ViewHolder>() {
    private var listData: List<EmergencyWorkerItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_danger_ready_person, parent, false))

    override fun getItemCount(): Int = listData?.size ?: 0

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listData?.let {
            holder.item_danger_ready_personname.text = it[position].name
            holder.item_danger_ready_personjob.text = if (it[position].remarks == "null") "无" else it[position].remarks
            holder.item_danger_ready_personphone.text = if (it[position].phone == "null") "无" else it[position].phone
            holder.item_danger_ready_personsex.text = if (it[position].card == "null") "无" else it[position].card
            //打电话
            holder.item_danger_ready_personphone.setOnClickListener {
                if ((it as TextView).text.toString() == "无") return@setOnClickListener
                val phoneNum = it.text.toString().substring(0, 11)
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum))
                ctx.startActivity(intent)
            }
        }
    }

    fun setData(listData: List<EmergencyWorkerItem>?) {
        this.listData = listData
        notifyDataSetChanged()
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