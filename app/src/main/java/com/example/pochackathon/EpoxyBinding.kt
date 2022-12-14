package com.example.pochackathon

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(
    value = [
        "imageUrl"
    ],
    requireAll = true
)
fun imageUrl(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context).load(imageUrl).into(imageView)
}
