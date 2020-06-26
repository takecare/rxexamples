package org.vazteixeira.rui.rxjava.examples

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(Materialize)

object Materialize : Example {
    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .subscribeOn(Schedulers.computation())
            .materialize()
            .subscribeBy(
                onNext = { println(it) },
                onComplete = { latch.countDown() }
            )
    }
}
