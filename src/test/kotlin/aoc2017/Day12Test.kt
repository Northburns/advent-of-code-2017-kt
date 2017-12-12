package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {

    private val input = inputFile("day12.txt").trim().let(::toPipes)

    companion object {
        val sample = """
            |0 <-> 2
            |1 <-> 1
            |2 <-> 0, 3, 4
            |3 <-> 2, 4
            |4 <-> 2, 3, 6
            |5 <-> 6
            |6 <-> 4, 5""".trimMargin().let(::toPipes)
    }

    @Test
    fun part1() {
        assertEquals(6, sample.reachable(0).size)
        printSolution(12, 1, input.reachable(0).size, 288)
    }

    @Test
    fun part2() {
        assertEquals(2, sample.groups().size)
        printSolution(12, 2, input.groups().size, 211)
    }
}



