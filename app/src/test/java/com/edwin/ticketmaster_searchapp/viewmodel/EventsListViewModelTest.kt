package com.edwin.ticketmaster_searchapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.TicketMasterRepository
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.OnErrorType
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.RequestResponse
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [28])
class EventsListViewModelTest {

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
    fun `should give the success result to live data`() {
        runTest {
            //GIVEN
            val ticketMasterRepository = mock<TicketMasterRepository>()
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
            `when`((ticketMasterRepository.loadEvents())).thenReturn(result)

            val viewModel = EventsListViewModel(ticketMasterRepository)

            //WHEN
            viewModel.loadEvents()

            //THEN
            Assert.assertEquals(result.response.content?.events, viewModel.eventsListData.value)
        }
    }

    @Test
    fun `should give error result`() {
        runTest {
            //GIVEN
            val ticketMasterRepository = mock<TicketMasterRepository>()
            val result = RequestResponse.OnError(OnErrorType.OnGenericError(GenericErrorResponse()))
            `when`((ticketMasterRepository.loadEvents())).thenReturn(result)

            val viewModel = EventsListViewModel(ticketMasterRepository)

            //WHEN
            viewModel.loadEvents()

            //THEN
            Assert.assertEquals(0, viewModel.eventsListData.value?.size)
        }
    }
}