package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {

    private val input = inputFile("day13.txt").trim()

    companion object {
        val sample = """
            |0: 3
            |1: 2
            |4: 4
            |6: 4""".trimMargin()
    }

    @Test
    fun part1() {
        assertEquals(24, sample.let(::toFirewall).stepThrough().second)
        printSolution(13, 1, input.let(::toFirewall).stepThrough().second, 1704)
    }

    @Test
    fun part2() {
        assertEquals(10, smallestDelayToPassWithoutBeingCaught(sample))
        printSolution(13, 2, smallestDelayToPassWithoutBeingCaught(input)!!, 3970918)
    }
}



