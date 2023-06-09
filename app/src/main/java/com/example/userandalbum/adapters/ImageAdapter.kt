package com.example.userandalbum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userandalbum.databinding.ItemImageDetailsPreviewBinding
import com.example.userandalbum.models.ImageDetails

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageDetailsPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageDetails = differ.currentList[position]
        holder.apply {
            Glide.with(this.itemView).load(imageDetails.thumbnailUrl).into(binding.ivImage)
            binding.apply {

                tvTitle.text = imageDetails.title ?: "Title"

                ivImage.setOnClickListener {
                    onItemClickListener?.let { it(imageDetails) }
                }
            }
        }
    }

    private var onItemClickListener: ((ImageDetails) -> Unit)? = null

    fun setOnItemClickListener(listener: (ImageDetails) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ImageDetails>() {
        override fun areItemsTheSame(oldItem: ImageDetails, newItem: ImageDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ImageDetails, newItem: ImageDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    inner class ImageViewHolder(val binding: ItemImageDetailsPreviewBinding) : RecyclerView.ViewHolder(binding.root)
}