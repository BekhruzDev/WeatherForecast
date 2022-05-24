package com.bekhruz.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.databinding.ItemHourlyDetailsBinding
import com.bekhruz.weatherforecast.network.sevenday.Hour
import com.bekhruz.weatherforecast.viewmodels.WeatherViewModel

class HourlyDetailsAdapter(private val viewModel: WeatherViewModel) :
    ListAdapter<Hour, HourlyDetailsAdapter.HourlyDetailsViewHolder>(DiffCallback) {

    class HourlyDetailsViewHolder(
        private val binding: ItemHourlyDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hour, viewModel: WeatherViewModel) {
            binding.apply {
                hourTextview.text = viewModel.getTime(data.time_epoch.toLong(), false)
                icHourlyStatus.load(
                    data.condition.icon.toUri().buildUpon().scheme("https").build()
                )
                hourlyTemperature.text = data.temp_c.toString()
                chanceOfRainTextview.text =
                    String.format("%s%% Rain", data.chance_of_rain.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyDetailsViewHolder {
        return HourlyDetailsViewHolder(
            ItemHourlyDetailsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: HourlyDetailsViewHolder, position: Int) {
        val elementOfHourlyDetails = getItem(position)
        holder.bind(elementOfHourlyDetails, viewModel)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Hour>() {
        override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
            return oldItem.time_epoch == newItem.time_epoch
        }
    }
}