package aoc2018

import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun inputFile(fileName: String): String =
        ClassLoader.getSystemResource(fileName)
                .readText()

fun <T : Any> printSolution(day: Int, part: Int, solution: T, assert: T? = null) {
    println("Day $day, Part $part: $solution")
    assertNotNull(solution)
    assertEquals(assert, solution)
}
