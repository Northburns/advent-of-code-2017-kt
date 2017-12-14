package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {

    private val input = inputFile("day14.txt").trim()
    private val sample = "flqrgnkx"

    @Test
    fun part1() {
        assertEquals(8108, Disk(sample).countSquares())
        printSolution(14, 1, Disk(input).countSquares(), 8316)
    }

    @Test
    fun part2() {
        assertEquals(1242, Disk(sample).countRegions())
        printSolution(14, 1, Disk(input).countRegions(), 1074)
    }
}



