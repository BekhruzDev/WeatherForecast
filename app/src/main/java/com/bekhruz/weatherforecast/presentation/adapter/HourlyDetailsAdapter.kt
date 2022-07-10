package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.data.remote.utils.Constants.HTTPS
import com.bekhruz.weatherforecast.databinding.ItemHourlyDetailsBinding
import com.bekhruz.weatherforecast.domain.models.home.Hourly
class HourlyDetailsAdapter :
    ListAdapter<Hourly, HourlyDetailsAdapter.HourlyDetailsViewHolder>(DiffCallback) {

    class HourlyDetailsViewHolder(
        private val binding: ItemHourlyDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hourly) {
            binding.apply {
                hourTextview.text = data.time
                icHourlyStatus.load(
                    data.icon.toUri().buildUpon().scheme(HTTPS).build()
                )
                hourlyTemperature.text = data.tempC
                chanceOfRainTextview.text = data.chanceOfRain
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyDetailsViewHolder {
        return HourlyDetailsViewHolder(
            ItemHourlyDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HourlyDetailsViewHolder, position: Int) {
        val elementOfHourlyDetails = getItem(position)
        holder.bind(elementOfHourlyDetails)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Hourly>() {
        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem.time == newItem.time
        }
    }
}