package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {

    private val input = inputFile("day19.txt")

    private val sample1 = """
        |     |
        |     |  +--+
        |     A  |  C
        | F---|----E|--+
        |     |  |  |  D
        |     +B-+  +--+
            """.trimMargin()

    @Test
    fun part1Sample() {
        val n = NetworkDiagram(sample1)
        val p = n.createPacket()
        p.stepThrough()
        assertEquals("ABCDEF", p.collectedLetters)
    }


    @Test
    fun part1() {
        val n = NetworkDiagram(input)
        val p = n.createPacket()
        p.stepThrough()
        printSolution(19, 1, p.collectedLetters, "YOHREPXWN")
        printSolution(19, 2, p.stepCount, 16734)
    }
}



