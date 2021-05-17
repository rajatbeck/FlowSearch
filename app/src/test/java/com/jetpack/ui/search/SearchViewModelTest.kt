package com.jetpack.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jetpack.data.Resource
import com.jetpack.data.SearchRepository
import com.jetpack.utils.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    private val searchRepository: SearchRepository = mockk(relaxed = true)

    @Test
    @ExperimentalTime
    @ExperimentalCoroutinesApi
    fun `search single string success`() {
        mainCoroutineRule.dispatcher.runBlockingTest {
            //WHEN
            searchViewModel = SearchViewModel(searchRepository)
            val searchTerm = slot<String>()
            coEvery {
               val success = searchRepository.searchBreweries(capture(searchTerm))
                success
            }.returns(Resource.Success(listOf()))

            // THEN
            searchViewModel.onViewEvent("rajat")
            searchViewModel.onViewEvent("rajat1")
            searchViewModel.onViewEvent("rajat2")
            advanceTimeBy(200)
//
//            coVerify(exactly = 1) {
//                searchRepository.searchBreweries(any())
//            }

            //GIVEN
//            searchViewModel.searchEvent.test {
//                this.expectItem() shouldBeEqualTo "rajat2"
//            }
            searchViewModel.viewState.test {
               // assertEquals("rajat22", searchTerm.captured)
                //this.expectItem() `should be equal to` Resource.Loading
                //this.expectItem() `should be equal to` Resource.Loading
                this.expectItem() `should be equal to` Resource.Success(listOf())
                //expectComplete()
            }
        }
    }

}