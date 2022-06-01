package com.bekhruz.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bekhruz.weatherforecast.databinding.ItemSearchedCityBinding
import com.bekhruz.weatherforecast.data.network.geocoding.Result

class SearchedLocationsAdapter(private val onItemClick:(Result) -> Unit):ListAdapter<Result, SearchedLocationsAdapter.SearchedLocationsViewHolder>(DiffCall) {
    class SearchedLocationsViewHolder(private val binding: ItemSearchedCityBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:Result){
            binding.apply {
                searchedLocationTextview.text = data.name ?: data.address_line1
                searchedLocationInfoTextview.text = String.format("%s, %s",data.address_line1,data.country)
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
    companion object DiffCall : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.city == newItem.city
        }
    }
}