package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day18Test {

    private val input = inputFile("day18.txt").trim()
            .lines().map(DuetInstruction.Companion::fromString)

    private val sample1 = """
        |set a 1
        |add a 2
        |mul a a
        |mod a 5
        |snd a
        |set a 0
        |rcv a
        |jgz a -1
        |set a 1
        |jgz a -2""".trimMargin().lines().map(DuetInstruction.Companion::fromString)

    private val sample2 = """
        |snd 1
        |snd 2
        |snd p
        |rcv a
        |rcv b
        |rcv c
        |rcv d""".trimMargin().lines().map(DuetInstruction.Companion::fromString)

    @Test
    fun part1Sample() {

        val r = DuetRegistry.DuetRegistryPart1(sample1)
        assertEquals(0, r["a"])
        r.step()
        assertEquals(1, r["a"])
        r.step()
        assertEquals(3, r["a"])
        r.step()
        assertEquals(9, r["a"])
        r.step()
        assertEquals(4, r["a"])

        r.step()
        assertEquals(4, r.playingFrequency)

        r.step()
        assertEquals(0, r["a"])
        r.step()
        r.step()
        r.step()
        assertEquals(1, r["a"])
        r.step()
        r.step()
        r.step()
        assertEquals(4, r.recoveredFrequency)
    }

    @Test
    fun part1() {
        val solution = DuetRegistry.DuetRegistryPart1(input)
        solution.stepUntilRecover()
        printSolution(18, 1, requireNotNull(solution.recoveredFrequency), 2951)
    }

    @Test
    fun part2Sample() {
        val d = Duet(sample2)

        d.stepBoth()
        assertEquals(listOf(1L), d.incoming0)
        assertEquals(listOf(1L), d.incoming1)

        d.stepBoth()
        assertEquals(listOf(1L, 2L), d.incoming0)
        assertEquals(listOf(1L, 2L), d.incoming1)

        d.stepBoth()
        assertEquals(listOf(1L, 2L, 1L), d.incoming0)
        assertEquals(listOf(1L, 2L, 0L), d.incoming1)

        d.stepBoth()
        assertEquals(listOf(2L, 1L), d.incoming0)
        assertEquals(listOf(2L, 0L), d.incoming1)
        assertEquals(1L, d.r0["a"])
        assertEquals(1L, d.r1["a"])

        d.stepBoth()
        assertEquals(listOf(1L), d.incoming0)
        assertEquals(listOf(0L), d.incoming1)
        assertEquals(2L, d.r0["b"])
        assertEquals(2L, d.r0["b"])

        d.stepBoth()
        assertEquals(emptyList<Long>(), d.incoming0)
        assertEquals(emptyList<Long>(), d.incoming1)

        assertEquals(1, d.r0["c"])
        assertEquals(0, d.r1["c"])

        d.stepBoth()
        assertTrue { d.r0.waiting }
        assertTrue { d.r1.waiting }

    }

    @Test
    fun part2() {
        val solution = Duet(input)
        solution.stepThrough()

        printSolution(18, 2, solution.r1.sendCount, 7366)
    }
}



