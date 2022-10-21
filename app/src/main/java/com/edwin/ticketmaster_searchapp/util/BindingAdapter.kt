package com.edwin.ticketmaster_searchapp.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.Event
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventImage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventStartDate
import com.edwin.ticketmaster_searchapp.view.adapter.EventsListAdapter
import java.time.*
import java.util.*

@BindingAdapter("updateEvents")
fun RecyclerView.updateEventsList(events: List<Event>?) {
    (adapter as? EventsListAdapter)?.submitList(events)
}

@BindingAdapter("loadEventImage")
fun ImageView.loadEventImage(images: List<EventImage>) {
    images.firstOrNull {
        it.ratio == "4_3"
    }?.also {
        loadGlideImage(context, it.url, this)
    } ?: run {
        images.first().also {
            loadGlideImage(context, it.url, this)
        }
    }
}

private fun loadGlideImage(context: Context, url: String?, target: ImageView) {
    url?.apply {
        Glide.with(context).load(this).into(target)
    }
}

@BindingAdapter("formatEventDate")
fun TextView.formatEventDate(startDate: EventStartDate) {
    startDate.dateTime?.apply {
        text = DateUtil.convertDateTimeUTC2Local(this)
    }
}