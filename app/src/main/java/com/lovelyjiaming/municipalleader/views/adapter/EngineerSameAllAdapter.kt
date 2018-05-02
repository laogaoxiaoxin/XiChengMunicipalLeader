package com.lovelyjiaming.municipalleader.views.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lovelyjiaming.municipalleader.R
import com.lovelyjiaming.municipalleader.utils.AutoUtils
import com.lovelyjiaming.municipalleader.utils.EngineerItem
import com.lovelyjiaming.municipalleader.views.activitys.EngineerUsualDetailActivity

class EngineerSameAllAdapter constructor(private val ctx: Context) : RecyclerView.Adapter<EngineerSameAllAdapter.ViewHolder>() {
    var listData: List<EngineerItem>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_engineer_same_all, parent, false))
        return viewHolder
    }

    override fun getItemCount(): Int = listData?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            AutoUtils.auto(itemView)
            itemView.setOnClickListener {
                ctx.startActivity(Intent(ctx, EngineerUsualDetailActivity::class.java))
            }
        }
    }


}