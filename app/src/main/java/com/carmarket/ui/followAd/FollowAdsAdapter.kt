package com.carmarket.ui.followAd

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.model.responseBody.FollowAdResponseBody

class FollowAdsAdapter(
    private var list: List<FollowAdResponseBody>,
    private val onItemClick: (Long, Long) -> Unit
) : RecyclerView.Adapter<FollowAdsViewHolder>() {

    fun setAdData(list: List<FollowAdResponseBody>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAdsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = AllAdsCardBinding.inflate(inflater, parent, false)
        return FollowAdsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FollowAdsViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data, onItemClick)
    }


}