package com.girish.openpr.view

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.setVariable(BR.pr, item)
        binding.executePendingBindings()
    }

    companion object {
        @JvmStatic @BindingAdapter("imageUrl")
        fun loadImage(imageView: ImageView, url: String) {
            Picasso.get().load(url).into(imageView)
        }
    }
}