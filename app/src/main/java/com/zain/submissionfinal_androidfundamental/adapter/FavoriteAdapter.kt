package com.zain.submissionfinal_androidfundamental.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zain.submissionfinal_androidfundamental.R
import com.zain.submissionfinal_androidfundamental.room.database.Favorite
import com.zain.submissionfinal_androidfundamental.ui.DetailActivity

class FavoriteAdapter(private val listFavorite: List<Favorite>): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_fav, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listFavorite[position]
        holder.bind(currentItem)

        holder.btnDelete.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[holder.position])
        }

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.KEY_USER, currentItem.username)
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    override fun getItemCount(): Int = listFavorite.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgPhoto: ImageView = view.findViewById(R.id.photo_profil)
        val tvName: TextView = view.findViewById(R.id.txtUsername)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)

        fun bind(favorite: Favorite) {
            tvName.text = favorite.username
            Glide.with(imgPhoto.context).load(favorite.avatarUrl).into(imgPhoto)
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Favorite)
    }
}