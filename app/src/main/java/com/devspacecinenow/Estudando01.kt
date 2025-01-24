import kotlinx.coroutines.*

fun main1() {
    val scope = CoroutineScope(Dispatchers.Default) // Cria um escopo de coroutines

    // Lança uma coroutine no escopo
    scope.launch {
        coroutineScope {  }
        repeat(5) { i ->
            println("Executando tarefa $i")
            delay(1000) // Simula uma operação demorada
        }
    }

    // Aguarda 3 segundos e cancela o escopo
    Thread.sleep(3000)
    scope.cancel() // Cancela todas as coroutines no escopo
    println("Escopo cancelado.")
}

fun main(){
    val thread = Thread{
        runBlocking {
            delay(2000)
            50
        }
    }
    thread.start()
    thread.join()

    println("O nome da variável eh: ${thread::class.simpleName}")
}
