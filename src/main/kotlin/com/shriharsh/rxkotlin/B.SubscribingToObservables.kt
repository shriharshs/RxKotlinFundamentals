package com.shriharsh.rxkotlin

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import java.io.File

sealed class Droid : Throwable() {
    class OU812 : Droid()
}

sealed class FileReadError : Throwable() {
    class FileNotFound : FileReadError()
}

fun main() {

    //Example 1
    exampleOf("subscribe") {

        val observable = Observable.just(episodeIV, episodeV, episodeVI)

        observable.subscribeBy(
            onNext = {
                println(it)
            }, onComplete = {
                println("Completed")
            })


        println("\n--- Another Subscriber ---")

        observable.subscribe { element ->
            println(element)
        }
    }

    //Example 2
    exampleOf("empty") {

        val observable = Observable.empty<Unit>()

        observable.subscribeBy(
            onNext = {
                println(it)
            }, onComplete = {
                println("Completed")
            })
    }

    //Example 3
    exampleOf("never") {

        val observable = Observable.never<Any>()

        observable.subscribeBy(
            onNext = {
                println(it)
            }, onComplete = {
                println("Completed")
            })
    }

    //Example 4
    exampleOf("dispose") {

        val mostPopular: Observable<String> = Observable.just(episodeV, episodeIV, episodeVI)

        val subscription = mostPopular.subscribe {
            println(it)
        }

        subscription.dispose()
    }

    //Example 5
    exampleOf("CompositeDisposable") {

        val subscriptions = CompositeDisposable()

        subscriptions.add(listOf(episodeVII, episodeI, rogueOne)
            .toObservable()
            .subscribe {
                println(it)
            })

        subscriptions.dispose()
    }

    //Example 6
    exampleOf("create") {

        val subscriptions = CompositeDisposable()

        val droids = Observable.create<String> { emitter ->
            emitter.onNext("R2-D2")
            emitter.onNext("C-3PO")
            emitter.onNext("K-2SO")
            emitter.onComplete()
        }

        val observer = droids.subscribeBy(
            onNext = { println(it) },
            onError = { println("Error, $it") },
            onComplete = { println("Completed") })

        subscriptions.add(observer)
    }

    //Example 7
    exampleOf("Single") {
        val subscriptions = CompositeDisposable()

        fun loadText(filename: String): Single<String> {
            return Single.create create@{ emitter ->
                val file = File(filename)

                if (!file.exists()) {
                    emitter.onError(FileReadError.FileNotFound())
                    return@create
                }

                val contents = file.readText(Charsets.UTF_8)

                emitter.onSuccess(contents)
            }
        }

        val observer = loadText("ANewHope.txt")
            .subscribe({
                println(it)
            }, {
                println("Error, $it")
            })

        subscriptions.add(observer)
    }

}