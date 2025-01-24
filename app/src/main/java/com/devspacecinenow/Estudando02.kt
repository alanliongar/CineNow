package com.devspacecinenow

import kotlinx.coroutines.*

fun main(){
    val disp = Dispatchers.IO
    runBlocking {
        launch {
            repeat(3) {
                println("Corrotina 1 - Iteração $it")
                yield()
            }
        }
        launch {
            repeat(3) {
                println("Corrotina 2 - Iteração $it")
                yield()
            }
        }
    }
}






/*

fun main() = runBlocking {
    try {
        withTimeout(2000) {
            repeat(3) {
                println("Executando iteração $it")
                delay(1000)
            }
        }
    } catch (e: TimeoutCancellationException) {
        println("Tempo limite excedido!")
    }
}


fun main() = runBlocking {
    val resultado = withTimeoutOrNull(2000) {
        repeat(3) {
            println("Executando iteração $it")
            delay(1000)
        }
        "Concluído"
    }
    println("Resultado: $resultado")
}


fun main() = runBlocking {
    println("Executando no dispatcher: ${Thread.currentThread().name}")
    withContext(Dispatchers.IO) {
        println("Executando em IO dispatcher: ${Thread.currentThread().name}")
    }
}



fun main() = runBlocking {
    val job = launch {
        delay(1000)
        println("Corrotina concluída")
    }
    println("Esperando o job terminar...")
    job.join()
    println("Job terminado!")
}



fun main() = runBlocking {
    val resultado = async {
        delay(1000)
        "Resultado da coroutine"
    }
    println("Esperando o resultado...")
    println("Resultado: ${resultado.await()}")
}


fun main() = runBlocking {
    println("Antes do delay")
    delay(1000)
    println("Depois do delay")
}


fun main() = runBlocking {
    launch {
        repeat(3) {
            println("Corrotina 1 - Iteração $it")
            yield()
        }
    }
    launch {
        repeat(3) {
            println("Corrotina 2 - Iteração $it")
            yield()
        }
    }
}



fun main() = runBlocking {
    val resultado = async {
        delay(1000)
        42
    }
    println("Resultado do async: ${resultado.await()}")
}



fun main() = runBlocking {
    launch {
        delay(1000)
        println("Corrotina executada com launch!")
    }
    println("Executando corrotina com launch")
}



fun main() = runBlocking {
    println("Início do runBlocking")
    delay(1000)
    println("Fim do runBlocking")
}

*/
