package aoc2017

import aoc.inputFile
import aoc.printSolution
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {

    private val input = inputFile("day16.txt").trim().split(",")
    private val sample = "flqrgnkx"

    @Test
    fun part1() {
        val d = DanceLine("abcde")
        d.move("s1")
        assertEquals("eabcd", d.joinNames())
        d.move("x3/4")
        assertEquals("eabdc", d.joinNames())
        d.move("pe/b")
        assertEquals("baedc", d.joinNames())

        val solution = DanceLine()
        solution.move(input)

        printSolution(16, 1, solution.joinNames(), "fgmobeaijhdpkcln")
    }

    @Test
    fun part2() {
        val solution = DanceLine()
        val moves = DanceMove.fromStrings(input)
        val totalDances = 1_000_000_000
        /*
        Doing all billion dances would take a long time.
        Thanks to hints over at the subreddit, let's see when the dance loops to its starting position.
         */
        var danceCycle = -1
        var dance = 0
        val start = solution.line
        while (danceCycle == -1) {
            println("Dance progress: ${(100f * dance.toFloat() / 1_000_000_000f)} %")
            moves.forEach(solution::move)
            dance++
            if (start == solution.line) {
                danceCycle = dance
                break
            }
        }
        // Find the remainder from the full dance cycles, to end up with the final positions
        repeat(totalDances % danceCycle) {
            moves.forEach(solution::move)
        }

        printSolution(16, 2, solution.joinNames(), "lgmkacfjbopednhi")
    }
}



