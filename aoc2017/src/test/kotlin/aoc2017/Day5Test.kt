package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day5Test {

    private val input = inputFile("day5.txt").trim()
            .split('\n')
            .map(String::toInt)
            .toIntArray()

    @Test
    fun examplesPart1() {
        val t = Trampolines(intArrayOf(0, 3, 0, 1, -3))

        assertEquals(0, t.head)

        t.step()
        assertEquals(0, t.head)

        t.step()
        assertEquals(1, t.head)

        t.step()
        assertEquals(4, t.head)

        t.step()
        assertEquals(1, t.head)

        t.step()
        assertTrue(t.isOutside())

        printSolution(5, 1, Trampolines(input).apply { stepUntilOutside() }.steps, 373543)
    }

    @Test
    fun examplesPart2() {
        val t = createWierderTrampoline(intArrayOf(0, 3, 0, 1, -3))

        t.stepUntilOutside()
        assertEquals(10, t.steps)
        assertEquals(intArrayOf(2, 3, 2, 3, -1).toList(), t.jumps.toList())

        printSolution(5, 2, createWierderTrampoline(input).apply { stepUntilOutside() }.steps, 27502966)
    }


}
