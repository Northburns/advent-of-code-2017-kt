package aoc2017

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Test {

    private val input = inputFile("day4.txt").trim().split('\n')

    @Test
    fun examplesPart1() {
        assertTrue(isValidPassphrase1("aa bb cc dd ee"))
        assertFalse(isValidPassphrase1("aa bb cc dd aa"))
        assertTrue(isValidPassphrase1("aa bb cc dd aaa"))

        println("Day 3, Part 1: ${input.filter(::isValidPassphrase1).count()}")
    }

    @Test
    fun examplesPart2() {
        assertTrue(isValidPassphrase2("abcde fghij"))
        assertFalse(isValidPassphrase2("abcde xyz ecdab"))
        assertTrue(isValidPassphrase2("a ab abc abd abf abj"))
        assertTrue(isValidPassphrase2("iiii oiii ooii oooi oooo"))
        assertFalse(isValidPassphrase2("oiii ioii iioi iiio"))

        println("Day 3, Part 1: ${input.filter(::isValidPassphrase2).count()}")
    }


}
