package org.vazteixeira.rui.rxjava

import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

object Compose : OldExample {
    val transformer: ObservableTransformer<Double, Double> = ObservableTransformer { upstream ->
        upstream // a transformer that sets up schedulers
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.single())
    }

    override fun invoke(latch: CountDownLatch) {
        randomPublisher()
            .compose(transformer)
            .subscribeBy(
                onNext = { println("$it") },
                onComplete = { latch.countDown() }
            )
    }
}
