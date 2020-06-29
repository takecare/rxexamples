package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.runExample

fun main() =
    runExample(ErrorExample)

object ErrorExample : Example {
    private fun fail(msg: String): Observable<String> = Observable.fromCallable { throw Exception(msg) }

    override val observable: Observable<Pair<String, String>> =
        Observables.zip(
            fail("one"),
            fail("two")
        )
            .doOnError { println("error") }
}

// example output:
//[main]	onSubscribe
//error
//[main]	onError: java.lang.Exception: one
