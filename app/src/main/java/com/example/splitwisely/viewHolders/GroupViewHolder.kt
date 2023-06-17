package com.example.splitwisely.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwisely.R

class GroupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val groupImgView: ImageView = itemView.findViewById(R.id.groupImgView)
    val titleTv: TextView = itemView.findViewById(R.id.titleTv)
}