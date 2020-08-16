package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(Reduce)

object Reduce : Example {

    override val observable: Observable<Double> =
        randomPublisher()
            .reduce { acc: Double, value: Double -> acc + value }
            .toObservable()
}
