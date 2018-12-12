package aoc2017

import aoc2017.NodeState.*
import aoc2017.common.Coord2d
import aoc2017.common.OrthogonalDirection
import aoc2017.common.Undefined
import aoc2017.common.go

enum class NodeState(val string: String) {
    CLEAN("."),
    INFECTED("#"),
    WEAKENED("W"),
    FLAGGED("F");

    companion object {
        fun fromChar(char: Char) =
                char.toString().let { string -> values().find { it.string == string }!! }
    }

    override fun toString() = string

}

sealed class SporificaVirusRules {

    abstract fun infectionalize(state: NodeState): NodeState
    abstract fun directionalize(state: NodeState, direction: OrthogonalDirection): OrthogonalDirection

    class Part1 : SporificaVirusRules() {
        override fun infectionalize(state: NodeState) =
                when (state) {
                    CLEAN -> INFECTED
                    INFECTED -> CLEAN
                    else -> throw Undefined("Illegal state for part1: $this")
                }

        override fun directionalize(state: NodeState, direction: OrthogonalDirection) =
                when (state) {
                    INFECTED -> direction.clockwise()
                    CLEAN -> direction.counterClockwise()
                    else -> throw Undefined("Illegal state for part1: $this")
                }
    }

    class Part2 : SporificaVirusRules() {
        override fun infectionalize(state: NodeState) =
                when (state) {
                    CLEAN -> WEAKENED
                    WEAKENED -> INFECTED
                    INFECTED -> FLAGGED
                    FLAGGED -> CLEAN
                }

        override fun directionalize(state: NodeState, direction: OrthogonalDirection) =
                when (state) {
                    CLEAN -> direction.counterClockwise()
                    WEAKENED -> direction
                    INFECTED -> direction.clockwise()
                    FLAGGED -> direction.opposite()
                }
    }


}

class SporificaVirus(
        val map: VirusMap,
        var position: Coord2d,
        var direction: OrthogonalDirection = OrthogonalDirection.UP) {

    fun burst() {
        map[position].let { current ->
            direction = map.rules.directionalize(current.state, direction)
            current.state = map.rules.infectionalize(current.state)
            if (current.state == INFECTED) infectionCount++
        }
        position = position.go(direction)
    }

    var infectionCount: Long = 0

}

/**
 * @param initialState y-axis flipped. Must be square with odd side length. Not asserted though.
 */
class VirusMap(
        val rules: SporificaVirusRules,
        initialState: List<List<NodeState>>) {

    class Node(
            val initialState: NodeState,
            var state: NodeState = initialState)

    val nodes = initialState
            .reversed() // Flip the y-axis back up
            .mapIndexed { y, row ->
                row.mapIndexed { x, cell ->
                    Coord2d(x, y) to Node(cell)
                }
            }.flatten().toMap().toMutableMap()

    val center = (initialState.size / 2).let { Coord2d(it, it) }
    val virus = SporificaVirus(this, center)

    operator fun get(coord: Coord2d) = nodes.computeIfAbsent(coord) { _ -> Node(CLEAN) }

    tailrec fun step(steps: Int = 1) {
        if (steps == 0) return
        virus.burst()
        step(steps - 1)
    }

}