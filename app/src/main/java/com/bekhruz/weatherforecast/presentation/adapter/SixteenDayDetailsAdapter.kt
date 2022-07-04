package com.bekhruz.weatherforecast.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bekhruz.weatherforecast.databinding.ItemSixteenDayDetailsBinding
import com.bekhruz.weatherforecast.domain.models.SixteenDayData
import com.bekhruz.weatherforecast.presentation.utils.Icons.getIconsOfSixteenDayData
import com.bekhruz.weatherforecast.presentation.utils.TimeFormat.getTime
import com.bekhruz.weatherforecast.presentation.utils.TimeFormattingType.*

class SixteenDayDetailsAdapter :
    ListAdapter<SixteenDayData, SixteenDayDetailsAdapter.SixteenDayDetailsViewHolder>(DiffCallBack) {

    class SixteenDayDetailsViewHolder(private val binding: ItemSixteenDayDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SixteenDayData) {
            binding.apply {
                weekDaysTextview.text = getTime(
                    data.timeEpoch.toLong(),
                    date
                )
                iconWeekdaysStatus.load(
                    getIconsOfSixteenDayData(data.icon).toUri().buildUpon()
                        .scheme("https").build()
                ) {
                    //TODO: ADD PLACEHOLDER, ERROR HANDLING FOR COIL
                }
                rainStatusWeekdaysTextview.text =
                    String.format("%d%% rain", data.rainStatus)
                lowTempWeekdaysTextview.text = data.minTemp.toString()
                highTempWeekdaysTextview.text = String.format(" / %s", data.maxTemp.toString())
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

    companion object DiffCallBack : DiffUtil.ItemCallback<SixteenDayData>() {
        override fun areItemsTheSame(oldItem: SixteenDayData, newItem: SixteenDayData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SixteenDayData, newItem: SixteenDayData): Boolean {
            return oldItem.icon == newItem.icon
        }
    }
}