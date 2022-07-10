package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.data.remote.utils.Constants.HTTPS
import com.bekhruz.weatherforecast.databinding.ItemSixteenDayDetailsBinding
import com.bekhruz.weatherforecast.domain.models.sixteendayweather.DailyForecast
import com.bekhruz.weatherforecast.presentation.utils.Icons.getIconsOfSixteenDayData

class SixteenDayDetailsAdapter :
    ListAdapter<DailyForecast, SixteenDayDetailsAdapter.SixteenDayDetailsViewHolder>(DiffCallBack) {

    class SixteenDayDetailsViewHolder(private val binding: ItemSixteenDayDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DailyForecast) {
            binding.apply {
                weekDaysTextview.text = data.time
                iconWeekdaysStatus.load(getIconsOfSixteenDayData(data.icon)
                    .toUri().buildUpon().scheme(HTTPS).build()
                )
                rainStatusWeekdaysTextview.text = data.rainStatus
                lowTempWeekdaysTextview.text = data.minTemp
                highTempWeekdaysTextview.text = data.maxTemp
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SixteenDayDetailsViewHolder {
        return SixteenDayDetailsViewHolder(
            ItemSixteenDayDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: SixteenDayDetailsViewHolder, position: Int) {
        val elementOfSevenDayDetails = getItem(position)
        holder.bind(elementOfSevenDayDetails)
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.icon == newItem.icon
        }
    }
}