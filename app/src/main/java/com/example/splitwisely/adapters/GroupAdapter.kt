package com.example.splitwisely.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.splitwisely.R
import com.example.splitwisely.models.Group
import com.example.splitwisely.viewHolders.GroupViewHolder
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class GroupAdapter(options: FirestoreRecyclerOptions<Group>, val listener: IGroupAdapter) : FirestoreRecyclerAdapter<Group, GroupViewHolder>(
    options
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val viewHolder =  GroupViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_group, parent, false))
        viewHolder.itemView.setOnClickListener{
            listener.onItemClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int, model: Group) {
        holder.groupImgView.setImageResource(R.drawable.defaultavatar)
        holder.titleTv.text = ""

        holder.titleTv.text = model.name
        Glide.with(holder.groupImgView.context).load(model.imageUrl)
            .placeholder(R.drawable.defaultavatar)
            .error(R.drawable.defaultavatar)
            .circleCrop()
            .into(holder.groupImgView)
    }
}

interface IGroupAdapter {
    fun onItemClicked(groupId: String)
}