package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(GlobalErrorHandler)

object GlobalErrorHandler : Example {
    private fun getUser() = Observable.just(
        User("takecare"),
        User("you"),
        User("another")
    )

    private fun getRepos(username: String) = Observable.create<String> { emitter ->
        emitter.onNext("$username's 1st repository")
        emitter.onNext("$username's 2nd repository")
        throw Exception("boom!")
    }

    override val observable: Observable<String>
        get() {
            RxJavaPlugins.setErrorHandler { error -> println("UNHANDLED ERROR: $error") }
            return getUser()
                .subscribeOn(Schedulers.computation())
                .flatMap {
                    getRepos(it.username)
                        .subscribeOn(Schedulers.newThread())
                }
        }
}

// example output w/o RxJavaPlugins.setErrorHandler() (notice the unhandled crash)
//[RxNewThreadScheduler-1]    takecare's 1st repository
//[RxNewThreadScheduler-1]    takecare's 2nd repository
//[RxNewThreadScheduler-2]    you's 1st repository
//[RxNewThreadScheduler-2]    another's 1st repository
//[RxNewThreadScheduler-2]    another's 2nd repository
//[RxNewThreadScheduler-2]    you's 2nd repository
//ERROR: java.lang.Exception: boom!
//io.reactivex.exceptions.UndeliverableException: The exception could not be delivered to the consumer because it has
// already canceled/disposed the flow or the exception has nowhere to go to begin with. Further reading:
// https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling | java.lang.Exception: boom!
//at io.reactivex.plugins.RxJavaPlugins.onError(RxJavaPlugins.java:367)

// example output w/ RxJavaPlugins.setErrorHandler() (notice the crash is now handled)
//[RxNewThreadScheduler-1]    takecare's 1st repository
//[RxNewThreadScheduler-1]    takecare's 2nd repository
//[RxNewThreadScheduler-2]    you's 1st repository
//[RxNewThreadScheduler-2]    another's 1st repository
//[RxNewThreadScheduler-2]    another's 2nd repository
//[RxNewThreadScheduler-2]    you's 2nd repository
//ERROR: java.lang.Exception: boom!
//UNHANDLED ERROR: io.reactivex.exceptions.UndeliverableException: The exception could not be delivered to the consumer
// because it has already canceled/disposed the flow or the exception has nowhere to go to begin with.
// Further reading: https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0#error-handling |
// java.lang.Exception: boom!
