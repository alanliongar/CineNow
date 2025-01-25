package com.devspacecinenow.list

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Test

class TestesCoroutines {
    // Cria um TestCoroutineScheduler manualmente
    private val scheduler = TestCoroutineScheduler()

    // Cria um dispatcher baseado no scheduler manual

    private var testDispatcher: TestDispatcher = UnconfinedTestDispatcher(scheduler)

    @Test
    fun main() {
        // Usa runBlocking para criar o ambiente de teste
        runTest(testDispatcher) {
            println("Tarefa iniciada.")

            // Lança uma corrotina para executar o delay e outros trabalhos
            delay(1000) // Simula um atraso de 1 segundo virtual
            println("Tarefa concluída.")

            // Avança o tempo virtualmente em 1 segundo
            scheduler.advanceTimeBy(1000)
        }
    }
}








