package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.presentation.MovieListViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieListViewModelTest {

    private val repository: MovieListRepository = mock { }

    private val underTest by lazy {
        MovieListViewModel(repository)
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
            val result = Result.success(movies)
            whenever(repository.getTopRated(1)).thenReturn(result)

            //When
            underTest.uiTopRated.value
        }
    }
}