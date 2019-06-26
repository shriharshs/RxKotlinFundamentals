package com.shriharsh.rxkotlin

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

fun main() {

    //Example of ignoreElements
    exampleOf("ignoreElements") {

        val subscriptions = CompositeDisposable()

        val cannedProjects = PublishSubject.create<String>()

        subscriptions.add(
            cannedProjects.ignoreElements() // Returns a Completable, so no onNext in subscribeBy
                .subscribeBy {
                    println("Completed")
                })

        cannedProjects.onNext(landOfDroids)
        cannedProjects.onNext(wookieWorld)
        cannedProjects.onNext(detours)

        cannedProjects.onComplete()
    }

    //Example of elementAt
    exampleOf("elementAt") {

        val subscriptions = CompositeDisposable()

        val quotes = PublishSubject.create<String>()

        subscriptions.add(
            quotes.elementAt(2) // Returns a Maybe, subscribe with onSuccess instead of onNext
                .subscribeBy(
                    onSuccess = { println(it) },
                    onComplete = { println("Completed") }
                ))

        quotes.onNext(mayTheOdds)
        quotes.onNext(liveLongAndProsper)
        quotes.onNext(mayTheForce)
    }

    //Example of filter
    exampleOf("filter") {

        val subscriptions = CompositeDisposable()

        subscriptions.add(
            Observable.fromIterable(tomatometerRatings)
                .filter { movie ->
                    movie.rating >= 90
                }.subscribe {
                    println(it)
                })
    }

    //Example of skipWhile
    exampleOf("skipWhile") {

        val subscriptions = CompositeDisposable()

        subscriptions.add(
            Observable.fromIterable(tomatometerRatings)
                .skipWhile { movie ->
                    movie.rating < 90
                }.subscribe {
                    println(it)
                })

    }

    //Example of skipUntil
    exampleOf("skipUntil") {

        val subscriptions = CompositeDisposable()

        val subject = PublishSubject.create<String>()
        val trigger = PublishSubject.create<Unit>()

        subscriptions.add(
            subject.skipUntil(trigger)
                .subscribe {
                    println(it)
                })

        subject.onNext(movieEpisodeI.title)
        subject.onNext(movieEpisodeII.title)
        subject.onNext(movieEpisodeIII.title)

        trigger.onNext(Unit)

        subject.onNext(movieEpisodeIV.title)
    }

    //Example of distinctUntilChanged
    exampleOf("distinctUntilChanged") {

        val subscriptions = CompositeDisposable()

        subscriptions.add(
            Observable.just(DroidEnum.R2D2, DroidEnum.C3PO, DroidEnum.C3PO, DroidEnum.R2D2)
                .distinctUntilChanged()
                .subscribe {
                    println(it)
                })
    }

    //Filtering challenge
    exampleOf("Challenge 1") {

        val subscriptions = CompositeDisposable()

        val contacts = mapOf(
            "603-555-1212" to "Florent",
            "212-555-1212" to "Junior",
            "408-555-1212" to "Marin",
            "617-555-1212" to "Scott")

        fun phoneNumberFrom(inputs: List<Int>): String {
            val phone = inputs.map { it.toString() }.toMutableList()
            phone.add(3, "-")
            phone.add(7, "-")
            return phone.joinToString("")
        }

        val input = PublishSubject.create<Int>()

        // Add your code here

        subscriptions.add(
            input
                .skipWhile { it == 0 }
                .filter { it < 10 }
                .take(10)
                .toList() // Returns a Single
                .subscribeBy( onSuccess = {
                    val phone = phoneNumberFrom(it)
                    val contact = contacts[phone]
                    if (contact != null) {
                        println("Dialing $contact ($phone)...")
                    } else {
                        println("Contact not found")
                    }
                }))

        input.onNext(0)
        input.onNext(603) // this greater than 10

        input.onNext(2)
        input.onNext(1)

        // Confirm that 7 results in "Contact not found", and then change to 2 and confirm that Junior is found
        input.onNext(2)

        "5551212".forEach {
            input.onNext(it.toString().toInt()) // Need toString() or else Char conversion is done
        }

        input.onNext(9)
    }

    //Checkout for scheduler, timing operators like debounce and others etc

}