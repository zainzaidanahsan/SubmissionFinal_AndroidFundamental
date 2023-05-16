package com.zain.submissionfinal_androidfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.response.ItemsUser
import com.zain.submissionfinal_androidfundamental.ui.DetailActivity

class UserAdapter(private val listUser: List<ItemsUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list_user, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listUser[position]
        holder.tvName.text = item.login
        Glide.with(holder.imgPhoto.context).load(item.avatarUrl).into(holder.imgPhoto)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.KEY_USER, item.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgPhoto: ImageView = view.findViewById(R.id.photo_profil)
        val tvName: TextView = view.findViewById(R.id.username)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsUser)
    }
}