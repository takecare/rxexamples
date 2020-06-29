package org.vazteixeira.rui.rxjava.examples

import io.reactivex.rxkotlin.subscribeBy
import org.vazteixeira.rui.rxjava.OldExample
import org.vazteixeira.rui.rxjava.randomPublisher
import java.util.concurrent.CountDownLatch

object Reduce : OldExample {
    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .reduce { acc: Double, value: Double -> acc + value }
            .subscribeBy(
                onSuccess = { println("$it") },
                onComplete = { latch.countDown() }
            )

    }
}
