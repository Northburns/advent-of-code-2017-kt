package aoc2018

import aoc2018.day1.frequencyCalc
import aoc2018.day1.frequencyCalc2
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    private val input = inputFile("day1.txt")
            .trim()
            .split(Regex("\\s"))
            .filter(String::isNotBlank)
            .map(String::toInt)

    @Test
    fun examplesPart1() {
        assertEquals(3, frequencyCalc(listOf(1, 1, 1)))
        assertEquals(0, frequencyCalc(listOf(1, 1, -2)))
        assertEquals(-6, frequencyCalc(listOf(-1, -2, -3)))
    }

    @Test
    fun solution1() {
        printSolution(1, 1, frequencyCalc(input), 490)
    }

    @Test
    fun examplesPart2() {
        assertEquals(0, frequencyCalc2(listOf(1, -1)))
        assertEquals(10, frequencyCalc2(listOf(3, 3, 4, -2, -4)))
        assertEquals(5, frequencyCalc2(listOf(-6, 3, 8, 5, -6)))
        assertEquals(14, frequencyCalc2(listOf(7, 7, -2, -7, -4)))
    }

    @Test
    fun solution2() {
        printSolution(1, 2, frequencyCalc2(input), 1177)
    }

}
