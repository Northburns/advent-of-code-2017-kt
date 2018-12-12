package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test

class Day20Test {

    private val input = inputFile("day20.txt")
            .trim()
            .lines()
            .mapIndexed { i, string -> Particle.fromString(i, string) }

    @Test
    fun part1() {
        // In the end, the particle that stays closest to <0,0,0> is the one with the smallest acceleration.
        // Others will eventually catch it up, and go further.
        val m = input.minBy { it.a.manhattanDistance() }!!
        printSolution(20, 1, m.index, 150)
    }

    @Test
    fun part2() {
        val c = ParticleCollider(input)
        c.tickThrough()
        printSolution(20, 2, c.particlesById.size, 657)
    }
}



