package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.Result
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.TimeUnit

fun main() = runExample(RetryWhen)

private const val NUM_OF_RETRIES = 3

object RetryWhen : Example {

    private var counter = 0

    private fun getData(): Observable<String> = Observable.fromCallable {
        if (counter == 3) {
            return@fromCallable "Data" // throw Exception("BOOM!")
        }
        counter += 1
        throw Exception("Error!!")
    }

    private fun fails() = Observable.fromCallable { throw Exception("BOOM") }

    private fun mappedData(): Observable<Result<String>> = getData()
        .map { Result.Success(it) as Result<String> }
        .onErrorReturn { Result.Failure(it) }

    override val observable: Observable<Result<String>>
        get() {
            val data: Observable<Result<String>> = mappedData()
                .flatMap { result ->
                    when (result) {
                        is Result.Success -> Observable.just(result)
                        is Result.Failure -> throw Exception(result.error)
                    }
                }

            return data
                .retryWhen { errors ->
                    errors
                        .zipWith(Observable.range(1, NUM_OF_RETRIES + 1)) { error, attempt ->
                            if (attempt == NUM_OF_RETRIES + 1) {
                                throw error
                            }
                            attempt
                        }
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .doOnNext { println("> attempt #$it") }
                }
                .onErrorReturn { Result.Failure(it) }
        }
}
