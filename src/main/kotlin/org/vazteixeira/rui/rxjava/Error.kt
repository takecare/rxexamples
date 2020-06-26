package org.vazteixeira.rui.rxjava

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

fun main() = runExample(ErrorExample)

object ErrorExample : Example {
    override fun invoke(latch: CountDownLatch) {
        fun fail(msg: String) = Observable.fromCallable { throw Exception(msg) }
        Observables.zip(fail("one"), fail("two"))
            .subscribeOn(Schedulers.computation())
            .doOnError { latch.countDown() }
            .subscribeBy(
                onError = { println(it) },
                onComplete = { println("not called") }
            )
    }
}
