package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(Compose)

object Compose : Example {
    private val transformer: ObservableTransformer<Double, Double> = ObservableTransformer { upstream ->
        upstream // a transformer that sets up schedulers
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.single())
    }

    override val observable: Observable<Double> =
        randomPublisher()
            .compose(transformer)
}

// example output:
//[main]	onSubscribe
//[RxSingleScheduler-1]	onNext: 0.019256514192206087
//[RxSingleScheduler-1]	onNext: 0.7242660397324459
//[RxSingleScheduler-1]	onNext: 0.08487434830587381
//[RxSingleScheduler-1]	onNext: 0.9963592506283532
//[RxSingleScheduler-1]	onComplete!
