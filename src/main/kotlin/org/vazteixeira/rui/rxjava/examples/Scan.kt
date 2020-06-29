package org.vazteixeira.rui.rxjava.examples

import io.reactivex.rxkotlin.subscribeBy
import org.vazteixeira.rui.rxjava.OldExample
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(Scan)

object Scan : OldExample {
    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .scan { acc: Double, value: Double -> acc + value }
            .subscribeBy(
                onNext = { println("$it") },
                onComplete = { latch.countDown() }
            )

    }
}
