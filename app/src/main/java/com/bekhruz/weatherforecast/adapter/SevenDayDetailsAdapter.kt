package com.bekhruz.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.databinding.ItemSevenDayDetailsBinding
import com.bekhruz.weatherforecast.network.sevenday.Forecastday
import com.bekhruz.weatherforecast.viewmodels.WeatherViewModel

class SevenDayDetailsAdapter(private val viewModel: WeatherViewModel) :
    ListAdapter<Forecastday, SevenDayDetailsAdapter.SevenDayDetailsViewHolder>(DiffCallBack) {
    class SevenDayDetailsViewHolder(private val binding: ItemSevenDayDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Forecastday, viewModel: WeatherViewModel) {
            binding.apply {
                weekDaysTextview.text = viewModel.getTime(data.date_epoch.toLong(), "weekday")
                iconWeekdaysStatus.load(
                    data.day.condition.icon.toUri().buildUpon().scheme("https").build()
                ) {
                    //TODO: ADD PLACEHOLDER, ERRORHANDLING FOR COIL
                }
                rainStatusWeekdaysTextview.text =
                    String.format("%d%% rain", data.day.daily_chance_of_rain)
                lowTempWeekdaysTextview.text = data.day.mintemp_c.toString()
                highTempWeekdaysTextview.text = data.day.maxtemp_c.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SevenDayDetailsViewHolder {
        return SevenDayDetailsViewHolder(
            ItemSevenDayDetailsBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SevenDayDetailsViewHolder, position: Int) {
        val elementOfSevenDayDetails = getItem(position)
        holder.bind(elementOfSevenDayDetails, viewModel)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem.date == newItem.date
        }
    }
}