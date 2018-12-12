package aoc2017

import kotlin.test.Test

class Day23Test {

    private val input = inputFile("day23.txt")
            .trim()
            .lines()
            .map(CcInstruction.Companion::fromString)

    @Test
    fun part1() {
        val cr = CcRegistry(input)
        cr.stepThrough()
        printSolution(23, 1, cr.mulCount, 6724)
    }

    @Test
    fun part2() {
        printSolution(23, 2, optimizedProgram(), 903)
    }

}



