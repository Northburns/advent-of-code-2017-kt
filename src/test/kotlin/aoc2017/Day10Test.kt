package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    private val input = inputFile("day10.txt").trim()


    @Test
    fun examplesPart1() {
        val s = KnotHash(5, listOf(3, 4, 1, 5))

        assertState(0, 0, listOf(0, 1, 2, 3, 4), s)

        s.step()
        assertState(3, 1, listOf(2, 1, 0, 3, 4), s)

        s.step()
        assertState(3, 2, listOf(4, 3, 0, 1, 2), s)

        s.step()
        assertState(1, 3, listOf(4, 3, 0, 1, 2), s)

        s.step()
        assertState(4, 4, listOf(3, 4, 2, 1, 0), s)

        assertEquals(12, s.result())

        val solution = KnotHash(256, input.split(",").map { it.toInt() })
        val result = solution.stepToResultPart1()

        printSolution(10, 1, result, 40132)
    }

    @Test
    fun examplesPart2() {

        val knotHash = KnotHash(input)
        val solution = knotHash.stepToResultPart2()

        printSolution(10, 2, solution, "35b028fe2c958793f7d5a61d07a008c8")
    }

    companion object {
        fun assertState(position: Int, skipSize: Int, string: List<Int>, knot: KnotHash) {
            assertEquals(position, knot.position)
            assertEquals(skipSize, knot.skipSize)
            assertEquals(string, knot.string.toList())
        }
    }

}
