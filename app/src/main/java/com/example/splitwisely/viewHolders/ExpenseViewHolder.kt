package com.example.splitwisely.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitwisely.R

class ExpenseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val expenseImgView: ImageView = itemView.findViewById(R.id.expenseImgView)
    val titleTv: TextView = itemView.findViewById(R.id.titleTv)
    val timeTv: TextView = itemView.findViewById(R.id.timeTv)
    val subtitleTv: TextView = itemView.findViewById(R.id.subtitleTv)
    val countTv: TextView = itemView.findViewById(R.id.countTv)
}