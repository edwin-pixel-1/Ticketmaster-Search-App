package com.edwin.ticketmaster_searchapp.repository.ticketmaster

import com.edwin.ticketmaster_searchapp.manager.NetworkManager
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local.LocalStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote.RemoteStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TicketMasterRepository @Inject constructor(
    private val remoteStorage: RemoteStorage,
    private val localStorage: LocalStorage,
    private val networkManager: NetworkManager,
) {

    suspend fun loadEvents(): RequestResponse = withContext(Dispatchers.IO) {
        if (networkManager.isConnected()) {
            remoteStorage.loadEvents(100).apply {
                if (this is RequestResponse.OnSuccess) {
                    localStorage.putSerializedData(LAST_QUERY_EVENTS_KEY, response)
                }
            }
        } else {
            localStorage.getSerializedData(LAST_QUERY_EVENTS_KEY, EventsResponse::class.java)?.let {
                RequestResponse.OnSuccess(it)
            } ?: RequestResponse.networkError
        }
    }

    companion object {
        private const val LAST_QUERY_EVENTS_KEY = "LAST_QUERY_EVENTS"
    }

}