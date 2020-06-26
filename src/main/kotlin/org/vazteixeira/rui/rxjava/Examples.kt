package org.vazteixeira.rui.rxjava

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Deprecated("Use [NewExample] instead", ReplaceWith("NewExample"))
typealias OldExample = (CountDownLatch) -> Unit // CountDownLatch

@Deprecated("Use [NewExample] instead")
fun runExample(example: OldExample) {
    val latch = CountDownLatch(1)
    example(latch)
    latch.await(15, TimeUnit.SECONDS)
}

interface Example {

    fun observable(): Observable<*>

    operator fun invoke(latch: CountDownLatch? = null): CountDownLatch? {
        observable()
            .subscribe(
                { printWithName("onNext: $it") },
                {
                    printWithName("onError: $it")
                    latch?.countDown()
                },
                {
                    printWithName("onComplete!")
                    latch?.countDown()
                },
                { printWithName("onSubscribe") }
            )
        return latch
    }
}

fun runExample(example: Example) {
    example(CountDownLatch(1))
        ?.await(15, TimeUnit.SECONDS)
}

object ExampleSchedulers {
    var io = { Schedulers.io() }
    var newThread = { Schedulers.newThread() }
    var computation = { Schedulers.computation() }
}
