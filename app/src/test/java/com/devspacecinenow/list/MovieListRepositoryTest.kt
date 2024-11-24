package com.devspacecinenow.list

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.list.data.MovieListRepository
import com.devspacecinenow.list.data.local.MovieListLocalDataSource
import com.devspacecinenow.list.data.remote.MovieListRemoteDataSource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import okio.IOException
import java.net.UnknownHostException

class MovieListRepositoryTest {
    private val local: MovieListLocalDataSource = mock()
    private val remote: MovieListRemoteDataSource = mock()

    private val underTest by lazy {
        MovieListRepository(local = local, remote = remote)
    }

    @Test
    fun `Given remote success When getting nowPlaying movies Then update local data`() {
        /*API Sucesso,
          Com dados,
          Atualiza dados locais,
          Devolve dados locais.*/
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
            whenever(local.getNowPlayingMovies(1)).thenReturn(list)

            //When
            val result = underTest.getNowPlaying(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote empty success When getting nowPlaying movies Then return local empty data`() {
        /*API Sucesso,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais vazio.*/
        runTest {
            //Given
            val list = emptyList<Movie>()
            val remoteResult = Result.success(list)
            whenever(remote.getNowPlaying(1)).thenReturn(remoteResult)
            whenever(local.getNowPlayingMovies(1)).thenReturn(list)

            //When
            val result = underTest.getNowPlaying(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local, times(0)).updateLocalItems(list)
        }
    }

    @Test
    fun `Given no internet connection When getting nowPlaying movies Then return local data`() {
        /*API Falha,
        Sem dados, mas tanto faz se tiver dados ou não,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getNowPlaying(1)).thenReturn(Result.failure(UnknownHostException("No internet")))
            whenever(local.getNowPlayingMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getNowPlaying(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting nowPlaying movies Then asset known error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getNowPlaying(1)).thenThrow(IOException("No internet"))
            whenever(local.getNowPlayingMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getNowPlaying(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting nowPlaying movies Then asset unknown error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            val exception = RuntimeException("Erro genérico. Não faço ideia do que aconteceu")
            whenever(remote.getNowPlaying(1)).thenThrow(exception)

            //When
            val result = underTest.getNowPlaying(1)

            //Then
            val expected = exception
            assertEquals(expected, result.exceptionOrNull())
        }
    }

    @Test
    fun `Given remote success When getting upComing movies Then update local data`() {
        /*API Sucesso,
          Com dados,
          Atualiza dados locais,
          Devolve dados locais.*/
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.UpComing.name,
                    page = 1
                )
            )
            val remoteResult = Result.success(list)
            whenever(remote.getUpComing(1)).thenReturn(remoteResult)
            whenever(local.getUpComingMovies(1)).thenReturn(list)

            //When
            val result = underTest.getUpComing(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote empty success When getting upComing movies Then return local empty data`() {
        /*API Sucesso,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais vazio.*/
        runTest {
            //Given
            val list = emptyList<Movie>()
            val remoteResult = Result.success(list)
            whenever(remote.getUpComing(1)).thenReturn(remoteResult)
            whenever(local.getUpComingMovies(1)).thenReturn(list)

            //When
            val result = underTest.getUpComing(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local, times(0)).updateLocalItems(list)
        }
    }

    @Test
    fun `Given no internet connection When getting upComing movies Then return local data`() {
        /*API Falha,
        Sem dados, mas tanto faz se tiver dados ou não,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getUpComing(1)).thenReturn(Result.failure(UnknownHostException("No internet")))
            whenever(local.getUpComingMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getUpComing(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting upComing movies Then asset known error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getUpComing(1)).thenThrow(IOException("No internet"))
            whenever(local.getUpComingMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getUpComing(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting upComing movies Then asset unknown error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            val exception = RuntimeException("Erro genérico. Não faço ideia do que aconteceu")
            whenever(remote.getUpComing(1)).thenThrow(exception)

            //When
            val result = underTest.getUpComing(1)

            //Then
            val expected = exception
            assertEquals(expected, result.exceptionOrNull())
        }
    }

    @Test
    fun `Given remote success When getting topRated movies Then update local data`() {
        /*API Sucesso,
          Com dados,
          Atualiza dados locais,
          Devolve dados locais.*/
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name,
                    page = 1
                )
            )
            val remoteResult = Result.success(list)
            whenever(remote.getTopRated(1)).thenReturn(remoteResult)
            whenever(local.getTopRatedMovies(1)).thenReturn(list)

            //When
            val result = underTest.getTopRated(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote empty success When getting topRated movies Then return local empty data`() {
        /*API Sucesso,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais vazio.*/
        runTest {
            //Given
            val list = emptyList<Movie>()
            val remoteResult = Result.success(list)
            whenever(remote.getTopRated(1)).thenReturn(remoteResult)
            whenever(local.getTopRatedMovies(1)).thenReturn(list)

            //When
            val result = underTest.getTopRated(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local, times(0)).updateLocalItems(list)
        }
    }

    @Test
    fun `Given no internet connection When getting topRated movies Then return local data`() {
        /*API Falha,
        Sem dados, mas tanto faz se tiver dados ou não,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getTopRated(1)).thenReturn(Result.failure(UnknownHostException("No internet")))
            whenever(local.getTopRatedMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getTopRated(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting topRated movies Then asset known error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getTopRated(1)).thenThrow(IOException("No internet"))
            whenever(local.getTopRatedMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getTopRated(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting topRated movies Then asset unknown error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            val exception = RuntimeException("Erro genérico. Não faço ideia do que aconteceu")
            whenever(remote.getTopRated(1)).thenThrow(exception)

            //When
            val result = underTest.getTopRated(1)

            //Then
            val expected = exception
            assertEquals(expected, result.exceptionOrNull())
        }
    }

    @Test
    fun `Given remote success When getting popular movies Then update local data`() {
        /*API Sucesso,
          Com dados,
          Atualiza dados locais,
          Devolve dados locais.*/
        runTest {
            //Given
            val list = listOf(
                Movie(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name,
                    page = 1
                )
            )
            val remoteResult = Result.success(list)
            whenever(remote.getPopular(1)).thenReturn(remoteResult)
            whenever(local.getPopularMovies(1)).thenReturn(list)

            //When
            val result = underTest.getPopular(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local).updateLocalItems(list)
        }
    }

    @Test
    fun `Given remote empty success When getting popular movies Then return local empty data`() {
        /*API Sucesso,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais vazio.*/
        runTest {
            //Given
            val list = emptyList<Movie>()
            val remoteResult = Result.success(list)
            whenever(remote.getPopular(1)).thenReturn(remoteResult)
            whenever(local.getPopularMovies(1)).thenReturn(list)

            //When
            val result = underTest.getPopular(1) //aqui retorna um RESULT que contém uma lista.

            //Then
            val expected = list
            assertEquals(expected, result.getOrNull())
            verify(local, times(0)).updateLocalItems(list)
        }
    }

    @Test
    fun `Given no internet connection When getting popular movies Then return local data`() {
        /*API Falha,
        Sem dados, mas tanto faz se tiver dados ou não,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getPopular(1)).thenReturn(Result.failure(UnknownHostException("No internet")))
            whenever(local.getPopularMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getPopular(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting popular movies Then asset known error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            whenever(remote.getPopular(1)).thenThrow(IOException("No internet"))
            whenever(local.getPopularMovies(1)).thenReturn(emptyList<Movie>()) //banco de dados vazio, não importa se tem dados ou nao, pq o teste aqui não é esse

            //When
            val result = underTest.getPopular(1)

            //Then
            val expected = emptyList<Movie>()
            assertEquals(expected, result.getOrNull())
        }
    }

    @Test
    fun `Given no internet connection When getting popular movies Then asset unknown error`() {
        /*API Falha,
        Sem dados,
        NÃO Atualiza dados locais,
        Devolve dados locais*/
        runTest {
            //Given
            val exception = RuntimeException("Erro genérico. Não faço ideia do que aconteceu")
            whenever(remote.getPopular(1)).thenThrow(exception)

            //When
            val result = underTest.getPopular(1)

            //Then
            val expected = exception
            assertEquals(expected, result.exceptionOrNull())
        }
    }
}