package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {

    private val input = inputFile("day7.txt").trim()

    @Test
    fun examplesPart1() {

        val sampleInput = """
            |pbga (66)
            |xhth (57)
            |ebii (61)
            |havc (66)
            |ktlj (57)
            |fwft (72) -> ktlj, cntj, xhth
            |qoyq (66)
            |padx (45) -> pbga, havc, qoyq
            |tknk (41) -> ugml, padx, fwft
            |jptl (61)
            |ugml (68) -> gyxo, ebii, jptl
            |gyxo (61)
            |cntj (57)""".trimMargin()

        val t = Tower(sampleInput)
        val solution = Tower(input)

        assertEquals("tknk", t.rootProgram)

        println("Day 3, Part 1: ${solution.rootProgram}")

        val solutionDisc = solution.findDeepestUnbalancedDisk()
        val (program, weightAdjustment) = solutionDisc.validateWeights()!!
        val targetWeight = program.weight + weightAdjustment

        println("Day 3, Part 2: $targetWeight")
    }


}
