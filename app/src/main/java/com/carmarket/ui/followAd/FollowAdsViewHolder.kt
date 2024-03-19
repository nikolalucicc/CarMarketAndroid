package com.carmarket.ui.followAd

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carmarket.R
import com.carmarket.databinding.AllAdsCardBinding
import com.carmarket.model.responseBody.FollowAdResponseBody

class FollowAdsViewHolder(
    private val binding: AllAdsCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: FollowAdResponseBody, clickListener: (Long, Long) -> Unit) {
        binding.adPriceTextView.text = binding.adPriceTextView.context.getString(R.string.price, data.ad.price)
        binding.adBrandTextView.text = data.ad.brand
        binding.adModelTextView.text = data.ad.model
        binding.adIdTextView.text = data.ad.id.toString()
        binding.followedAdIdTextView.text = data.id.toString()
        binding.adFuelTextView.text = data.ad.fuel
        binding.adEngineCubicTextView.text = binding.adEngineCubicTextView.context.getString(R.string.cubic, data.ad.engineCubic)
        binding.adEngineHpTextView.text = binding.adEngineHpTextView.context.getString(R.string.ks, data.ad.engineHp)
        binding.adMileageTextView.text = binding.adMileageTextView.context.getString(R.string.km, data.ad.mileage)
        binding.adDriveTextView.text = data.ad.drive
        binding.adYearTextView.text = binding.adYearTextView.context.getString(R.string.year, data.ad.year)
        binding.adLocationTextView.text = data.ad.city

        if (data.ad.images.isNotEmpty()) {
            val firstImage = data.ad.images[0]
            Glide.with(binding.adImageView.context)
                .load(firstImage.url)
                .into(binding.adImageView)
        }

        itemView.setOnClickListener {
            clickListener(data.ad.id, data.id)
        }
    }

}