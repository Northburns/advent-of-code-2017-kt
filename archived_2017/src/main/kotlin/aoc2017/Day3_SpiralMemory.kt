package aoc2017

import aoc.common.Coord2d
import aoc.common.computeIfAbsentK
import aoc.common.rotate

/**
 * This class feels like such a mess, but I did end up with the right answers, so :shrug:
 */
class SpiralMemoryModel {

    private val ringCoordinates = mutableMapOf<Int, List<Coord2d>>()

    private val registerCoordinates = mutableMapOf<Int, Coord2d>()

    private val storedValues = mutableMapOf<Coord2d, Int>()

    private fun ringCoordinatesUpTo(ring: Int): List<Coord2d> = (0..ring).flatMap {
        ringCoordinates.computeIfAbsentK(it) { ringCoordinateComponentArray(it) }
    }

    fun coordinateFor(register: Int) = registerCoordinates.computeIfAbsentK(register) {
        ringCoordinatesUpTo(registerRing(it))[it - 1]
    }

    fun registerManhattanDistance(register: Int, other: Int = 1): Int {
        val from = coordinateFor(register)
        val to = coordinateFor(other)
        return from.manhattanDistance(to)
    }

    fun valueAt(register: Int) = storedValues[coordinateFor(register)]!!

    /**
     * @param end last written value is tested on, if filling should ciese
     *
     * @return the last written value
     */
    fun fillWithStressTestData(end: (Int) -> Boolean): Int {

        val result = generateSequence(1, { it + 1 })
                .map { register ->
                    val coord = coordinateFor(register)
                    val value = if (register == 1) 1 else coord.neighbours().sumBy { storedValues[it] ?: 0 }
                    storedValues.put(coord, value)
                    value
                }
                .find(end)

        return result!!
    }

    companion object {

        fun ringCoordinateComponentArray(ring: Int): List<Coord2d> {
            if (ring == 0) return listOf(Coord2d(0, 0))

            val ringsSideLength = ringsSideLength(ring)

            // Eg ring 1 == 1,1,1,0,-1,-1,-1,0
            // Eg ring 2 == 2,2,2,2,2,1,0,-1,-2,-2,-2,-2,-2,-1,0,1
            // As x coordinate, phase + 1 (==1)
            // As y coordinate, phase - 3 (==ring side length - 2)

            return mutableListOf<Int>().let { list ->
                repeat(ringsSideLength) { list.add(ring) }
                (-ring).rangeTo(ring)
                        .drop(1).dropLast(1)
                        .reversed()
                        .forEach { list.add(it) }
                list + list.map { -it }
            }.let { components ->
                val xArray = components.rotate(1)
                val yArray = components.rotate(1 - 2 * ring)
                xArray.zip(yArray) { x, y ->
                    Coord2d(x, y)
                }
            }
        }

        private fun ringsSideLength(ring: Int) = ring * 2 + 1

        fun registerRing(register: Int): Int {
            if (register == 1) return 0
            return generateSequence(0, { it + 1 }).find { ring ->
                ringsSideLength(ring).let { it * it } >= register
            }!!
        }

    }


}
