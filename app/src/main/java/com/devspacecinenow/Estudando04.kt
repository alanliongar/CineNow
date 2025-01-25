import kotlinx.coroutines.*

fun main() = runBlocking {
    val api1 = async { carregarDadosDeApi("API 1") }
    val api2 = async { carregarDadosDeApi("API 2") }

    println("Carregando dados...")
    println(api1.await())
    println(api2.await())
}

suspend fun carregarDadosDeApi(api: String): String {
    delay(2000) // Simula o tempo de resposta da API
    return "$api: Dados carregados"
}