package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.ExampleSchedulers
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(FlatMap)

inline class User(val username: String)

object FlatMap : Example {

    private fun getUser() = Observable.just(
        User("takecare"),
        User("you"),
        User("another")
    )

    private fun getRepos(username: String) = Observable.just(
        "$username's 1st repository",
        "$username's 2nd repository",
        "$username's 3nd repository"
    )

    override val observable: Observable<String>
        get() =
            getUser()
                .subscribeOn(ExampleSchedulers.computation())
                .flatMap {
                    getRepos(it.username)
                        .subscribeOn(ExampleSchedulers.newThread())
                }
}

// example output:
//[RxNewThreadScheduler-1]    takecare's 1st repository
//[RxNewThreadScheduler-1]    takecare's 2nd repository
//[RxNewThreadScheduler-1]    takecare's 3nd repository
//[RxNewThreadScheduler-2]    you's 1st repository
//[RxNewThreadScheduler-2]    another's 1st repository
//[RxNewThreadScheduler-2]    another's 2nd repository
//[RxNewThreadScheduler-2]    another's 3nd repository
//[RxNewThreadScheduler-2]    you's 2nd repository
//[RxNewThreadScheduler-2]    you's 3nd repository