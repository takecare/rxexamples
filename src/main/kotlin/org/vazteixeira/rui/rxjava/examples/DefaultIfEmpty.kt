package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(DefaultIfEmpty)

object DefaultIfEmpty : Example {

    override val observable: Observable<List<Double>>
        get() = Observable.create<List<Double>> { emitter -> emitter.onComplete() }
            .defaultIfEmpty(listOf(9.99, 99.99))
}

// example output:
//[main]	onSubscribe
//[main]	onNext: [9.99, 99.99]
//[main]	onComplete!
