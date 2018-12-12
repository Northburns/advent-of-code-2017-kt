package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day8Test {

    private val input = inputFile("day8.txt")
            .trim()
            .lines().map(Instruction.Companion::fromString)

    @Test
    fun examplesPart1() {

        val sampleInput = """
            |b inc 5 if a > 1
            |a inc 1 if b < 5
            |c dec -10 if a >= 1
            |c inc -20 if c == 10""".trimMargin()
                .lines().map(Instruction.Companion::fromString)

        val r = Registry()

        r.reset(sampleInput)

        r.step()
        assertEquals(0, r["b"])

        r.step()
        assertEquals(1, r["a"])

        r.step()
        assertEquals(10, r["c"])

        r.step()
        assertEquals(-10, r["c"])

        assertTrue { r.isDone() }

        assertEquals(1, r.largestValue())
        assertEquals(10, r.largestValueEver())

        //

        r.reset(input)
        r.stepThrough()

        printSolution(8, 1, r.largestValue(), 4877)
        printSolution(8, 2, r.largestValueEver(), 5471)
    }


}
