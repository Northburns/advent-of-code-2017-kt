package aoc2017

import aoc.common.Coord2d
import aoc.common.OrthogonalDirection
import aoc.common.Undefined
import aoc.common.go
import java.lang.Character.isLetter

class NetworkDiagram(input: String) {

    private val map: List<String> = input.lines()

    operator fun get(coord: Coord2d) = map.getOrNull(coord.y)?.getOrNull(coord.x) ?: ' '

    fun startingPosition(): Coord2d {
        val x = map[0]
                .mapIndexed { i, c -> i to c }
                .single { (_, c) -> c != ' ' }.first
        return Coord2d(x, 0)
    }

    fun createPacket() = NetworkPacket(this, startingPosition())

}

class NetworkPacket(
        val diagram: NetworkDiagram,
        var pos: Coord2d) {
    var collectedLetters = ""
    var stepCount = 1 // It starts on the diagram, which counts as a step

    var direction = OrthogonalDirection.UP // Y-axis is flipped in the diagram drawings.
    var deadEnd = false

    fun stepThrough() {
        while (!endOfLine()) step()
    }

    fun endOfLine() = deadEnd

    fun step() {
        val forwardCoord = pos.go(direction)
        val nextSquare = diagram[forwardCoord]
        if (nextSquare == ' ') {
            // Would fall off:
            val canGoLeft = diagram[pos.go(direction.counterClockwise())] != ' '
            val canGoRight = diagram[pos.go(direction.clockwise())] != ' '

            if (canGoLeft && canGoRight) throw Undefined("Can go left and right, can't choose.")

            if (canGoLeft)
                direction = direction.counterClockwise()
            else if (canGoRight)
                direction = direction.clockwise()
            else
                deadEnd = true
        } else {
            pos = forwardCoord
            stepCount++
            if (isLetter(nextSquare))
                collectedLetters += nextSquare
        }
    }


}
