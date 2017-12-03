package aoc2017.common

import kotlin.math.absoluteValue

class Undefined(message: String = "Undefined") : Throwable(message)

data class Coord2d(val x: Int, val y: Int) {

    fun manhattanDistance(to: Coord2d) = (x - to.x).absoluteValue + (y - to.y).absoluteValue

    fun neighbours() =
            ((x - 1)..(x + 1)).flatMap { x_ ->
                ((y - 1)..(y + 1))
                        .filter { y_ -> !(x_ == x && y_ == y) }
                        .map { y_ -> Coord2d(x_, y_) }
            }

}


/**
 * Return a copy of this list with elements "phased" by [phase].
 *
 * Undefined for empty lists.
 */
inline fun <reified T> List<T>.rotate(phase: Int): List<T> {
    val phaseInBounds = phase.modulo(size)
    return when {
        isEmpty() -> emptyList()
        phaseInBounds == 0 -> toMutableList()
        else -> {
            subList(phaseInBounds, size) + subList(0, phaseInBounds)
        }
    }
}

/**
 * For use with [rotate].
 */
fun Int.modulo(other: Int) = when {
    other <= 1 -> throw Undefined()
    this >= 0 -> rem(other)
    else -> {
        val phaseAboveZero = ((this.absoluteValue / other) + 1) * other
        (this + phaseAboveZero).rem(other)
    }
}