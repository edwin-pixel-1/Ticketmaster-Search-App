package com.edwin.ticketmaster_searchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.TicketMasterRepository
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.OnErrorType
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.Event
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.GenericErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsListViewModel @Inject constructor(private val ticketMasterRepository: TicketMasterRepository) :
    ViewModel() {

    private val _eventsListData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsListData: LiveData<List<Event>>
        get() = _eventsListData

    private val _screenEvent: MutableLiveData<EventsListScreenEvent?> = MutableLiveData()
    val screenEvent: LiveData<EventsListScreenEvent?>
        get() {
            val event = _screenEvent
            _screenEvent.value = null
            return event
        }


    fun loadEvents() {
        viewModelScope.launch {
            when (val requestResponse = ticketMasterRepository.loadEvents()) {
                is RequestResponse.OnSuccess -> {
                    requestResponse.response.content?.events?.apply {
                        _eventsListData.value = this
                        _screenEvent.value = EventsListScreenEvent.LoadEventsCompleted
                    }
                }
                is RequestResponse.OnError -> {
                    _eventsListData.value = listOf()
                    when (val errorType = requestResponse.error) {
                        is OnErrorType.OnFaultError -> {
                            errorType.error.fault?.let { fault ->
                                fault.description?.also { description ->
                                    fault.detail?.errorCode?.also { detail ->
                                        _screenEvent.value = EventsListScreenEvent.LoadEventsError(
                                            detail,
                                            description)
                                    }
                                }
                            } ?: handleGenericError(errorType.error)
                        }
                        is OnErrorType.OnQueryError -> {
                            errorType.error.errors?.first()?.apply {
                                this.codeId?.also { codeId ->
                                    this.detail?.also { detail ->
                                        _screenEvent.value =
                                            EventsListScreenEvent.LoadEventsError(
                                                "Error $codeId",
                                                detail)
                                    }
                                }
                            } ?: handleGenericError(errorType.error)
                        }
                        is OnErrorType.OnGenericError -> {
                            handleGenericError(errorType.error)
                        }
                    }
                }
            }
        }
    }

    private fun handleGenericError(error: GenericErrorResponse) {
        error.description?.apply {
            _screenEvent.value =
                EventsListScreenEvent.LoadEventsError(
                    "Error ${error.code ?: ""} - ${error.error}",
                    this)
        }
    }

}