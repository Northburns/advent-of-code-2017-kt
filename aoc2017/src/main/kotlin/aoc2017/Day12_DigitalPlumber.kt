package aoc2017

import aoc.common.Undefined

fun toPipes(input: String) = input
        .lines()
        .flatMap {
            val (from, to) = it.split("<->")
            val fromI = from.trim().toInt()
            to
                    .split(",")
                    .map { it.trim().toInt() }
                    .map { BidirectionalPipe(fromI, it) }
                    .toSet()
        }.toSet().let(::Plumbing)

data class BidirectionalPipe(val tube: Set<Int>) {

    constructor(end1: Int, end2: Int) : this(setOf(end1, end2))

    init {
        if (tube.size > 2 || tube.isEmpty()) throw Undefined("Tube must have 1-2 endpoints.")
    }

    /**
     * If this is a "boomerang-pipe", returns the same value as [thisEnd]
     */
    fun otherEnd(thisEnd: Int) = tube.find { it != thisEnd } ?: thisEnd

}

class Plumbing(val pipes: Set<BidirectionalPipe>) {

    fun reachable(from: Int): Set<Int> {

        val queue = mutableListOf(from)
        val queuedOrVisited = mutableSetOf(from)

        while (queue.isNotEmpty()) {

            val next = queue.removeAt(0)
            val neighbours = pipes
                    .filter { it.tube.contains(next) } // Find next's tubes
                    .map { it.otherEnd(next) } // Find the neighbours
                    .filter { it !in queuedOrVisited } // Don't evaluate any program ID twice
                    .toSet()

            queue.addAll(neighbours)
            queuedOrVisited.addAll(neighbours)
        }

        return queuedOrVisited
    }

    fun groups(): Set<Set<Int>> {
        val allPrograms = pipes.flatMap { it.tube }.toSet()
        return allPrograms.map { reachable(it) }.toSet()
    }


}
