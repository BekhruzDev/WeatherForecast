package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.databinding.ItemHourlyDetailsBinding
import com.bekhruz.weatherforecast.domain.models.Hourly
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat.getTime
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType.*
class HourlyDetailsAdapter :
    ListAdapter<Hourly, HourlyDetailsAdapter.HourlyDetailsViewHolder>(DiffCallback) {

    class HourlyDetailsViewHolder(
        private val binding: ItemHourlyDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hourly) {
            binding.apply {
                hourTextview.text = getTime(data.timeEpoch.toLong(), time
                )
                icHourlyStatus.load(
                    data.icon.toUri().buildUpon().scheme("https").build()
                )
                hourlyTemperature.text = data.tempC.toString()
                chanceOfRainTextview.text =
                    String.format("%s%% Rain", data.chanceOfRain.toString())
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
            return oldItem.timeEpoch == newItem.timeEpoch
        }
    }
}