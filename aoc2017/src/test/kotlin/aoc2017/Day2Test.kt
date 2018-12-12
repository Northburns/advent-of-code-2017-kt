package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    private val input = inputFile("day2.txt")

    @Test
    fun examplesPart1() {
        val exampleInput = """
            |5 1 9 5
            |7 5 3
            |2 4 6 8
            """.trimMargin().replace(' ', '\t')

        val sheet = SpreadSheet(exampleInput)

        assertEquals(8, sheet.rows[0].checksum)
        assertEquals(4, sheet.rows[1].checksum)
        assertEquals(6, sheet.rows[2].checksum)
        assertEquals(18, sheet.checksum)

        printSolution(2, 1, SpreadSheet(input).checksum, 41919)
    }

    @Test
    fun examplesPart2() {
        val exampleInput = """
            |5 9 2 8
            |9 4 7 3
            |3 8 6 5
            """.trimMargin().replace(' ', '\t')

        val sheet = SpreadSheet(exampleInput)

        assertEquals(4, sheet.rows[0].divResult)
        assertEquals(3, sheet.rows[1].divResult)
        assertEquals(2, sheet.rows[2].divResult)
        assertEquals(9, sheet.divSum)

        printSolution(2, 2, SpreadSheet(input).divSum, 303)
    }

}
