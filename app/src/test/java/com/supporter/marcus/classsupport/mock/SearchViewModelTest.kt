package com.supporter.marcus.classsupport.mock

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.supporter.marcus.classsupport.data.DonorRepository
import com.supporter.marcus.classsupport.mock.MockedData.mockList
import com.supporter.marcus.classsupport.ui.Event
import com.supporter.marcus.classsupport.ui.LoadingState
import com.supporter.marcus.classsupport.ui.State
import com.supporter.marcus.classsupport.ui.search.SearchViewModel
import com.supporter.marcus.classsupport.util.MockitoHelper
import com.supporter.marcus.classsupport.util.MockitoHelper.argumentCaptor
import com.supporter.marcus.classsupport.util.TestSchedulerProvider
import kotlinx.coroutines.experimental.async
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchViewModelMockTest {
    lateinit var viewModel: SearchViewModel
    @Mock
    lateinit var statesView: Observer<State>
    @Mock
    lateinit var eventsView: Observer<Event>
    @Mock
    lateinit var repository: DonorRepository


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel =
                SearchViewModel(repository, TestSchedulerProvider())
        viewModel.states.observeForever(statesView)
        viewModel.events.observeForever(eventsView)
    }


    @Test
    fun testDisplayList() {
        given(repository.getProposals(MockitoHelper.any(), MockitoHelper.any(),
                MockitoHelper.any(), MockitoHelper.any(),
                MockitoHelper.any(), MockitoHelper.any(),
                MockitoHelper.any()
        )).willReturn(async { mockList })

        viewModel.getProposals(MockitoHelper.any(), "", "", "", "", "", "")

        // setup ArgumentCaptor
        val arg = argumentCaptor<State>()
        // Here we expect 2 calls on statesView.onChanged
        verify(statesView, Mockito.times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        Assert.assertEquals(2, values.size)
        Assert.assertEquals(LoadingState, values[0])
        
    }
}