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

fun <E> Collection<E>.allEqual(): Boolean {
    if (isEmpty()) return true
    val first = first()
    return drop(1).find { it != first } == null
}

fun <E> Collection<E>.findOneNonEqual(): E? {
    if (allEqual()) return null
    if (size == 2) throw Undefined("Can't pick one non-equal when there's two elements.")

    return groupBy { it }
            .minBy { it.value.size }
            ?.key
}

@Deprecated(
        "This is actually in the stdlib.",
        ReplaceWith("single()"),
        DeprecationLevel.WARNING)
fun <E> Collection<E>.only() = when {
    size != 1 -> throw Undefined("'only' assumes size == 1 (was $size).")
    else -> first()
}