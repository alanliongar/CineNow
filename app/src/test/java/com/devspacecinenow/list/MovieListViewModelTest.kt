package com.devspacecinenow.list

import app.cash.turbine.test
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListViewModelTest {

    private val repository: MovieListRepository = mock() //mock = dado mockado, é um dublê.

    @OptIn(ExperimentalCoroutinesApi::class)

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }


    @Test
    fun `Given fresh viewModel when collecting to nowPlaying then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name,
                    page = 1
                )
            )
            whenever(repository.getNowPlaying(1)).thenReturn(Result.success(movies)) //esse é o dublê.


            //When
            underTest.uiNowPlaying.test { //a função test entende que o último item é o item a ser testado (biblioteca turbine)
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "1",
                            image = "image1",
                        )
                    )
                )
                assertEquals(expected, awaitItem())
            }
        }
    }


    @Test
    fun `Given fresh viewModel when collecting to topRated then assert expected value`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "1",
                    image = "image1",
                    category = MovieCategory.TopRated.name,
                    page = 1
                )
            )
            whenever(repository.getTopRated(1)).thenReturn(Result.success(movies)) //esse é o dublê.


            //When
            underTest.uiTopRated.test { //a função test entende que o último item é o item a ser testado (biblioteca turbine)
                //Then assert expected value
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "1",
                            image = "image1",
                        )
                    )
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collecting to topRated then assert loading state`() {
        runTest {
            //Given
            val movies = listOf<Movie>(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "1",
                    image = "image1",
                    category = MovieCategory.TopRated.name,
                    page = 1
                )
            )

            whenever(repository.getTopRated(1)).thenReturn(Result.success(movies)) //esse é o dublê.


            //When
            val result = underTest.uiTopRated.value

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )
            assertEquals(expected, result)
        }
    }
}