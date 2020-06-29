package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(OnErrorReturn)

object OnErrorReturn : Example {

    private fun getUser() = Observable.error<String>(Exception("boom!"))

    override val observable: Observable<String> =
        getUser()
            .onErrorReturn { error -> "default value" }
}

// example output:
//[main]	onSubscribe
//[main]	onNext: default value
//[main]	onComplete!
