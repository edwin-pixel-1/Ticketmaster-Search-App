package com.edwin.ticketmaster_searchapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edwin.ticketmaster_searchapp.databinding.EventItemBinding
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.Event

class EventsListAdapter :
    ListAdapter<Event, EventsListAdapter.EventViewHolder>(object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem.id == newItem.id
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(EventItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
        holder.bind(getItem(position))

    class EventViewHolder(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Event) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

}