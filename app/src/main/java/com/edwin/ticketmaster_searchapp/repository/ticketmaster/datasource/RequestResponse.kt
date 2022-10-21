package com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource

import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.EventsResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.FaultErrorResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.GenericErrorResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.QueryErrorResponse
import kotlinx.serialization.Serializable

@Serializable
sealed class RequestResponse {

    @Serializable
    data class OnSuccess(val response: EventsResponse) : RequestResponse()

    @Serializable
    data class OnError(val error: OnErrorType) : RequestResponse()

    companion object {
        val networkError =
            OnError(OnErrorType.OnGenericError(GenericErrorResponse(error = "Network Error",
                description = "Not Internet Connection")))

        fun createGenericError(code: Int?, error: String?, description: String?): OnError = OnError(
            OnErrorType.OnGenericError(GenericErrorResponse(code, error, description))
        )
    }
}

@Serializable
sealed class OnErrorType {
    @Serializable
    data class OnFaultError(val error: FaultErrorResponse) : OnErrorType()

    @Serializable
    data class OnQueryError(val error: QueryErrorResponse) : OnErrorType()

    @Serializable
    data class OnGenericError(val error: GenericErrorResponse) : OnErrorType()
}