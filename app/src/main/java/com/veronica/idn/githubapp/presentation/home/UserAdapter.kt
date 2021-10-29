package com.veronica.idn.githubapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veronica.idn.githubapp.util.OnItemClickCallback
import com.veronica.idn.githubapp.data.model.User
import com.veronica.idn.githubapp.databinding.ItemUserBinding

class UserAdapter(private val listUser: List<User?>?) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class UserViewHolder(var itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val users = listUser?.get(position)
        holder.itemUserBinding.tvNameItemUser.text = users?.login
        holder.itemUserBinding.tvRepoLinkItemUser.text = users?.html_url
        holder.itemUserBinding.tvTypeItemUser.text = users?.type

        Glide.with(holder.itemView.context).load(users?.avatar_url)
            .into(holder.itemUserBinding.cvItemUser)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(users) }
    }

    override fun getItemCount(): Int = listUser?.size ?: 0

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}