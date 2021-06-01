package com.jetpack.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jetpack.databinding.LayoutRowBreweryItemBinding
import com.jetpack.ui.common.BaseViewHolder
import com.rajat.domain.model.Breweries

class SearchAdapter : ListAdapter<Breweries, SearchAdapter.SearchViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Breweries>() {
            override fun areItemsTheSame(oldItem: Breweries, newItem: Breweries): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Breweries, newItem: Breweries): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val viewBinding = LayoutRowBreweryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchViewHolder(private val viewBinding: LayoutRowBreweryItemBinding) :
        BaseViewHolder<Breweries>(viewBinding.root) {
        override fun bind(data: Breweries) {
            viewBinding.tvBreweryName.text = data.name.orEmpty()
        }
    }
}