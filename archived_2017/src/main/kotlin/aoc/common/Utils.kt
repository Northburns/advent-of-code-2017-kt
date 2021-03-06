package aoc.common

import kotlin.math.absoluteValue

class Undefined(message: String = "Undefined") : Throwable(message)

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

fun Int.equalsAny(vararg others: Int) = others.find { this == it } != null

fun Int.toBinaryArray(length: Int): List<Boolean> = ((length - 1) downTo 0)
        .fold(mutableListOf()) { acc, b ->
            acc.also { it += this and (1 shl b) != 0 }
        }

fun List<Boolean>.toOnesAndZeroes(prefix: String = "[", postfix: String = "]", separator: String = "") =
        joinToString(prefix = prefix, postfix = postfix, separator = separator) { if (it) "1" else "0" }
