package org.vazteixeira.rui.rxjava.examples

import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.vazteixeira.rui.rxjava.Example
import org.vazteixeira.rui.rxjava.randomPublisher
import org.vazteixeira.rui.rxjava.runExample

fun main() = runExample(Materialize)

object Materialize : Example {

    override val observable: Observable<Notification<Double>>
        get() = randomPublisher()
            .subscribeOn(Schedulers.computation())
            .materialize()
}

// example output:
//[main]	onSubscribe
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.38068823039620736]
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.43905118589802217]
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.5214536881436441]
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.4313906805726886]
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.062084316294772846]
//[RxComputationThreadPool-1]	onNext: OnNextNotification[0.9995650745852004]
//[RxComputationThreadPool-1]	onNext: OnCompleteNotification
//[RxComputationThreadPool-1]	onComplete!
