package com.capstone.smaily.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.smaily.databinding.ItemAppBinding
import com.capstone.smaily.response.ParentAppResponse

class AppAdapter: ListAdapter<ParentAppResponse, AppAdapter.AppViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class AppViewHolder(private val binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ParentAppResponse) {
            with(binding) {
                tvApps.text = data.app.toString()
                switch1.setOnClickListener {
                }
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ParentAppResponse>() {
            override fun areItemsTheSame(oldItem: ParentAppResponse, newItem: ParentAppResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ParentAppResponse, newItem: ParentAppResponse): Boolean {
                return oldItem.app == newItem.app
            }
        }
    }
}