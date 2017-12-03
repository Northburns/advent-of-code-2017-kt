package aoc2017

import aoc2017.SpiralMemoryModel.Companion.ringCoordinateComponentArray
import aoc2017.common.Coord2d
import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {

    private val input = inputFile("day3.txt").trim().toInt()

    @Test
    fun testRingCoordinates() {
        val ring0 = listOf(
                Coord2d(0, 0))
        assertEquals(ring0,
                ringCoordinateComponentArray(0))

        val ring1 = listOf(
                Coord2d(1, 0),
                Coord2d(1, 1),
                Coord2d(0, 1),
                Coord2d(-1, 1),
                Coord2d(-1, 0),
                Coord2d(-1, -1),
                Coord2d(0, -1),
                Coord2d(1, -1))
        assertEquals(ring1,
                ringCoordinateComponentArray(1))

        val ring2 = listOf(
                Coord2d(2, -1),
                Coord2d(2, 0),
                Coord2d(2, 1),
                Coord2d(2, 2),
                Coord2d(1, 2),
                Coord2d(0, 2),
                Coord2d(-1, 2),
                Coord2d(-2, 2),
                Coord2d(-2, 1),
                Coord2d(-2, 0),
                Coord2d(-2, -1),
                Coord2d(-2, -2),
                Coord2d(-1, -2),
                Coord2d(0, -2),
                Coord2d(1, -2),
                Coord2d(2, -2))
        assertEquals(ring2,
                ringCoordinateComponentArray(2))
    }

    @Test
    fun testRegisterCoordinates() {
        val mem = SpiralMemoryModel()
        assertEquals(Coord2d(0, 0), mem.coordinateFor(1))
    }

    @Test
    fun examplesPart1() {
        val mem = SpiralMemoryModel()

        val f = { register: Int -> mem.registerManhattanDistance(register) }

        assertEquals(0, f(1))
        assertEquals(3, f(12))
        assertEquals(2, f(23))
        assertEquals(31, f(1024))

        println("Day 3, Part 1: ${f(input)}")
    }

    @Test
    fun examplesPart2() {
        val mem = SpiralMemoryModel()

        val result = mem.fillWithStressTestData { it > input }

        assertEquals(1, mem.valueAt(1))
        assertEquals(1, mem.valueAt(2))
        assertEquals(2, mem.valueAt(3))
        assertEquals(4, mem.valueAt(4))
        assertEquals(5, mem.valueAt(5))

        println("Day 3, Part 2: $result")
    }


}
