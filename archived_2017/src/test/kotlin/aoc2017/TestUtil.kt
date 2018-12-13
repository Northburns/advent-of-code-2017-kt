package aoc

import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun inputFile(fileName: String): String =
        ClassLoader
                .getSystemResource("$fileName")
                .readText()

fun <T : Any> printSolution(day: Int, part: Int, solution: T, assert: T? = null) {
    println("Day $day, Part $part: $solution")
    assertNotNull(solution)
    assertEquals(assert, solution)
}

fun <T, U> withFunction(
        function: (T) -> U,
        block: FunctionAsserter<T, U>.() -> Unit) {
    FunctionAsserter(function).block()
}

class FunctionAsserter<T, U>(
        private val function: (T) -> U) {

    fun eq(expected: U, input: T) {
        assertEquals(expected, function(input))
    }
}
