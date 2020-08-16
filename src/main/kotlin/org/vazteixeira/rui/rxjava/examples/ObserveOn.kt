package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(ObserveOn)

object ObserveOn : Example {

    override val observable: Observable<Double>
        get() = randomPublisher()
            .observeOn(Schedulers.computation())
            .doOnNext { println("Observed $it on ${Thread.currentThread().name}") }
            .observeOn(Schedulers.io())
}

// example output:
//[main]	onSubscribe
//Observed 0.9337338178031138 on RxComputationThreadPool-1
//[RxCachedThreadScheduler-1]	onNext: 0.9337338178031138
//Observed 0.7331414459085989 on RxComputationThreadPool-1
//[RxCachedThreadScheduler-1]	onNext: 0.7331414459085989
//Observed 0.7114100841726366 on RxComputationThreadPool-1
//[RxCachedThreadScheduler-1]	onNext: 0.7114100841726366
//Observed 0.5531584765575804 on RxComputationThreadPool-1
//[RxCachedThreadScheduler-1]	onNext: 0.5531584765575804
//Observed 0.9947283306562356 on RxComputationThreadPool-1
//[RxCachedThreadScheduler-1]	onNext: 0.9947283306562356
//[RxCachedThreadScheduler-1]	onComplete!