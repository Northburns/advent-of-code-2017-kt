package aoc2017.common


data class Vector3d(val x: Long, val y: Long, val z: Long) {

    companion object {
        val ORIGO = Vector3d(0, 0, 0)
    }

    private inline fun smash(v: Vector3d, op: (Long, Long) -> Long) =
            copy(x = op(x, v.x), y = op(y, v.y), z = op(z, v.z))

    operator fun plus(v: Vector3d) = smash(v, Long::plus)

    operator fun minus(v: Vector3d) = smash(v, Long::minus)

    operator fun unaryMinus() = ORIGO.minus(this)

    fun manhattanDistance(other: Vector3d = ORIGO) =
            this.minus(other).run { Math.abs(x) + Math.abs(y) + Math.abs(z) }

}