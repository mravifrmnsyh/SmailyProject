package com.capstone.smaily.adapter

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.smaily.databinding.ItemLinkBinding
import com.capstone.smaily.network.ApiConfig
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.response.DeleteUrlResponse
import com.capstone.smaily.response.ParentUrlResponse
import com.capstone.smaily.response.UrlResponse
import com.capstone.smaily.ui.parent.BlokLinkParentActivity
import com.capstone.smaily.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrlAdapter: ListAdapter<UrlResponse, UrlAdapter.UrlViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UrlViewHolder {
        val binding = ItemLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UrlViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UrlViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class UrlViewHolder(private val binding: ItemLinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UrlResponse) {
            with(binding) {
                tvLink.text = data.url.toString()
                ivDeletLink.setOnClickListener {
                    val loginPref = itemView.context.getSharedPreferences(ParentLoginPref.PARENT_LOGIN, MODE_PRIVATE)
                    val id = loginPref.getString(ParentLoginPref.ID, null).toString()
                    val url = data.url.toString()
                    val accessToken = loginPref.getString(ParentLoginPref.ACCESSTOKEN, null).toString()


                    Log.d("url", data.url.toString())

                    val viewModel = ApiConfig.getApiService().deleteUrl(id, url, accessToken)
                    viewModel.enqueue(object : Callback<DeleteUrlResponse> {
                        override fun onResponse(
                            call: Call<DeleteUrlResponse>,
                            response: Response<DeleteUrlResponse>
                        ) {
                            if (response.isSuccessful){
                                Log.d("Succes delete", response.body().toString())
                                Log.d("Succes delete message", response.message())
                            } else{
                                Log.d("Error delete", response.message())
                            }
                        }

                        override fun onFailure(call: Call<DeleteUrlResponse>, t: Throwable) {
                            Log.d("Failure", t.message.toString())
                        }

                    })
                    //delete link
//                    val deletLink = ApiConfig.getApiService().deleteUrl()
                    Log.d("klik", data.url.toString())
                }
            }
            binding.root.setOnClickListener {
//                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.NAME, data.name)
//                intent.putExtra(DetailStoryActivity.PHOTOURL, data.photoUrl)
//                intent.putExtra(DetailStoryActivity.DESCRIPTION, data.description)
//                intent.putExtra(DetailStoryActivity.DATE, data.createdAt)
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        Pair(binding.imgItemPhoto, "iv_stories"),
//                        Pair(binding.tvItemUname, "tv_name"),
//                        Pair(binding.tvItemDate, "tv_date"),
//                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UrlResponse>() {
            override fun areItemsTheSame(oldItem: UrlResponse, newItem: UrlResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UrlResponse, newItem: UrlResponse): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
}