package com.devspacecinenow.list

import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest

fun main() = runTest {
    val fluxo = flow {
        emit(1)
        delay(1000)
        emit(2)
    }

    fluxo.test {
        val item1 = awaitItem()
        println("Recebido: $item1") // Exibe o primeiro valor no terminal
        assert(item1 == 1) // Valida o primeiro item

        val item2 = awaitItem()
        println("Recebido: $item2") // Exibe o segundo valor no terminal
        assert(item2 == 2) // Valida o segundo item

        awaitComplete() // Confirma que o fluxo foi concluído
        println("Fluxo concluido!") // Indica que o fluxo terminou
    }
}
