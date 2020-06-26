package org.vazteixeira.rui.rxjava

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

object ObserveOn : OldExample {
    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .observeOn(Schedulers.computation())
            .doOnNext { println("${Thread.currentThread().name}: $it") }
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = { println("${Thread.currentThread().name}: $it") },
                onComplete = { latch.countDown() }
            )

    }
}
