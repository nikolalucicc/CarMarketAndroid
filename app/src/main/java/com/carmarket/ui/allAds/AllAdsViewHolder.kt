package com.carmarket.ui.allAds

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.model.responseBody.AdResponseBody

class AllAdsViewHolder(
    private val binding: AllAdsCardBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(data: AdResponseBody, clickListener: (Long) -> Unit) {
        binding.adPriceTextView.text = data.price.toString()
        binding.adBrandTextView.text = data.brand
        binding.adModelTextView.text = data.model
        binding.adYearTextView.text = data.year.toString()
        binding.adMileageTextView.text = data.mileage.toString()
        binding.adEngineHpTextView.text = data.engineHp.toString()
        binding.adEngineTypeTextView.text = data.engineType
        binding.adEngineCubicTextView.text = data.engineCubic.toString()
        binding.adIdTextView.text = data.id.toString()

        if (data.images.isNotEmpty()) {
            val firstImage = data.images[0]
            Glide.with(binding.adImageView.context)
                .load(firstImage.url)
                .into(binding.adImageView)
        }

        itemView.setOnClickListener {
            clickListener(data.id)
        }
    }

}