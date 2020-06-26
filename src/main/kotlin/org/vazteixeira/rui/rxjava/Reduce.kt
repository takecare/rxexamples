package org.vazteixeira.rui.rxjava

import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.CountDownLatch

object Reduce : Example {
    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .reduce { acc: Double, value: Double -> acc + value }
            .subscribeBy(
                onSuccess = { println("$it") },
                onComplete = { latch.countDown() }
            )

    }
}
