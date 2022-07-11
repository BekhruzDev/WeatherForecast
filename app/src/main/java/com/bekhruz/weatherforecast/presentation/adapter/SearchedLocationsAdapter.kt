package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekhruz.weatherforecast.databinding.ItemSearchedCityBinding
import com.bekhruz.weatherforecast.domain.models.geocoding.LocationResult
import com.bekhruz.weatherforecast.presentation.utils.RecyclerViewItemClick

class SearchedLocationsAdapter(private val recyclerViewItemClick: RecyclerViewItemClick<LocationResult>):ListAdapter<LocationResult, SearchedLocationsAdapter.SearchedLocationsViewHolder>(DiffCall) {
    class SearchedLocationsViewHolder(private val binding: ItemSearchedCityBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: LocationResult){
            binding.apply {
                searchedLocationTextview.text = data.locationName
                searchedLocationInfoTextview.text = data.locationInfo
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
            recyclerViewItemClick.onItemClicked(item)
        }
        holder.bind(item)
    }


    companion object DiffCall : DiffUtil.ItemCallback<LocationResult>() {
        override fun areItemsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
            return oldItem.locationName == newItem.locationName
        }
    }
}