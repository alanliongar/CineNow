package com.devspacecinenow
//Com Turbine
import app.cash.turbine.*
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import com.nhaarman.mockitokotlin2.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds

class MovieListViewModelTestCOMTURBINE {

    private val repository: MovieListRepository = mock() //mock = dado mockado, é um dublê.

    @OptIn(ExperimentalCoroutinesApi::class)

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }

    private val underTest2 by lazy {
        MovieListViewModel(repository, testDispatcher, delay = 2L)
    }


    //Essa é a função, e seja feliz.
    //Aqui a emissão é de um ítem, ou seja: a tela já passou pelo estado de "isloading"
    //O que significa que estamos pegando o segundo estado dela.
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
                val expected = MovieListUiState( //Aqui seria a segunda emissão
                    list = listOf(
                        MovieUiData(
                            id = 1, title = "title1", overview = "1", image = "image1"
                        )
                    )
                )
                assertEquals(expected, awaitItem())
                //Note que aqui não é o primeiro ítem, segundo o Roque é o último ítem a ser emitido
            }
        }
    }

    @Test
    fun `Given fresh viewModel when collecting to nowPlaying then assert isLoading state`() {
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

            underTest2.uiNowPlaying.test {
                val expectedLoading = MovieListUiState( //primeira emissão de item
                    isLoading = true
                )
                assertEquals(expectedLoading, awaitItem())
                //Note que aqui não é o primeiro ítem, é o segundo, pois o primeiro
                //faria o assert do estado de loading da tela.
            }
        }
    }
}