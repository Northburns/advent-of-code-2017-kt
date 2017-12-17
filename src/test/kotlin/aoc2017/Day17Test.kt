package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {

    private val input = inputFile("day17.txt").trim().toInt()

    @Test
    fun part1() {
        val sl = Spinlock(3)

        assertEquals("(0)", sl.toString())
        sl.insert()
        assertEquals("0 (1)", sl.toString())
        sl.insert()
        assertEquals("0 (2) 1", sl.toString())
        sl.insert()
        assertEquals("0  2 (3) 1", sl.toString())
        sl.insert()
        assertEquals("0  2 (4) 3  1", sl.toString())
        sl.insert()
        assertEquals("0 (5) 2  4  3  1", sl.toString())
        sl.insert()
        assertEquals("0  5  2  4  3 (6) 1", sl.toString())
        sl.insert()
        assertEquals("0  5 (7) 2  4  3  6  1", sl.toString())
        sl.insert()
        assertEquals("0  5  7  2  4  3 (8) 6  1", sl.toString())
        sl.insert()
        assertEquals("0 (9) 5  7  2  4  3  8  6  1", sl.toString())

        val sl2017 = Spinlock(3)
        sl2017.insert2017()
        assertEquals("1512  1134  151 (2017) 638  1513  851", sl2017.toStringSub(3, 3))

        val solution = Spinlock(input)
        solution.insert2017()
        val result = solution.buffer[solution.head + 1]
        printSolution(17, 1, result, 808)
    }

    @Test
    fun part2() {
        // Value 0 keeps at position 0, right? So we're interested in index 1.
        val solution = AngrySpinlock(input, 1)
        repeat(50_000_000) {
            solution.insert()
        }
        val result = solution.savedValue!!
        printSolution(17, 2, result, 47465686)
    }
}



