package com.devspacecinenow.list

import android.util.Log
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
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockedStatic
import org.mockito.Mockito
import java.net.UnknownHostException

class MovieListViewModelTestEXSEMTURBINE {

    private lateinit var mockedLog: MockedStatic<Log>

    @Before
    fun setupMockLog() {
        mockedLog = Mockito.mockStatic(Log::class.java)
        mockedLog.`when`<Any> { Log.d(Mockito.anyString(), Mockito.anyString()) }.thenReturn(0)
    }

    @After
    fun tearDownMockLog() {
        mockedLog.close() // Libera o mock após cada teste
    }

    private val repository: MovieListRepository = mock() //mock = dado mockado, é um dublê.

    @OptIn(ExperimentalCoroutinesApi::class)

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieListViewModel(repository, testDispatcher)
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying Then assert isloading state`() {
        testStateFlowFunctionIsLoadingState("uiNowPlaying")
    }

    @Test
    fun `Given fresh viewModel When collecting to upComing Then assert isloading state`() {
        testStateFlowFunctionIsLoadingState("uiUpComing")
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert isloading state`() {
        testStateFlowFunctionIsLoadingState("uiTopRated")
    }

    @Test
    fun `Given fresh viewModel When collecting to popular Then assert isloading state`() {
        testStateFlowFunctionIsLoadingState("uiPopular")
    }

    @Test
    fun `Given fresh viewModel when collecting to nowPlaying then assert expected value`() {
        testStateFlowFunctionSuccessWithDataState("uiNowPlaying")
    }

    @Test
    fun `Given fresh viewModel when collecting to upComing then assert expected value`() {
        testStateFlowFunctionSuccessWithDataState("uiUpComing")
    }

    @Test
    fun `Given fresh viewModel when collecting to topRated then assert expected value`() {
        testStateFlowFunctionSuccessWithDataState("uiTopRated")
    }

