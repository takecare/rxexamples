package org.vazteixeira.rui.rxjava

import io.reactivex.Observable

fun randomPublisher(
    threshold: Double = 0.99,
    sleepMultiplierMillis: Int = 100
) = Observable.create<Double> { emitter ->
    var value = Math.random()
    do {
        emitter.onNext(value)
        value = Math.random()
        Thread.sleep((value * sleepMultiplierMillis).toLong())
    } while (value < threshold)
    emitter.onNext(value)
    emitter.onComplete()
}

fun namedRandomPublisher(
    name: String = "Publisher",
    threshold: Double = 0.99,
    sleepMultiplier: Int = 100
) = randomPublisher(threshold, sleepMultiplier).map { "$name: $it" }

fun printWithName(msg: String) {
    println("[${Thread.currentThread().name}]\t$msg")
}
