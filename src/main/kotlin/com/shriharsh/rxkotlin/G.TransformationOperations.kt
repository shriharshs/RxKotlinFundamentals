package com.shriharsh.rxkotlin

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

fun main() {

    //Example of Map
    exampleOf("map") {

        val subscriptions = CompositeDisposable()

        subscriptions.add(
            Observable.fromIterable(episodes)
                .map {
                    val components = it.split(" ").toMutableList()
                    val number = components[1].romanNumeralIntValue()
                    val numberString = number.toString()
                    components[1] = numberString
                    components.joinToString(" ")
                }
                .subscribeBy {
                    println(it)
                })
    }

    //Example of flatMap
    exampleOf("flatMap") {

        val subscriptions = CompositeDisposable()

        val ryan = Jedi(BehaviorSubject.createDefault<JediRank>(JediRank.Youngling))
        val charlotte = Jedi(BehaviorSubject.createDefault<JediRank>(JediRank.Youngling))

        val student = PublishSubject.create<Jedi>()

        subscriptions.add(
            student
                .flatMap {
                    it.rank
                }
                .subscribeBy {
                    println(it)
                })

        student.onNext(ryan)

        ryan.rank.onNext(JediRank.Padawan)

        student.onNext(charlotte)

        ryan.rank.onNext(JediRank.JediKnight)

        charlotte.rank.onNext(JediRank.JediMaster)
    }

    //Example of switchMap
    exampleOf("switchMap") {

        val subscriptions = CompositeDisposable()

        val ryan = Jedi(BehaviorSubject.createDefault<JediRank>(JediRank.Youngling))
        val charlotte = Jedi(BehaviorSubject.createDefault<JediRank>(JediRank.Youngling))

        val student = PublishSubject.create<Jedi>()

        subscriptions.add(
            student
                .switchMap {
                    it.rank
                }
                .subscribeBy {
                    println(it)
                })

        student.onNext(ryan)

        ryan.rank.onNext(JediRank.Padawan)

        student.onNext(charlotte)

        ryan.rank.onNext(JediRank.JediKnight)

        charlotte.rank.onNext(JediRank.JediMaster)
    }

    //Example of Transformations challenge
    exampleOf("Challenge 1") {

        val subscriptions = CompositeDisposable()

        val contacts = mapOf(
            "603-555-1212" to "Florent",
            "212-555-1212" to "Junior",
            "408-555-1212" to "Marin",
            "617-555-1212" to "Scott")

        val convert: (String) -> Int = { value ->
            val number = try {
                value.toInt()
            } catch (e: NumberFormatException) {
                val keyMap = mapOf(
                    "abc" to 2, "def" to 3, "ghi" to 4, "jkl" to 5,
                    "mno" to 6, "pqrs" to 7, "tuv" to 8, "wxyz" to 9)

                keyMap.filter { it.key.contains(value.toLowerCase()) }.map { it.value }.first()
            }

            if (number < 10) {
                number
            } else {
                sentinel // RxJava 2 does not allow null in stream, so return sentinel value
            }
        }

        val format: (List<Int>) -> String = { inputs ->
            val phone = inputs.map { it.toString() }.toMutableList()
            phone.add(3, "-")
            phone.add(7, "-")
            phone.joinToString("")
        }

        val dial: (String) -> String = { phone ->
            val contact = contacts[phone]
            if (contact != null) {
                "Dialing $contact ($phone)..."
            } else {
                "Contact not found"
            }
        }

        val input = BehaviorSubject.createDefault<String>("$sentinel")

        // Add your code here

        subscriptions.add(
            input
                .map(convert)
                .filter { it != sentinel }
                .skipWhile { it == 0 }
                .take(10)
                .toList()
                .map(format)
                .map(dial)
                .subscribeBy(onSuccess = {
                    println(it)
                }, onError = {
                    println(it)
                }))

        input.onNext("617")
        input.onNext("0")
        input.onNext("408")

        input.onNext("6")
        input.onNext("212")
        input.onNext("0")
        input.onNext("3")

        "JKL1A1B".forEach {
            input.onNext(it.toString()) // Need toString() or else Char conversion is done
        }

        input.onNext("9")
    }

}