    @Test
    fun `Given fresh viewModel when collecting to popular then assert expected value`() {
        testStateFlowFunctionSuccessWithDataState("uiPopular")
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying then assert empty expected value`() {
        testStateFlowFunctionSuccessWithOutDataState("uiNowPlaying")
    }

    @Test
    fun `Given fresh viewModel When collecting to upComing then assert empty expected value`() {
        testStateFlowFunctionSuccessWithOutDataState("uiUpComing")
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated then assert empty expected value`() {
        testStateFlowFunctionSuccessWithOutDataState("uiTopRated")
    }

    @Test
    fun `Given fresh viewModel When collecting to popular then assert empty expected value`() {
        testStateFlowFunctionSuccessWithOutDataState("uiPopular")
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying then assert error known state`() {
        testStateFlowFunctionKnownFailure("uiNowPlaying")
    }

    @Test
    fun `Given fresh viewModel When collecting to upComing then assert error known state`() {
        testStateFlowFunctionKnownFailure("uiUpComing")
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated then assert error known state`() {
        testStateFlowFunctionKnownFailure("uiTopRated")
    }

    @Test
    fun `Given fresh viewModel When collecting to popular then assert error known state`() {
        testStateFlowFunctionKnownFailure("uiPopular")
    }

    @Test
    fun `Given fresh viewModel When collecting to nowPlaying then assert error unknown state`() {
        testStateFlowFunctionUnKnownFailure("uiNowPlaying")
    }

    @Test
    fun `Given fresh viewModel When collecting to upComing then assert error unknown state`() {
        testStateFlowFunctionUnKnownFailure("uiUpComing")
    }

    @Test
    fun `Given fresh viewModel When collecting to topRated then assert error unknown state`() {
        testStateFlowFunctionUnKnownFailure("uiTopRated")
    }

    @Test
    fun `Given fresh viewModel When collecting to popular then assert error unknown state`() {
        testStateFlowFunctionUnKnownFailure("uiPopular")
    }


    private fun testStateFlowFunctionIsLoadingState(
        viewModelFlowToBeTested: String
    ) {
        runTest {
            val movies = emptyList<Movie>()
            if (viewModelFlowToBeTested == "uiNowPlaying") {
                whenever(repository.getNowPlaying(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiUpComing") {
                whenever(repository.getUpComing(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiTopRated") {
                whenever(repository.getTopRated(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiPopular") {
                whenever(repository.getPopular(1)).thenReturn(Result.success(movies))
            } else {
                throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
            }
            var result: MovieListUiState? = null
            backgroundScope.launch(testDispatcher) {
                if (viewModelFlowToBeTested == "uiNowPlaying") {
                    result = underTest.uiNowPlaying.drop(0).first()
                } else if (viewModelFlowToBeTested == "uiUpComing") {
                    result = underTest.uiUpComing.drop(0).first()
                } else if (viewModelFlowToBeTested == "uiTopRated") {
                    result = underTest.uiTopRated.drop(0).first()
                } else {
                    result = underTest.uiPopular.drop(0).first()
                }
            }
            val expected = MovieListUiState(
                isLoading = true
            )
            assertEquals(expected, result)
        }
    }

    private fun testStateFlowFunctionSuccessWithDataState(
        viewModelFlowToBeTested: String
    ) {
        runTest {
            val movies = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "1",
                    image = "image1",
                    category = if (viewModelFlowToBeTested == "uiNowPlaying") {
                        MovieCategory.NowPlaying.name
                    } else if (viewModelFlowToBeTested == "uiUpComing") {
                        MovieCategory.UpComing.name
                    } else if (viewModelFlowToBeTested == "uiTopRated") {
                        MovieCategory.TopRated.name
                    } else if (viewModelFlowToBeTested == "uiPopular") {
                        MovieCategory.Popular.name
                    } else {
                        throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
                    },
                    page = 1
                )
            )
            if (viewModelFlowToBeTested == "uiNowPlaying") {
                whenever(repository.getNowPlaying(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiUpComing") {
                whenever(repository.getUpComing(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiTopRated") {
                whenever(repository.getTopRated(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiPopular") {
                whenever(repository.getPopular(1)).thenReturn(Result.success(movies))
            } else {
                throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
            }
            var result: MovieListUiState? = null
            backgroundScope.launch(testDispatcher) {
                result = if (viewModelFlowToBeTested == "uiNowPlaying") {
                    underTest.uiNowPlaying.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiUpComing") {
                    underTest.uiUpComing.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiTopRated") {
                    underTest.uiTopRated.drop(1).first()
                } else {
                    underTest.uiPopular.drop(1).first()
                }
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

    private fun testStateFlowFunctionSuccessWithOutDataState(
        viewModelFlowToBeTested: String
    ) {
        runTest {
            val movies = emptyList<Movie>()
            if (viewModelFlowToBeTested == "uiNowPlaying") {
                whenever(repository.getNowPlaying(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiUpComing") {
                whenever(repository.getUpComing(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiTopRated") {
                whenever(repository.getTopRated(1)).thenReturn(Result.success(movies))
            } else if (viewModelFlowToBeTested == "uiPopular") {
                whenever(repository.getPopular(1)).thenReturn(Result.success(movies))
            } else {
                throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
            }
            var result: MovieListUiState? = null
            backgroundScope.launch(testDispatcher) {
                result = if (viewModelFlowToBeTested == "uiNowPlaying") {
                    underTest.uiNowPlaying.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiUpComing") {
                    underTest.uiUpComing.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiTopRated") {
                    underTest.uiTopRated.drop(1).first()
                } else {
                    underTest.uiPopular.drop(1).first()
                }
            }
            val expected = MovieListUiState(
                list = emptyList<MovieUiData>()
            )
            assertEquals(expected, result)
        }
    }

    private fun testStateFlowFunctionKnownFailure(
        viewModelFlowToBeTested: String
    ) {
        runTest {
            val exception = UnknownHostException()
            if (viewModelFlowToBeTested == "uiNowPlaying") {
                whenever(repository.getNowPlaying(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiUpComing") {
                whenever(repository.getUpComing(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiTopRated") {
                whenever(repository.getTopRated(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiPopular") {
                whenever(repository.getPopular(1)).thenReturn(Result.failure(exception))
            } else {
                throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
            }

            var result: MovieListUiState? = null
            backgroundScope.launch(testDispatcher) {
                result = if (viewModelFlowToBeTested == "uiNowPlaying") {
                    underTest.uiNowPlaying.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiUpComing") {
                    underTest.uiUpComing.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiTopRated") {
                    underTest.uiTopRated.drop(1).first()
                } else {
                    underTest.uiPopular.drop(1).first()
                }
            }
            val expected = MovieListUiState(
                isError = true,
                isLoading = false,
                errorMessage = "Sem internet",
                list = emptyList()
            )
            assertEquals(expected, result)
        }
    }

    private fun testStateFlowFunctionUnKnownFailure(
        viewModelFlowToBeTested: String
    ) {
        runTest {
            val exception = UnknownError()
            if (viewModelFlowToBeTested == "uiNowPlaying") {
                whenever(repository.getNowPlaying(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiUpComing") {
                whenever(repository.getUpComing(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiTopRated") {
                whenever(repository.getTopRated(1)).thenReturn(Result.failure(exception))
            } else if (viewModelFlowToBeTested == "uiPopular") {
                whenever(repository.getPopular(1)).thenReturn(Result.failure(exception))
            } else {
                throw IllegalArgumentException("Invalid state: $viewModelFlowToBeTested")
            }

            var result: MovieListUiState? = null
            backgroundScope.launch(testDispatcher) {
                result = if (viewModelFlowToBeTested == "uiNowPlaying") {
                    underTest.uiNowPlaying.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiUpComing") {
                    underTest.uiUpComing.drop(1).first()
                } else if (viewModelFlowToBeTested == "uiTopRated") {
                    underTest.uiTopRated.drop(1).first()
                } else {
                    underTest.uiPopular.drop(1).first()
                }
            }
            val expected = MovieListUiState(
                isError = true,
                isLoading = false,
                errorMessage = "Algo deu errado",
                list = emptyList()
            )
            assertEquals(expected, result)
        }
    }
}