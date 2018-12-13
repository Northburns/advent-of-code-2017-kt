package aoc2018

import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    private val input = inputFile("day2.txt").trim().lines()

    @Test
    fun examplesPart1() {
        assertEquals(12, day2Checksum(listOf(
                "abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab"
        )))
    }

    @Test
    fun solution1() {
        printSolution(2, 1, day2Checksum(input), 7105)
    }

    @Test
    fun examplesPart2() {
        val data = listOf(
                "abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz")
        assertEquals("fgij", day2findboxes(data))
    }

    @Test
    fun solution2() {
        printSolution(2, 2, day2findboxes(input), "")
    }

}
