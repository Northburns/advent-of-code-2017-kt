package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day9Test {

    private val input = inputFile("day9.txt")

    @Test
    fun selfContainedPiecesOfGarbage() {
        listOf(
                process("<>"),
                process("<random characters>"),
                process("<<<<>"),
                process("<{!>}>"),
                process("<!!>"),
                process("<!!!>>"),
                process("<{o\"i!a,<{i<a>"))
                .map { it.single() }
                .forEach {
                    assertTrue("Should be garbage: $it") {
                        it is Garbage
                    }
                }
    }


    companion object {
        fun groupAssert(groupCount: Int, input: String) {
            val group = process(input).single() as Group
            assertEquals(groupCount, group.groupCount(), "$groupCount for $group")
        }

        fun scoreAssert(score: Int, input: String) {
            val group = process(input).single() as Group
            assertEquals(score, group.scoreTotal(), "$score for $group")
        }
    }

    @Test
    fun groupsAndCountA() {
        groupAssert(1, "{}")
    }

    @Test
    fun groupsAndCountB() {
        groupAssert(3, "{{{}}}")
    }

    @Test
    fun groupsAndCountC() {
        groupAssert(3, "{{},{}}")
    }

    @Test
    fun groupsAndCountD() {
        groupAssert(6, "{{{},{},{{}}}}")
    }

    @Test
    fun groupsAndCountE() {
        groupAssert(1, "{<{},{},{{}}>}")
    }

    @Test
    fun groupsAndCountF() {
        groupAssert(1, "{<a>,<a>,<a>,<a>}")
    }

    @Test
    fun groupsAndCountG() {
        groupAssert(5, "{{<a>},{<a>},{<a>},{<a>}}")
    }

    @Test
    fun groupsAndCountH() {
        groupAssert(2, "{{<!>},{<!>},{<!>},{<a>}}")
    }

    @Test
    fun scoreA() {
        scoreAssert(1, "{}")
    }

    @Test
    fun score() {
        scoreAssert(6, "{ { {} } }")
    }

    @Test
    fun scoreB() {
        scoreAssert(5, "{ {},{} }")
    }

    @Test
    fun scoreC() {
        scoreAssert(16, "{ { {},{},{ {} } } }")
    }

    @Test
    fun scoreD() {
        scoreAssert(1, "{ <a >, <a>, <a>, <a> }")
    }

    @Test
    fun scoreE() {
        scoreAssert(9, "{ { <ab > },{ <ab > },{ <ab > },{ <ab > } }")
    }

    @Test
    fun scoreF() {
        scoreAssert(9, "{ { <!!> },{ <!!> },{ <!!> },{ <!!> } }")
    }

    @Test
    fun scoreG() {
        scoreAssert(3, "{ { <a!> },{ <a!> },{ <a!> },{ <ab > } }")
    }

    @Test
    fun solution() {
        val solution = process(input)
                .single() as Group
        val scoreTotal = solution.scoreTotal()

        assertEquals(15922, scoreTotal)
        printSolution(9, 1, scoreTotal, 15922)

        val garbageCharacters = solution.garbageCharCount()

        assertEquals(7314, garbageCharacters)
        printSolution(9, 2, garbageCharacters, 7314)
    }


}
