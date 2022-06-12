package com.capstone.smaily.adapter

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.smaily.databinding.ItemLinkBinding
import com.capstone.smaily.network.ApiConfig
import com.capstone.smaily.preferences.ParentLoginPref
import com.capstone.smaily.response.DeleteUrlResponse
import com.capstone.smaily.response.UrlResponse
import com.capstone.smaily.ui.parent.MainParentActivity
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

                    val viewModel = ApiConfig.getApiService().deleteUrl(id, url, accessToken)
                    viewModel.enqueue(object : Callback<DeleteUrlResponse> {
                        @RequiresApi(Build.VERSION_CODES.R)
                        override fun onResponse(
                            call: Call<DeleteUrlResponse>,
                            response: Response<DeleteUrlResponse>
                        ) {
                            if (response.isSuccessful){
                                Log.d("Succes delete", response.body().toString())
                                Toast.makeText(itemView.context, "Delete success", Toast.LENGTH_LONG).show()
                                itemView.context.startActivity(Intent(itemView.context, MainParentActivity::class.java))
                            } else{
                                Log.d("Error delete", response.message())
                            }
                        }

                        override fun onFailure(call: Call<DeleteUrlResponse>, t: Throwable) {
                            Log.d("Failure", t.message.toString())
                        }

                    })
                }
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