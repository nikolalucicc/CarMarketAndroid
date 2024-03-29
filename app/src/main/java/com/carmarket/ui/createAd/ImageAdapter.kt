package com.carmarket.ui.createAd

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.carmarket.R

class ImageAdapter(private val context: Context, private val images: MutableList<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.create_ad_item_image, parent, false)
        return ImageViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position]
        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    holder.deleteButton.visibility = View.GONE
                }
                MotionEvent.ACTION_DOWN -> {
                    holder.deleteButton.visibility = View.VISIBLE
                }
            }

            val isWithinImageBounds = motionEvent.x > 0 && motionEvent.x < holder.imageView.width &&
                    motionEvent.y > 0 && motionEvent.y < holder.imageView.height

            if (!isWithinImageBounds) {
                holder.deleteButton.visibility = View.GONE
            }

            false
        }

        holder.imageView.setOnLongClickListener {
            holder.deleteButton.visibility = View.VISIBLE
            true
        }

        holder.deleteButton.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                images.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagesCreateAdImageView)
        val deleteButton: ImageButton = itemView.findViewById(R.id.deleteImageButton)

        init {
            imageView.setOnLongClickListener {
                deleteButton.visibility = View.VISIBLE
                true
            }

            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    images.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }

}
