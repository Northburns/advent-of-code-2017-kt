package aoc2017

import aoc2017.common.Undefined
import kotlin.math.abs

/**
 * We use "odd-q" as defined
 * [here](https://www.redblobgames.com/grids/hexagons/#coordinates).
 *
 */
data class HexCoordinate(val q: Int, val r: Int) {

    fun neighbours() = Direction.values()
            .map { it to plus(it) }

    operator fun plus(path: Collection<Direction>) =
            path.fold(this, HexCoordinate::plus)

    operator fun plus(direction: Direction) = when (direction) {
        Direction.N -> HexCoordinate(q, r - 1)
        Direction.S -> HexCoordinate(q, r + 1)

        Direction.NE -> HexCoordinate(q + 1, r - 1 + oddQ())
        Direction.SE -> HexCoordinate(q + 1, r + oddQ())
        Direction.SW -> HexCoordinate(q - 1, r + oddQ())
        Direction.NW -> HexCoordinate(q - 1, r - 1 + oddQ())
    }

    private fun oddQ() = abs(q) % 2

    enum class Direction(val s: String) {
        N("n"),
        NE("ne"),
        SE("se"),
        S("s"),
        SW("sw"),
        NW("nw");

        companion object {
            fun fromString(string: String) = values().find { it.s == string } ?: throw Undefined("no result for $string")
        }
    }

    fun displacement(to: HexCoordinate): Int {
        // Thanks for the math, redblobgames!
        // https://www.redblobgames.com/grids/hexagons/#distances-offset

        fun HexCoordinate.offsetToCube(): Triple<Int, Int, Int> {
            val z = r - (q - q.and(1)) / 2
            return Triple(q, -q - z, z)
        }

        fun cubeDistance(a: Triple<Int, Int, Int>, b: Triple<Int, Int, Int>): Int {
            val (ax, ay, az) = a
            val (bx, by, bz) = b
            return (abs(ax - bx) + abs(ay - by) + abs(az - bz)) / 2
        }

        return cubeDistance(offsetToCube(), to.offsetToCube())
    }

}


fun List<HexCoordinate.Direction>.displacement(): Int {
    val start = HexCoordinate(0, 0) // any arbitrary hex
    return start.displacement(start + this)
}
