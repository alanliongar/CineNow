package com.devspacecinenow

import kotlinx.coroutines.*

fun unicaThreadDispatcherPersonalizado(){
    val mySingleThreadDispatcher = newSingleThreadContext("MyThread")
    println("Executando na thread: ${mySingleThreadDispatcher::class.simpleName}")
    runBlocking(mySingleThreadDispatcher) {
        println("Executando na thread personalizada!")
    }
    mySingleThreadDispatcher.close()
}

fun main(){
    unicaThreadDispatcherPersonalizado()
}