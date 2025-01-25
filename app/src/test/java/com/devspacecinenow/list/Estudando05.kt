package com.devspacecinenow.list

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*

fun main() = runTest {
    // Usa o scheduler padrão do runTest
    val testDispatcher = UnconfinedTestDispatcher(testScheduler)

    withContext(testDispatcher) {
        println("Executando no UnconfinedTestDispatcher: ${Thread.currentThread().name}")
        delay(1000) // Simula uma tarefa longa
        println("Finalizando no UnconfinedTestDispatcher.")
    }
}
