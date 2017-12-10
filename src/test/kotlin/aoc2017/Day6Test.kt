package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {

    private val input = inputFile("day6.txt").trim()
            .split(Regex("\\s+"))
            .map(String::toInt)
            .toList()

    @Test
    fun examplesSolutions() {
        var m = Memory(listOf(0, 2, 7, 0))

        assertEquals(2, m.bankWithMostBlocks())
        m = m.redistribute()
        assertEquals(listOf(2, 4, 1, 2), m.memoryBlockCounts())

        assertEquals(1, m.bankWithMostBlocks())
        m = m.redistribute()
        assertEquals(listOf(3, 1, 2, 3), m.memoryBlockCounts())

        assertEquals(0, m.bankWithMostBlocks())
        m = m.redistribute()
        assertEquals(listOf(0, 2, 3, 4), m.memoryBlockCounts())

        assertEquals(3, m.bankWithMostBlocks())
        m = m.redistribute()
        assertEquals(listOf(1, 3, 4, 1), m.memoryBlockCounts())

        assertEquals(2, m.bankWithMostBlocks())
        m = m.redistribute()
        assertEquals(listOf(2, 4, 1, 2), m.memoryBlockCounts())

        val (history, loopIndex) = Memory(input).redistributionHistoryUntilLoops()

        printSolution(6, 1, history.last().redisributeCounter, 12841)
        printSolution(6, 2, history.size - loopIndex - 1, 8038)

        assertEquals(12841, history.last().redisributeCounter)
    }

}
