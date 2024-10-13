package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class MovieListRepositoryTest {
    private val local = FakeMovieListLocalDataSource()
    private val remote: MovieListRemoteDataSource = mock()

    private val underTest by lazy {
        MovieListRepository(local = local, remote = remote)
    }


    @Test
    fun `Given no internet connection When getting now playing movies Then return local data`() {
        runTest {
            //Given
            val localList = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name,
                    page = 1
                )
            )
            whenever(remote.getNowPlaying(1)).thenReturn(Result.failure(UnknownHostException("No internet")))
            local.nowPlaying = localList

            //whenever(local.getNowPlayingMovies(1)).thenReturn(localList)

            //When
            val result = underTest.getNowPlaying(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = localList
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given remote success When getting now playing movies Then update local data`() {
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name,
                    page = 1
                )
            )
            val remoteResult = Result.success(list)
            whenever(remote.getNowPlaying(1)).thenReturn(remoteResult)
            local.nowPlaying = list

            //whenever(local.getNowPlayingMovies(1)).thenReturn(list)

            //When
            val result = underTest.getNowPlaying(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            assertEquals(local.updateItems, list)
            //verify(local).updateLocalItems(list)
        }
    }
}

//11 min do video testando repository