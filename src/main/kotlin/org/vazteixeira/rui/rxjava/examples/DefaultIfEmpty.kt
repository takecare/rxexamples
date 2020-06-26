package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import org.vazteixeira.rui.rxjava.OldExample
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(DefaultIfEmpty)

object DefaultIfEmpty : OldExample {
    override fun invoke(latch: CountDownLatch) {
        val observable = Observable.create<List<Double>> { emitter ->
            emitter.onComplete()
        }

        observable
            .defaultIfEmpty(listOf(99.99))
            .subscribeBy(
                onNext = { println(it) },
                onComplete = { latch.countDown() }
            )
    }
}
