package com.beer.product.ui.productList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.beer.product.data.dto.Product
import com.example.product.databinding.ListProductBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class ProductListAdapter @Inject constructor(val clickListener: ClickListener) :
    ListAdapter<Product, ProductListAdapter.ViewHolder>(ProductListDiffCallback()) {

    class ViewHolder(val binding: ListProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product, clickListener: ClickListener) {
            binding.beerName.text = item.name
            binding.tagline.text = item.tagline
            binding.beerImage.load(item.image_url)
            binding.root.setOnClickListener {
                clickListener.onClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListProductBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beerlist = getItem(position)
        holder.bind(beerlist, clickListener)
    }

    class ProductListDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}

class ClickListener @Inject constructor() {

    var onItemClick: ((Product) -> Unit)? = null

    fun onClick(data: Product) {
        onItemClick?.invoke(data)
    }
}
