package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekhruz.weatherforecast.databinding.ItemSearchedCityBinding
import com.bekhruz.weatherforecast.domain.models.geocoding.SearchedLocationResults

class SearchedLocationsAdapter(private val onItemClick:(SearchedLocationResults) -> Unit):ListAdapter<SearchedLocationResults, SearchedLocationsAdapter.SearchedLocationsViewHolder>(DiffCall) {
    class SearchedLocationsViewHolder(private val binding: ItemSearchedCityBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: SearchedLocationResults){
            binding.apply {
                searchedLocationTextview.text = data.name
                avgTemperatureTextview.text = String.format("%s, %s",data.addressLine1,data.country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedLocationsViewHolder {
        return SearchedLocationsViewHolder(
            ItemSearchedCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: SearchedLocationsViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
        holder.bind(item)
    }
    companion object DiffCall : DiffUtil.ItemCallback<SearchedLocationResults>() {
        override fun areItemsTheSame(oldItem: SearchedLocationResults, newItem: SearchedLocationResults): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SearchedLocationResults, newItem: SearchedLocationResults): Boolean {
            return oldItem.country == newItem.country
        }
    }
}