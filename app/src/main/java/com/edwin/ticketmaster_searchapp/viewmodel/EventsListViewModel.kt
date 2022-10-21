package com.edwin.ticketmaster_searchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.TicketMasterRepository
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.OnErrorType
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsListViewModel @Inject constructor(private val ticketMasterRepository: TicketMasterRepository) :
    ViewModel() {

    private val _eventsListData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsListData: LiveData<List<Event>>
        get() = _eventsListData

    fun loadEvents() {
        viewModelScope.launch {
            when (val requestResponse = ticketMasterRepository.loadEvents()) {
                is RequestResponse.OnSuccess -> {
                    requestResponse.response.content?.events?.apply {
                        _eventsListData.value = this
                    }
                }
                is RequestResponse.OnError -> {
                    _eventsListData.value = listOf()
                    when (requestResponse.error) {
                        is OnErrorType.OnFaultError -> {
                            //TODO: Update UI, Show alert dialog or similar
                        }
                        is OnErrorType.OnQueryError -> {
                            //TODO: Update UI, Show alert dialog or similar
                        }
                        is OnErrorType.OnGenericError -> {
                            //TODO: Update UI, Show alert dialog or similar
                        }
                    }
                }
            }
        }
    }

}