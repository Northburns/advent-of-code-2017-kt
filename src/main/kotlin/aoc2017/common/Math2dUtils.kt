package aoc2017.common

import kotlin.math.absoluteValue

data class Coord2d(val x: Int, val y: Int) {

    fun manhattanDistance(to: Coord2d) = (x - to.x).absoluteValue + (y - to.y).absoluteValue

    /**
     * Includes diagonals
     */
    fun neighbours() =
            ((x - 1)..(x + 1)).flatMap { x_ ->
                ((y - 1)..(y + 1))
                        .filter { y_ -> !(x_ == x && y_ == y) }
                        .map { y_ -> Coord2d(x_, y_) }
            }

    fun orthogonalNeighbours() = listOf(
            Coord2d(x - 1, y),
            Coord2d(x + 1, y),
            Coord2d(x, y - 1),
            Coord2d(x, y + 1))

    fun plus(dx: Int = 0, dy: Int = 0) = copy(x = x + dx, y = y + dy)

}


enum class OrthogonalDirection {

    UP,
    RIGHT,
    DOWN,
    LEFT;

    fun clockwise() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }

    fun counterClockwise() = when (this) {
        UP -> LEFT
        RIGHT -> UP
        DOWN -> RIGHT
        LEFT -> DOWN
    }

    fun from(position: Coord2d, distance: Int = 1) = when (this) {
        UP -> position.plus(dy = distance)
        RIGHT -> position.plus(dx = distance)
        DOWN -> position.plus(dy = -distance)
        LEFT -> position.plus(dx = -distance)
    }

    fun opposite() = when (this) {
        UP -> DOWN
        RIGHT -> LEFT
        DOWN -> UP
        LEFT -> RIGHT
    }
}

fun Coord2d.go(direction: OrthogonalDirection, distance: Int = 1) = direction.from(this, distance)

