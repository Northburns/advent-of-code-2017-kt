package aoc2018

import aoc.inputFile
import aoc2018.day1.frequencyCalc
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    private val input = inputFile("day1.txt").trim().split(Regex("\\s")).map { it.toInt() }

    @Test
    fun examplesPart1() {
        assertEquals(3, frequencyCalc(listOf(1, 1, 1)))
        assertEquals(0, frequencyCalc(listOf(1, 1, -2)))
        assertEquals(-6, frequencyCalc(listOf(-1, -2, -3)))
    }
//    printSolution(1, 1, f1(input), 1177)

    @Test
    fun examplesPart2() {
//        printSolution(1, 2, f2(input), 1060)
    }

}
