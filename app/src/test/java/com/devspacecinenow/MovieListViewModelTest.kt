package com.devspacecinenow
//Sem Turbine
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
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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
    fun `Given fresh viewModel When collecting to nowPlaying Then assert expected value`() {
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

            var result: MovieListUiState? = null

            backgroundScope.launch(testDispatcher) {
                result = underTest.uiNowPlaying.drop(1)
                    .first() //aqui está pegando somente o segundo cenário.
            }
            val expected = MovieListUiState(
                list = listOf(
                    MovieUiData(
                        id = 1, title = "title1", overview = "1", image = "image1"
                    )
                )
            )
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert expected value`() {
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

            val expected = MovieListUiState(
                list = listOf(
                    MovieUiData(
                        id = 1,
                        title = "title1",
                        image = "image1",
                        overview = "1"
                    )
                )
            )

            var result: MovieListUiState? = null

            backgroundScope.launch(testDispatcher) { //estou testando somente o segundo cenário, não estou testando o isloading aqui.
                result = underTest.uiTopRated.drop(1).first()
            }

            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert loading state`() {
        runTest {
            //Given
            val movies = listOf(
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

            var result: MovieListUiState? = null

            //When
            backgroundScope.launch(testDispatcher) {
                result = underTest.uiTopRated.drop(0).first()
            }

            //Then assert expected value
            val expected = MovieListUiState(
                isLoading = true
            )
            assertEquals(expected, result)
        }
    }
}

//3 cenários testados - testando viewmodel;