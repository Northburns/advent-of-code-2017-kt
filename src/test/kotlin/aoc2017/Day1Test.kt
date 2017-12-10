package aoc2017

import kotlin.test.Test

class Day1Test {

    private val f1 = { input: String ->
        reverseCaptchaSum(input)
    }
    private val f2 = ::reverseCaptchaSumHalfway

    private val input = inputFile("day1.txt")

    @Test
    fun examplesPart1() {
        withFunction(f1) {
            eq(3, "1122")
            eq(4, "1111")
            eq(0, "1234")
            eq(9, "91212129")
        }
        printSolution(1, 1, f1(input), 1177)
    }

    @Test
    fun examplesPart2() {
        withFunction(f2) {
            eq(6, "1212")
            eq(0, "1221")
            eq(4, "123425")
            eq(12, "123123")
            eq(4, "12131415")
        }
        printSolution(1, 2, f2(input), 1060)
    }

}
