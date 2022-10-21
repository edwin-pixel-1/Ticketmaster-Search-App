package com.edwin.ticketmaster_searchapp.repository.ticketmaster.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(
    @SerializedName("_embedded")
    val content: EventsList? = null
)

@Serializable
data class EventsList(
    @SerializedName("events")
    val events: List<Event>? = null
)

@Serializable
data class Event(

    @SerializedName("dates")
    val dates: EventDates?,

    @SerializedName("_embedded")
    val content: EventContent?

) : BaseEventContent()

@Serializable
data class EventImage(
    @SerializedName("ratio")
    val ratio: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("width")
    val width: Int?,

    @SerializedName("height")
    val height: Int?
)

@Serializable
data class EventDates(
    @SerializedName("start")
    val start: EventStartDate?,

    @SerializedName("timezone")
    val timezone: String?
)

@Serializable
data class EventStartDate(
    @SerializedName("localDate")
    val localDate: String?,

    @SerializedName("localTime")
    val localTime: String?,

    @SerializedName("dateTime")
    val dateTime: String?,

    @SerializedName("dateTBD")
    val dateTBD: Boolean?,

    @SerializedName("dateTBA")
    val dateTBA: Boolean?,

    @SerializedName("timeTBA")
    val timeTBA: Boolean?,

    @SerializedName("noSpecificTime")
    val noSpecificTime: String?
)

@Serializable
data class EventContent(
    @SerializedName("venues")
    val venues: List<EventVenue>?
)

@Serializable
data class EventVenue(
    @SerializedName("postalCode")
    val postalCode: String? = null,

    @SerializedName("timezone")
    val timeZone: String? = null,

    @SerializedName("city")
    val city: City? = null,

    @SerializedName("state")
    val state: State? = null,

    @SerializedName("country")
    val country: Country? = null,

    @SerializedName("address")
    val address: Address? = null,

    ) : BaseEventContent() {

    @Serializable
    data class City(
        @SerializedName("name")
        val name: String? = null,
    )

    @Serializable
    data class State(
        @SerializedName("name")
        val name: String? = null,

        @SerializedName("stateCode")
        val stateCode: String? = null,
    )

    @Serializable
    data class Country(
        @SerializedName("name")
        val name: String? = null,

        @SerializedName("countryCode")
        val countryCode: String? = null,
    )

    @Serializable
    data class Address(
        @SerializedName("line1")
        val line1: String? = null,
    )
}

@Serializable
open class BaseEventContent {
    @SerializedName("id")
    val id: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("type")
    val type: String? = null

    @SerializedName("url")
    val url: String? = null

    @SerializedName("locale")
    val locale: String? = null

    @SerializedName("images")
    val images: List<EventImage>? = null
}

@Serializable
open class GenericErrorResponse(
    val code: Int? = null,
    val error: String? = null,
    val description: String? = null
)

@Serializable
data class QueryErrorResponse(
    @SerializedName("errors")
    val errors: List<QueryError>? = null
) : GenericErrorResponse()

@Serializable
data class QueryError(
    @SerializedName("code")
    val codeId: String? = null,

    @SerializedName("detail")
    val detail: String? = null
)

@Serializable
data class FaultErrorResponse(
    @SerializedName("fault")
    val fault: FaultError? = null
) : GenericErrorResponse() {

    @Serializable
    data class FaultError(
        @SerializedName("faultstring")
        val description: String? = null,

        @SerializedName("detail")
        val detail: FaultDetail? = null
    )

    @Serializable
    data class FaultDetail(
        @SerializedName("errorcode")
        val errorCode: String? = null
    )
}