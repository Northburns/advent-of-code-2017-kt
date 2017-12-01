package aoc2017

import kotlin.test.assertEquals

fun inputFile(fileName: String): String =
        ClassLoader
                .getSystemResource("aoc2017/$fileName")
                .readText()
                .trim()

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
