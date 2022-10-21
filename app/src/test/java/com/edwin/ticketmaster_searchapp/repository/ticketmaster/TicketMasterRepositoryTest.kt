package com.edwin.ticketmaster_searchapp.repository.ticketmaster

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edwin.ticketmaster_searchapp.manager.NetworkManager
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.OnErrorType
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local.LocalStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote.RemoteStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.model.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [28])
class TicketMasterRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    var mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        hiltRule.inject()
    }

    @Test
    fun `load events when network is connected and success response`() {
        runTest {
            //GIVEN
            val result =
                RequestResponse.OnSuccess(EventsResponse(EventsList(listOf(
                    Event(dates = EventDates(
                        start = EventStartDate("", "", "", null, null, null, null), null),
                        content = EventContent(
                            listOf())),
                    Event(dates = EventDates(
                        start = EventStartDate("", "", "", null, null, null, null), null),
                        content = EventContent(
                            listOf())))
                )))

            val networkManager = mock<NetworkManager>()
            Mockito.`when`(networkManager.isConnected()).thenReturn(true)

            val remoteStorage = mock<RemoteStorage>()
            Mockito.`when`(remoteStorage.loadEvents(100)).thenReturn(result)

            val localStorage: LocalStorage = mock()

            val repository = TicketMasterRepository(remoteStorage, localStorage, networkManager)

            //WHEN
            val response = repository.loadEvents(100)

            //THEN
            Assert.assertEquals(result, response)
        }
    }

    @Test
    fun `load events when network is connected and error response`() {
        runTest {
            //GIVEN
            val result = RequestResponse.OnError(OnErrorType.OnGenericError(GenericErrorResponse()))

            val networkManager = mock<NetworkManager>()
            Mockito.`when`(networkManager.isConnected()).thenReturn(true)

            val remoteStorage = mock<RemoteStorage>()
            Mockito.`when`(remoteStorage.loadEvents(100)).thenReturn(result)

            val localStorage: LocalStorage = mock()

            val repository = TicketMasterRepository(remoteStorage, localStorage, networkManager)

            //WHEN
            val response = repository.loadEvents(100)

            //THEN
            Assert.assertEquals(result, response)
        }
    }

    @Test
    fun `load events when network is NOT connected and success response`() {
        runTest {
            //GIVEN
            val eventResponse = EventsResponse(EventsList(listOf(
                Event(dates = EventDates(
                    start = EventStartDate("", "", "", null, null, null, null), null),
                    content = EventContent(
                        listOf())),
                Event(dates = EventDates(
                    start = EventStartDate("", "", "", null, null, null, null), null),
                    content = EventContent(
                        listOf())))
            ))
            val result = RequestResponse.OnSuccess(eventResponse)

            val networkManager = mock<NetworkManager>()
            Mockito.`when`(networkManager.isConnected()).thenReturn(false)

            val remoteStorage = mock<RemoteStorage>()
            Mockito.`when`(remoteStorage.loadEvents(100)).thenReturn(result)

            val localStorage = mock<LocalStorage>()
            Mockito.`when`(localStorage.getSerializedData(TicketMasterRepository.LAST_QUERY_EVENTS_KEY,
                EventsResponse::class.java)).thenReturn(eventResponse)

            val repository = TicketMasterRepository(remoteStorage, localStorage, networkManager)

            //WHEN
            val response = repository.loadEvents(100)

            //THEN
            Assert.assertEquals(result, response)
        }
    }

}