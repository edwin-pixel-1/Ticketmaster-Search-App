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

    /**
     * This method is the responsible to decided what source should be taken the data. remotedSource or localSource
     * @param pageSize: This parameter is optional, and define the max number of items to load.
     * TODO: In the future, the @param pageSize can be loaded as part of a Global App Configuration
     */
    suspend fun loadEvents(pageSize: Int = 100): RequestResponse = withContext(Dispatchers.IO) {
        if (networkManager.isConnected()) {
            remoteStorage.loadEvents(pageSize).apply {
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
        const val LAST_QUERY_EVENTS_KEY = "LAST_QUERY_EVENTS"
    }

}