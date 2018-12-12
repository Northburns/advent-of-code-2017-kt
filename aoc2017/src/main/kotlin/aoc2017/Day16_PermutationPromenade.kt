package aoc2017

import aoc2017.common.Undefined
import aoc2017.common.rotate

class DanceLine(var line: List<String>) {

    constructor(dancersWithSingleLetterNames: String) : this(dancersWithSingleLetterNames.map { it.toString() })
    constructor() : this("abcdefghijklmnop")

    fun joinNames(separtor: String = "") = line.joinToString(separator = separtor)


    fun move(danceMove: DanceMove) {
        line = danceMove.execute(this)
    }

    fun move(danceMove: String) = move(DanceMove.fromString(danceMove))

    fun move(input: List<String>) = input.forEach(this::move)


}

sealed class DanceMove {

    companion object {
        fun fromString(s: String): DanceMove {
            return when {
                s.startsWith("s") -> {
                    Spin(s.substring(1).toInt())
                }
                s.startsWith("x") -> {
                    val (pos1, pos2) = s.substring(1).split("/").map { it.trim().toInt() }
                    Exchange(pos1, pos2)
                }
                s.startsWith("p") -> {
                    val (d1, d2) = s.substring(1).split("/").map { it.trim() }
                    Partner(d1, d2)
                }
                else -> throw Undefined("Unkown dance move: $s")
            }
        }

        fun fromStrings(input: List<String>) = input.map(this::fromString)
    }

    abstract fun execute(dancers: DanceLine): List<String>

    data class Spin(val amount: Int) : DanceMove() {
        override fun execute(dancers: DanceLine) = dancers.line.rotate(-amount)
    }

    data class Exchange(val pos1: Int, val pos2: Int) : DanceMove() {
        override fun execute(dancers: DanceLine): List<String> {
            return dancers.line.mapIndexed { i, d ->
                when (i) {
                    pos1 -> dancers.line[pos2]
                    pos2 -> dancers.line[pos1]
                    else -> d
                }
            }
        }
    }

    data class Partner(val dancer1: String, val dancer2: String) : DanceMove() {
        override fun execute(dancers: DanceLine): List<String> {
            return dancers.line.map { d ->
                when (d) {
                    dancer1 -> dancer2
                    dancer2 -> dancer1
                    else -> d
                }
            }
        }
    }


}