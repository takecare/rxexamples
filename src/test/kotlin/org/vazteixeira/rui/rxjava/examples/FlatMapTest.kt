package org.vazteixeira.rui.rxjava.examples

import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.vazteixeira.rui.rxjava.ExampleSchedulers

class FlatMapTest {

    @Before
    fun setUp() {
        ExampleSchedulers.computation = { Schedulers.trampoline() }
        ExampleSchedulers.newThread = { Schedulers.trampoline() }
    }

    @Test
    fun `it completes`() {
        val testObserver = TestObserver<String>()

        FlatMap.observable()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.assertComplete()
    }
}
