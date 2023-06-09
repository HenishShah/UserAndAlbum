package com.example.userandalbum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.userandalbum.databinding.ItemUserDetailsPreviewBinding
import com.example.userandalbum.models.UserDetails

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserDetailsPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userDetails = differ.currentList[position]
        holder.apply {
            //Glide.with(this).load(userDetails.urlToImage).into(ivArticleImage)
            binding.apply {
                tvId.text = userDetails.id.toString()
                tvUserName.text = userDetails.username

                tvUserName.setOnClickListener {
                    onItemClickListener?.let { it(userDetails) }
                }

                tvId.setOnClickListener {
                    onItemClickListener?.let { it(userDetails) }
                }
            }
        }
    }

    private var onItemClickListener: ((UserDetails) -> Unit)? = null

    fun setOnItemClickListener(listener: (UserDetails) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<UserDetails>() {
        override fun areItemsTheSame(oldItem: UserDetails, newItem: UserDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserDetails, newItem: UserDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class UserViewHolder(val binding: ItemUserDetailsPreviewBinding) : RecyclerView.ViewHolder(binding.root)
}