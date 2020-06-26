package org.vazteixeira.rui.rxjava

import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.CountDownLatch
import kotlin.math.ceil


object Lift : OldExample {
    override fun invoke(latch: CountDownLatch) {

        fun countsDownOnLatchOperator(latch: CountDownLatch) =
            ObservableOperator<Double, Double> { original ->
                object : Observer<Double> {
                    override fun onComplete() {
                        latch.countDown()
                        original.onComplete()
                    }

                    override fun onSubscribe(d: Disposable) {
                        original.onSubscribe(d)
                    }

                    override fun onNext(t: Double) {
                        original.onNext(t)
                    }

                    override fun onError(e: Throwable) {
                        original.onError(e)
                    }
                }
            }

        randomPublisher()
            .lift(countsDownOnLatchOperator(latch))
            .map { ceil(it) }
            .subscribeBy(
                onNext = { println("$it") },
                onComplete = { /* we don't want to countDown() here as the operator handles that */}
            )
    }
}
