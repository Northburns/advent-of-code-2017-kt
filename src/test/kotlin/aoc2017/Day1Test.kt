package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    private val f1 = { input: String -> reverseCaptchaSum(input) }
    private val f2 = ::reverseCaptchaSumHalfway

    @Test
    fun examplesPart1() {
        assertEquals(3, f1("1122"))
        assertEquals(4, f1("1111"))
        assertEquals(0, f1("1234"))
        assertEquals(9, f1("91212129"))
    }

    @Test
    fun examplesPart2() {
        assertEquals(6, f2("1212"))
        assertEquals(0, f2("1221"))
        assertEquals(4, f2("123425"))
        assertEquals(12, f2("123123"))
        assertEquals(4, f2("12131415"))
    }

    @Test
    fun part1() {
        printSolution("day1_1.txt", { it }, f1)
        printSolution("day1_1.txt", { it }, f2)
    }

}