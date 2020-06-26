package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.printWithName
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(SubscribeOn)

object SubscribeOn : Example {
    lateinit var disposable: Disposable
    val observable = Observable.create<String> { emitter ->
        printWithName("observable creation")
        (1..10).forEach { emitter.onNext("$it") }
        emitter.onComplete()
    }

    override fun invoke(latch: CountDownLatch) {
        disposable = observable
            .subscribeOn(Schedulers.computation())
            .map {
                printWithName("mapping...")
                "item $it"
            }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { printWithName("doOnSubscribe") }
            .doOnNext { printWithName("doOnNext") }
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { printWithName("onNext: $it") },
                { printWithName("onError: $it") },
                {
                    printWithName("onComplete!")
                    latch.countDown()
                },
                { printWithName("onSubscribe") }
            )
    }
}
