package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.printWithName
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch
import kotlin.math.ceil

fun main() = runExample(Lift)

object Lift : Example {

    override val observable: Observable<Double> = randomPublisher()

    private fun countsDownOnLatchOperator(latch: CountDownLatch) =
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

    override fun invoke(latch: CountDownLatch?): CountDownLatch? {
        latch?.let {
            observable
                .lift(countsDownOnLatchOperator(latch))
                .map { ceil(it) }
                .subscribe(
                    { printWithName("onNext: $it") },
                    {
                        printWithName("onError: $it")
                        latch.countDown()
                    },
                    {
                        printWithName("onComplete!")
                        latch.countDown()
                    },
                    { printWithName("onSubscribe") }
                )
        }
        return latch
    }
}

// example output:
//[main]	onSubscribe
//[main]	onNext: 1.0
//[main]	onNext: 1.0
//[main]	onNext: 1.0
//[main]	onComplete!
