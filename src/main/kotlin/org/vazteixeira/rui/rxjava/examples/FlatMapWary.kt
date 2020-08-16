package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.ExampleSchedulers
import org.vazteixeira.rui.rxjava.Result
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(FlatMapWary)

object FlatMapWary : Example {

    private fun getSomething() = Observable.error<Result<String>>(Error("boom!"))
        .onErrorReturn { Result.Failure(it) }

    private fun getSomethingElse() = Observable.just<Result<String>>(
        Result.Success("something else")
    )

    override val observable: Observable<Result<String>> =
        getSomething()
            .subscribeOn(ExampleSchedulers.computation())
            .flatMap { getSomethingElse() }
}

// example output:
//[main]	onSubscribe
//[RxComputationThreadPool-1]	onNext: Success(data=something else)
//[RxComputationThreadPool-1]	onComplete!]
