package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    private val input = inputFile("day11.txt").trim()

    val f: String.() -> Int = { toPath().displacement() }

    @Test
    fun examplesPart1_a() = assertEquals(3, "ne,ne,ne".f())

    @Test
    fun examplesPart1_b() = assertEquals(0, "ne,ne,sw,sw".f())

    @Test
    fun examplesPart1_c() = assertEquals(2, "ne,ne,s,s".f())

    @Test
    fun examplesPart1_d() = assertEquals(3, "se,sw,se,sw,sw".f())

    @Test
    fun solutionPart1() {
        val result = input.f()
        printSolution(11, 1, result, 707)
    }

    @Test
    fun solutionPart2() {
        val path = input.toPath()
        val furthest = path
                .mapIndexed { i, _ ->
                    path.subList(0, i + 1).displacement()
                }.max()!!
        printSolution(11, 2, furthest, 1490)
    }

    companion object {
        fun String.toPath() =
                trim().split(',').map {
                    HexCoordinate.Direction.fromString(it.trim())
                }
    }
}



