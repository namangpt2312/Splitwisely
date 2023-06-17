package com.example.splitwisely.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.splitwisely.R
import com.example.splitwisely.models.Expense
import com.example.splitwisely.models.Group
import com.example.splitwisely.viewHolders.ExpenseViewHolder
import com.example.splitwisely.viewHolders.GroupViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ExpenseAdapter(options: FirestoreRecyclerOptions<Expense>, val listener: IExpenseAdapter) : FirestoreRecyclerAdapter<Expense, ExpenseViewHolder>(
    options
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val viewHolder =  ExpenseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_expense, parent, false))
        viewHolder.itemView.setOnClickListener{
            listener.onItemClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int, model: Expense) {
        holder.expenseImgView.setImageResource(R.drawable.defaultavatar)
        holder.titleTv.text = ""

        holder.titleTv.text = model.name
        Glide.with(holder.expenseImgView.context).load(model.imageUrl)
            .placeholder(R.drawable.defaultavatar)
            .error(R.drawable.defaultavatar)
            .circleCrop()
            .into(holder.expenseImgView)
    }
}

interface IExpenseAdapter {
    fun onItemClicked(expenseId: String)
}