package com.devspacecinenow

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        //GWT
        //Given - dados dois numeros
        val oneNum = 2
        val twoNum = 3

        //when (soma) - operação
        val sum = oneNum + twoNum


        //then
        val expected = 5
        assertEquals(expected, sum)
    }
}