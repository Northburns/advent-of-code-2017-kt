package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {

    private val input = inputFile("day15.txt").trim().lines().map { line ->
        val (g, v) = line.split("starts with")
        g.split("Generator")[1].trim() to v.trim().toLong()
    }.associateBy({ it.first }) { it.second }
    private val sample = "flqrgnkx"

    @Test
    fun part1() {
        val rules = Generator.Rules.PART1
        val gA = Generator("A", 65, rules, true)
        val gB = Generator("B", 8921, rules, true)
        val judge = Judge(gA, gB)
        assertEquals(1, judge.stepNTimes(5))


        assertEquals(listOf(1092455L, 1181022009L, 245556042L, 1744312007L, 1352636452L), gA.rememberedValues.take(5))
        assertEquals(listOf(430625591L, 1233683848L, 1431495498L, 137874439L, 285222916L), gB.rememberedValues.take(5))


        val solution = Judge(Generator("A", input["A"]!!, rules), Generator("B", input["B"]!!, rules))
        val result = solution.step40MillionTimes()
        printSolution(15, 1, result, 612)
    }

    @Test
    fun part2() {
        val rules = Generator.Rules.PART2
        val gA = Generator("A", 65, rules, true)
        val gB = Generator("B", 8921, rules, true)
        val judge = Judge(gA, gB)
        assertEquals(1, judge.stepNTimes(1056))


        assertEquals(listOf(1352636452L, 1992081072L, 530830436L, 1980017072L, 740335192L), gA.rememberedValues.take(5))
        assertEquals(listOf(1233683848L, 862516352L, 1159784568L, 1616057672L, 412269392L), gB.rememberedValues.take(5))


        val solution = Judge(Generator("A", input["A"]!!, rules), Generator("B", input["B"]!!, rules))
        val result = solution.step5MillionTimes()
        printSolution(15, 2, result, 285)
    }
}



