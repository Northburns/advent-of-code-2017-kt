package aoc2017

import kotlin.test.Test

class Day22Test {

    private val input = inputFile("day22.txt")
            .trim()
            .lines()
            .map {
                it.map { NodeState.fromChar(it) }
            }

    @Test
    fun part1() {
        val vm = VirusMap(SporificaVirusRules.Part1(), input)
        vm.step(10_000)
        val burstsThatInfected = vm.virus.infectionCount
        printSolution(22, 1, burstsThatInfected, 5196)
    }

    @Test
    fun part2() {
        val vm = VirusMap(SporificaVirusRules.Part2(), input)
        vm.step(10_000_000)
        val burstsThatInfected = vm.virus.infectionCount
        printSolution(22, 2, burstsThatInfected, 2511633)
    }

}



