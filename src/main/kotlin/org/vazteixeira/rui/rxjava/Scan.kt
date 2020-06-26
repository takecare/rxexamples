package org.vazteixeira.rui.rxjava

import io.reactivex.rxkotlin.subscribeBy
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
