package com.edwin.ticketmaster_searchapp.viewmodel

sealed class EventsListScreenEvent {

    data class LoadEventsError(val title: String, val message: String) : EventsListScreenEvent()
    object LoadEventsCompleted : EventsListScreenEvent()

}