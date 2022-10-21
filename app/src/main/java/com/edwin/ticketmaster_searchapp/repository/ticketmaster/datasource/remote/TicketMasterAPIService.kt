package com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote

import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketMasterAPIService {

    @GET("events")
    suspend fun loadEvents(@Query("size") pageSize: Int): Response<EventsResponse>

}