package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Observable
import org.vazteixeira.rui.rxjava.NewExample
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(OnErrorReturn)

object OnErrorReturn : NewExample {

    override fun observable(): Observable<*> {
        fun getUser() = Observable.error<String>(Exception("boom!"))

        return getUser()
            .onErrorReturn { error -> "default value" }
    }
}
