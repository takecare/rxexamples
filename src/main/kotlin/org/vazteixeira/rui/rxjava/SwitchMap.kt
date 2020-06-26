package org.vazteixeira.rui.rxjava

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

fun main() = runExample(SwitchMap)

object SwitchMap : OldExample {
    override fun invoke(latch: CountDownLatch) {
        fun typingInSearchField() = Observable.just(
            "w", "wh", "wha", "what", "what ", "what i", "what is"
        )

        fun autocompleteSuggestionsFor(query: String) = Observable.just(
            "$query a", "$query b", "$query c", "$query d", "$query e", "$query f"
        )

        typingInSearchField()
            .switchMap {
                autocompleteSuggestionsFor(it)
                    .doOnDispose { println("disposed of '$it'") }
                    .subscribeOn(Schedulers.newThread())
            }
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { printWithName(it) },
                onComplete = { latch.countDown() }
            )
    }
}

// example output
//disposed of 'w'
//disposed of 'wh'
//disposed of 'wha'
//[RxNewThreadScheduler-2]    wh a
//[RxNewThreadScheduler-7]    what is a
//[RxNewThreadScheduler-7]    what is b
//[RxNewThreadScheduler-7]    what is c
//[RxNewThreadScheduler-7]    what is d
//[RxNewThreadScheduler-7]    what is e
//[RxNewThreadScheduler-7]    what is f
