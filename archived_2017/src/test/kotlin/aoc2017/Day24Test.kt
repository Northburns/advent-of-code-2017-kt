package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {

    private val input = inputFile("day24.txt")
            .trim()
            .lines()
            .map(BridgeComponent.Companion::fromString)

    @Test
    fun bridgeEnd() {
        assertEquals(1, Bridge.fromString("0/1").end)
        assertEquals(5, Bridge.fromString("0/2-2/2-2/3-3/5").end)
        assertEquals(10, Bridge.fromString("0/1-10/1").end)
        assertEquals(5, Bridge.fromString("0/2-3/2-3/3-3/5").end)
        assertEquals(5, Bridge.fromString("0/2-3/2-3/2-2/5").end)
    }

    @Test
    fun samplePart1() {
        val parts = """
            |0/2
            |2/2
            |2/3
            |3/4
            |3/5
            |0/1
            |10/1
            |9/10""".trimMargin().lines().map(BridgeComponent.Companion::fromString)

        val bridges = findValidBridges(parts)

        val expected = setOf(
                Bridge.fromString("0/1"),
                Bridge.fromString("0/1-10/1"),
                Bridge.fromString("0/1-10/1-9/10"),
                Bridge.fromString("0/2"),
                Bridge.fromString("0/2-2/3"),
                Bridge.fromString("0/2-2/3-3/4"),
                Bridge.fromString("0/2-2/3-3/5"),
                Bridge.fromString("0/2-2/2"),
                Bridge.fromString("0/2-2/2-2/3"),
                Bridge.fromString("0/2-2/2-2/3-3/4"),
                Bridge.fromString("0/2-2/2-2/3-3/5"))

        assertEquals(expected, bridges)

        val strongest = strongestBridge(bridges)

        assertEquals(31, strongest)
    }

    private fun strongestBridge(bridges: Collection<Bridge>) =
            bridges.maxBy(Bridge::strength)!!.strength

    @Test
    fun parts() {
        val bridges = findValidBridges(input)
        printSolution(24, 1, strongestBridge(bridges), 1859)

        val maxStrengthOfTheLongestOnes = bridges
                .groupBy { it.length }
                .let { strongestBridge(it[it.keys.max()]!!) }

        printSolution(24, 2, maxStrengthOfTheLongestOnes, 1799)
    }

}



