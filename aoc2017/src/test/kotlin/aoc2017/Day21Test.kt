package aoc2017

import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {

    private val input = inputFile("day21.txt")
            .trim()
            .lines()
            .map {
                val (from, to) = it.split(" => ")
                FractalPixelGrid.fromString(from) to FractalPixelGrid.fromString(to)
            }

    @Test
    fun rotate() {
        var g = FractalPixelGrid.fromString("#./..")
                .rotateClockwise()
        assertEquals(FractalPixelGrid.fromString(".#/.."), g)
        g = g.rotateClockwise()
        assertEquals(FractalPixelGrid.fromString("../.#"), g)
        g = g.rotateClockwise()
        assertEquals(FractalPixelGrid.fromString("../#."), g)
        g = g.rotateClockwise()
        assertEquals(FractalPixelGrid.fromString("#./.."), g)
    }

    @Test
    fun chunkedAndJoin() {

        val g = FractalPixelGrid.fromString("##..##/..##../####../..####/#.##.#/.#..#.")

        val ch2 = g.gridChunked(2)
        assertEquals(FractalPixelGrid.fromString("##/.."), ch2[0][0])
        assertEquals(FractalPixelGrid.fromString("../##"), ch2[0][1])
        assertEquals(FractalPixelGrid.fromString("##/.."), ch2[0][2])
        assertEquals(FractalPixelGrid.fromString("##/.."), ch2[1][0])
        assertEquals(FractalPixelGrid.fromString("##/##"), ch2[1][1])
        assertEquals(FractalPixelGrid.fromString("../##"), ch2[1][2])
        assertEquals(FractalPixelGrid.fromString("#./.#"), ch2[2][0])
        assertEquals(FractalPixelGrid.fromString("##/.."), ch2[2][1])
        assertEquals(FractalPixelGrid.fromString(".#/#."), ch2[2][2])
        assertEquals(g, ch2.joinGrids())

        val ch3 = g.gridChunked(3)
        assertEquals(FractalPixelGrid.fromString("##./..#/###"), ch3[0][0])
        assertEquals(FractalPixelGrid.fromString(".##/#../#.."), ch3[0][1])
        assertEquals(FractalPixelGrid.fromString("..#/#.#/.#."), ch3[1][0])
        assertEquals(FractalPixelGrid.fromString("###/#.#/.#."), ch3[1][1])
        assertEquals(g, ch3.joinGrids())

    }


    @Test
    fun solutions() {
        fun pixelOnCount(steps: Int) =
                FractalArt(input).run {
                    step(steps)
                    pixels.pixels.sumBy { it.count { it == FractalPixel.ON } }
                }
        printSolution(21, 1, pixelOnCount(5), 173)
        printSolution(21, 1, pixelOnCount(18), 2456178)
    }

}



