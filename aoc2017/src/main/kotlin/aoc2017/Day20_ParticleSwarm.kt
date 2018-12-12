package aoc2017

import aoc2017.common.Vector3d

data class Particle(
        val index: Int,
        var p: Vector3d,
        var v: Vector3d,
        val a: Vector3d) {
    companion object {
        private fun vectorFromString(string: String): Vector3d {
            val (x, y, z) = string.split(",").map { it.toLong() }
            return Vector3d(x, y, z)
        }

        fun fromString(index: Int, string: String): Particle {
            val (p, v, a) = string
                    .split(", ")
                    .map { it.trim('p', 'v', 'a', '=', '<', '>', ' ') }
                    .filter { it.isNotEmpty() }
                    .map { vectorFromString(it) }
            return Particle(index, p, v, a)
        }
    }

    fun tick() {
        v += a
        p += v
    }
}

class ParticleCollider(particles: Collection<Particle>) {

    val particlesById = particles.associateBy { it.index }.toMutableMap()

    /**
     * Set to true once it is detected that all remaining particles are only getting further from each other.
     */
    var stop = false

    /**
     * Key is the indices of the two particles whose distance is the value.
     *
     * Only index pairs where `second > first` are recorded.
     */
    var distanceMap: Map<Pair<Int, Int>, Long>? = null

    fun tick() {
        val collidingParticles = particlesById.values.run {
            forEach(Particle::tick)
            groupBy { it.p }.filterValues { it.size > 1 }.flatMap { it.value }
        }
        collidingParticles.forEach { particlesById.remove(it.index) }

        if (collidingParticles.isNotEmpty()) {
            // Do distance calculating only if no particles were removed.
            // I'm not sure if it matters. I haven't done the math.
            // This costs one more tick, right?
            distanceMap = null
        } else {
            // Record the current distance map
            val dm: Map<Pair<Int, Int>, Long> = particlesById.values
                    .flatMap { p1 ->
                        particlesById.values
                                .filter { p2 -> p2.index > p1.index }
                                .map { p2 ->
                                    (p1.index to p2.index) to p1.p.manhattanDistance(p2.p)
                                }
                    }.toMap()
            // If there is a previous distanceMap to compare to
            distanceMap?.let { dmBefore ->
                // Stop if no two particels are closer now.
                val pairGettingCloser = dm.entries.find { (nowI, distanceNow) ->
                    val distanceBefore = dmBefore[nowI]!!
                    distanceNow < distanceBefore
                }
                if (pairGettingCloser == null)
                    stop = true
            }
            distanceMap = dm
        }

    }

    fun tickThrough() {
        while (!stop) tick()
    }

}