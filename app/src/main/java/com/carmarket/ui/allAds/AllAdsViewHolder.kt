package com.carmarket.ui.allAds

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carmarket.R
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.model.responseBody.AdResponseBody

class AllAdsViewHolder(
    private val binding: AllAdsCardBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(data: AdResponseBody, clickListener: (Long) -> Unit) {
        binding.adPriceTextView.text = binding.adPriceTextView.context.getString(R.string.price, data.price)
        binding.adBrandTextView.text = data.brand
        binding.adModelTextView.text = data.model
        binding.adIdTextView.text = data.id.toString()
        binding.adFuelTextView.text = data.fuel
        binding.adEngineCubicTextView.text = binding.adEngineCubicTextView.context.getString(R.string.cubic, data.engineCubic)
        binding.adEngineHpTextView.text = binding.adEngineHpTextView.context.getString(R.string.ks, data.engineHp)
        binding.adMileageTextView.text = binding.adMileageTextView.context.getString(R.string.km, data.mileage)
        binding.adDriveTextView.text = data.drive
        binding.adYearTextView.text = binding.adYearTextView.context.getString(R.string.year, data.year)
        binding.adLocationTextView.text = data.city

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