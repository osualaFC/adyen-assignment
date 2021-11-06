package com.adyen.android.assignment.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adyen.android.assignment.databinding.VenuesItemBinding
import com.adyen.android.assignment.domain.model.Venue
import com.adyen.android.assignment.presentation.util.GoogleMapNavigationHelper
import java.lang.StringBuilder

class VenueAdapter(private val context: Context) : ListAdapter<Venue, VenueAdapter.VenueViewHolder>(
    ITEM_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val binding = VenuesItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return VenueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val item: Venue= getItem(position)
        holder.bind(item)
    }

    inner class VenueViewHolder(
        private val binding: VenuesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Venue) {
            val localAddress = StringBuilder()
                .append(item.address?.get(0) ?: "")
                .append("\n")
                .append(item.address?.get(1) ?: "")
                .toString()
            with(binding) {
                name.text = item.name
                address.text = localAddress
                distance.text = "${item.distance/1000}km"
                btnGetDirection.setOnClickListener {
                    GoogleMapNavigationHelper.navigateUsingMaps(context, item.latitude, item.longitude)
                }
            }
        }
    }

}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Venue>() {
    override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Venue, newItem: Venue  ): Boolean {
        return oldItem == newItem
    }
}