package com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote

import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.OnErrorType
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventsResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.FaultErrorResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.GenericErrorResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.QueryErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import javax.inject.Inject

class RemoteStorage @Inject constructor(private val ticketMasterAPIService: TicketMasterAPIService) {

    private val gson: Gson = Gson()

    suspend fun loadEvents(pageSize: Int): RequestResponse =
        validateResponse(ticketMasterAPIService.loadEvents(pageSize))

    private fun validateResponse(response: Response<EventsResponse>): RequestResponse =
        if (!response.isSuccessful) { // Failure case
            handleError(response)
        } else {
            RequestResponse.OnSuccess(response.body()!!)
        }

    private fun handleError(response: Response<EventsResponse>): RequestResponse {
        val code = response.code()
        return response.errorBody()?.let { body ->
            try {
                when (code) {
                    400 -> { // Bad Request
                        val parse = gson.fromJson(body.string(), QueryErrorResponse::class.java)
                        RequestResponse.OnError(OnErrorType.OnQueryError(parse))
                    }
                    401 -> { // Unauthorized
                        val parse = gson.fromJson(body.string(), FaultErrorResponse::class.java)
                        RequestResponse.OnError(OnErrorType.OnFaultError(parse))
                    }
                    else -> {
                        val parse = gson.fromJson(body.string(), GenericErrorResponse::class.java)
                        RequestResponse.OnError(OnErrorType.OnGenericError(parse))
                    }
                }
            } catch (e: JsonSyntaxException) {
                RequestResponse.createGenericError(code, "JsonSyntaxException", e.message)
            }
        } ?: RequestResponse.createGenericError(code, "Error", "Error Body Null")
    }
}