package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.OldExample
import org.vazteixeira.rui.rxjava.printWithName
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(SubscribeOnObserveOn)

object SubscribeOnObserveOn : OldExample {
    lateinit var disposable: Disposable

    //    val observable = Observable.fromArray("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val observable = Observable.create<String> { emitter ->
        printWithName("observable creation")
        (1..10).forEach { emitter.onNext("item $it") }
        emitter.onComplete()
    }

    override fun invoke(latch: CountDownLatch) {
        disposable = observable
            .subscribeOn(Schedulers.newThread())
            .doOnNext { printWithName(it) }
            .observeOn(Schedulers.newThread())
            .map {
                printWithName("mapping '$it'...")
                "_ $it _"
            }
            .observeOn(Schedulers.newThread())
            .doOnNext { printWithName("doOnNext: $it") }
            .doOnSubscribe { printWithName("doOnSubscribe") }
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
