package org.vazteixeira.rui.rxjava.examples

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.OldExample
import org.vazteixeira.rui.rxjava.randomPublisher
import java.util.concurrent.CountDownLatch

object Share : OldExample {
    override fun invoke(latch: CountDownLatch) {
        val shared = randomPublisher().share()
        shared
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { println("computation():\t$it") },
                onComplete = { latch.countDown() }
            )
        shared
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { println("io():\t\t\t$it") },
                onComplete = { latch.countDown() }
            )
    }
}
