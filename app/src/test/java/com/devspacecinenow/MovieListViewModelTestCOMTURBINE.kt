package com.devspacecinenow
//Com Turbine
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

class MovieListViewModelTestCOMTURBINE {

    private val repository: MovieListRepository = mock() //mock = dado mockado, é um dublê.

    @OptIn(ExperimentalCoroutinesApi::class)

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }


    //Essa é a função, e seja feliz.
    @Test
    fun `Given fresh viewModel when collecting to nowPlaying then assert expected value`() {
        runTest {
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name,
                    page = 1
                )
            )
            whenever(repository.getNowPlaying(1)).thenReturn(Result.success(movies))
            underTest.uiNowPlaying.test {
                val expected = MovieListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1, title = "title1", overview = "1", image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem()) //último ítem - ele supoe que o último é o que vc tá esperando.
            }
        }
    }
}