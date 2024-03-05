package com.carmarket.ui.allAds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.model.responseBody.AdResponseBody

class AllAdsAdapter(private var list: List<AdResponseBody>) :
    RecyclerView.Adapter<AllAdsViewHolder>() {

    fun setAdData(list: List<AdResponseBody>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAdsViewHolder {
        val binding = AllAdsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllAdsViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AllAdsViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

}