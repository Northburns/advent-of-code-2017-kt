package aoc2017

import aoc.printSolution
import kotlin.test.Test

class Day25Test {

    /**
     * This time I didn't write text parsing for the input.
     * Instead, I've typed it here.
     * There wasn't much, so it's fine.
     */
    object Input {

        val initialState = "A"
        val totalSteps = 12629077

        val stateRules = listOf(
                StateRule("A", mapOf(
                        false to StateAction({ true }, 1, "B"),
                        true to StateAction({ false }, -1, "B"))),
                StateRule("B", mapOf(
                        false to StateAction({ false }, 1, "C"),
                        true to StateAction({ true }, -1, "B"))),
                StateRule("C", mapOf(
                        false to StateAction({ true }, 1, "D"),
                        true to StateAction({ false }, -1, "A"))),
                StateRule("D", mapOf(
                        false to StateAction({ true }, -1, "E"),
                        true to StateAction({ true }, -1, "F"))),
                StateRule("E", mapOf(
                        false to StateAction({ true }, -1, "A"),
                        true to StateAction({ false }, -1, "D"))),
                StateRule("F", mapOf(
                        false to StateAction({ true }, 1, "A"),
                        true to StateAction({ true }, -1, "E"))))

    }

    @Test
    fun solution() {
        val tm = TuringMachine(false, Input.initialState, Input.stateRules)
        tm.step(Input.totalSteps)
        val checksum = tm.countValues { it }

        printSolution(25, 1, checksum, 3745)
    }

}



