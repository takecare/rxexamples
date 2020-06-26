package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.printWithName
import org.vazteixeira.rui.rxjava.runExample
import java.util.concurrent.CountDownLatch

fun main() = runExample(ConcatMap)

object ConcatMap : Example {

    override fun invoke(latch: CountDownLatch) {
        fun getUser() = Observable.just(
            User("takecare"),
            User("you"),
            User("another")
        )

        fun getRepos(username: String) = Observable.just(
            "$username's 1st repository",
            "$username's 2nd repository",
            "$username's 3nd repository"
        )

        getUser()
            .subscribeOn(Schedulers.computation())
            .concatMap {
                getRepos(it.username)
                    .subscribeOn(Schedulers.newThread())
            }
            .subscribeBy(
                onNext = { printWithName(it) },
                onComplete = { latch.countDown() }
            )
    }
}

// example output:
//[RxNewThreadScheduler-1]    takecare's 1st repository
//[RxNewThreadScheduler-1]    takecare's 2nd repository
//[RxNewThreadScheduler-1]    takecare's 3nd repository
//[RxNewThreadScheduler-2]    you's 1st repository
//[RxNewThreadScheduler-2]    you's 2nd repository
//[RxNewThreadScheduler-2]    you's 3nd repository
//[RxNewThreadScheduler-3]    another's 1st repository
//[RxNewThreadScheduler-3]    another's 2nd repository
//[RxNewThreadScheduler-3]    another's 3nd repository
