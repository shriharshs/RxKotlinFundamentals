package com.shriharsh.rxkotlin

import io.reactivex.subjects.BehaviorSubject
import java.util.*

fun exampleOf(description: String, action: () -> Unit) {
  println("\n~~~~~~~~ Example of: $description ~~~~~~~~")
  action()
}

fun <T> printWithLabel(label: String, element: T?) {
  println("$label $element")
}

sealed class Quote: Throwable() {
  class NeverSaidThat : Quote()
}

const val episodeI = "The Phantom Menace"
const val episodeII = "Attack of the Clones"
const val theCloneWars = "The Clone Wars"
const val episodeIII = "Revenge of the Sith"
const val solo = "Solo: A Star Wars Story"
const val rogueOne = "Rogue One: A Star Wars Story"
const val episodeIV = "A New Hope"
const val episodeV = "The Empire Strikes Back"
const val episodeVI = "Return of the Jedi"
const val episodeVII = "The Force Awakens"
const val episodeVIII = "The Last Jedi"
const val episodeIX = "Episode IX"

const val itsNotMyFault = "It’s not my fault."
const val doOrDoNot = "Do. Or do not. There is no try."
const val lackOfFaith = "I find your lack of faith disturbing."
const val eyesCanDeceive = "Your eyes can deceive you. Don’t trust them."
const val stayOnTarget = "Stay on target."
const val iAmYourFather = "Luke, I am your father"
const val useTheForce = "Use the Force, Luke."
const val theForceIsStrong = "The Force is strong with this one."
const val mayTheForceBeWithYou = "May the Force be with you."
const val mayThe4thBeWithYou = "May the 4th be with you."

val cards = mutableListOf(
  Pair("🂡", 11), Pair("🂢", 2), Pair("🂣", 3), Pair("🂤", 4), Pair("🂥", 5), Pair("🂦", 6), Pair("🂧", 7),
  Pair("🂨", 8), Pair("🂩", 9), Pair("🂪", 10), Pair("🂫", 10), Pair("🂭", 10), Pair("🂮", 10),
  Pair("🂱", 11), Pair("🂲", 2), Pair("🂳", 3), Pair("🂴", 4), Pair("🂵", 5), Pair("🂶", 6), Pair("🂷", 7),
  Pair("🂸", 8), Pair("🂹", 9), Pair("🂺", 10), Pair("🂻", 10), Pair("🂽", 10), Pair("🂾", 10),
  Pair("🃁", 11), Pair("🃂", 2), Pair("🃃", 3), Pair("🃄", 4), Pair("🃅", 5), Pair("🃆", 6), Pair("🃇", 7),
  Pair("🃈", 8), Pair("🃉", 9), Pair("🃊", 10), Pair("🃋", 10), Pair("🃍", 10), Pair("🃎", 10),
  Pair("🃑", 11), Pair("🃒", 2), Pair("🃓", 3), Pair("🃔", 4), Pair("🃕", 5), Pair("🃖", 6), Pair("🃗", 7),
  Pair("🃘", 8), Pair("🃙", 9), Pair("🃚", 10), Pair("🃛", 10), Pair("🃝", 10), Pair("🃞", 10)
)

fun cardString(hand: List<Pair<String, Int>>): String {
  return hand.joinToString("") { it.first }
}

fun points(hand: List<Pair<String, Int>>) = hand.map { it.second }.fold(0) { s, a -> s + a }

fun IntRange.random() = Random().nextInt(endInclusive - start) +  start

sealed class HandError: Throwable() {
  class Busted: HandError()
}

const val landOfDroids = "Land of Droids"
const val wookieWorld = "Wookie World"
const val detours = "Detours"

const val mayTheOdds = "And may the odds be ever in your favor"
const val liveLongAndProsper = "Live long and prosper"
const val mayTheForce = "May the Force be with you"

data class Movie(val title: String, val rating: Int)

val movieEpisodeI = Movie("The Phantom Menace", 55)
val movieEpisodeII = Movie("Attack of the Clones", 66)
val movieEpisodeIII = Movie("Revenge of the Sith", 79)
val movieEogueOne = Movie("Rogue One", 85)
val movieEpisodeIV = Movie("A New Hope", 93)
val movieEpisodeV = Movie("The Empire Strikes Back", 94)
val movieEpisodeVI = Movie("Return Of The Jedi", 80)
val movieEpisodeVII = Movie("The Force Awakens", 93)
val movieEpisodeVIII = Movie("The Last Jedi", 91)
val tomatometerRatings = listOf(
  movieEpisodeI, movieEpisodeII, movieEpisodeIII, movieEogueOne, movieEpisodeIV, movieEpisodeV, movieEpisodeVI, movieEpisodeVII, movieEpisodeVIII)

enum class DroidEnum {
  C3PO, R2D2
}

const val mapEpisodeI = "Episode I - The Phantom Menace"
const val mapEpisodeII = "Episode II - Attack of the Clones"
const val mapEpisodeIII = "Episode III - Revenge of the Sith"
const val mapEpisodeIV = "Episode IV - A New Hope"
const val mapEpisodeV = "Episode V - The Empire Strikes Back"
const val mapEpisodeVI = "Episode VI - Return Of The Jedi"
const val mapEpisodeVII = "Episode VII - The Force Awakens"
const val mapEpisodeVIII = "Episode VIII - The Last Jedi"
const val mapEpisodeIX = "Episode IX - Episode IX"

val episodes = listOf(mapEpisodeI, mapEpisodeII, mapEpisodeIII, mapEpisodeIV, mapEpisodeV, mapEpisodeVI, mapEpisodeVII, mapEpisodeVIII, mapEpisodeIX)

fun String.romanNumeralIntValue(): Int {

  val lookup: Map<Char, Int> = mapOf(
    Pair('I', 1),
    Pair('V', 5),
    Pair('X', 10),
    Pair('L', 50),
    Pair('C', 100),
    Pair('D', 500),
    Pair('M', 1000)
  )

  fun accumulate(currentValue: Int?, lastValueAndTotal: Pair<Int, Int>): Pair<Int, Int> {
    if (currentValue == null) return lastValueAndTotal
    val mult = if (lastValueAndTotal.first > currentValue) -1 else 1
    return Pair(currentValue, lastValueAndTotal.second + (mult * currentValue))
  }

  return this.map { lookup[it] }
    .foldRight(Pair(0, 0), ::accumulate)
    .second
}

data class Jedi(val rank: BehaviorSubject<JediRank>)

enum class JediRank(val value: String) {
  Youngling("Youngling"),
  Padawan("Padawan"),
  JediKnight("Jedi Knight"),
  JediMaster("Jedi Master"),
  JediGrandMaster("Jedi Grand Master")
}

const val sentinel = -1

const val luke = "Luke Skywalker"
const val hanSolo = "Han Solo"
const val leia = "Princess Leia"
const val chewbacca = "Chewbacca"

const val lightsaber = "Lightsaber"
const val dl44 = "DL-44 Blaster"
const val defender = "Defender Sporting Blaster"
const val bowcaster = "Bowcaster"

val runtimes = mapOf(
  episodeI to 136,
  episodeII to 142,
  theCloneWars to 98,
  episodeIII to 140,
  solo to 142,
  rogueOne to 142,
  episodeIV to 121,
  episodeV to 124,
  episodeVI to 134,
  episodeVII to 136,
  episodeVIII to 152
)

fun stringFrom(minutes: Int): String {
  val hours = minutes / 60
  val min = minutes % 60
  return String.format("%d:%02d", hours, min)
}