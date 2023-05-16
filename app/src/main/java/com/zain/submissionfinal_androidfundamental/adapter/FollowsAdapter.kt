package com.zain.submissionfinal_androidfundamental.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.response.FollowersResponseItem
import com.zain.submissionfinal_androidfundamental.response.FollowingResponseItem

class FollowsAdapter(private val listFollow: List<Any>): RecyclerView.Adapter<FollowsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listFollow[position]

        if (currentItem is FollowersResponseItem) {
            holder.tvName.text = currentItem.login
            Glide.with(holder.imgPhoto.context).load(currentItem.avatarUrl).into(holder.imgPhoto)
        } else if (currentItem is FollowingResponseItem) {
            holder.tvName.text = currentItem.login
            Glide.with(holder.imgPhoto.context).load(currentItem.avatarUrl).into(holder.imgPhoto)
        }
    }

    override fun getItemCount(): Int = listFollow.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgPhoto: ImageView = view.findViewById(R.id.photo_profil)
        val tvName: TextView = view.findViewById(R.id.username)
    }
}