package aoc2017

import aoc2017.common.Undefined

/*
    Maybe a bit overkill, but it was fun.
 */

enum class FractalPixel(val string: String) {
    OFF("."),
    ON("#");

    override fun toString() = string

    companion object {
        fun fromChar(string: Char) = values().find { it.string == string.toString() }!!
    }
}

data class FractalPixelGrid(val pixels: List<List<FractalPixel>>) {
    /**
     * It is assumed that the width and height are always the same
     */
    val size = pixels.size

    fun get(x: Int, y: Int) = pixels[y][x]

    fun rotateClockwise() = FractalPixelGrid(
            (0.until(size)).map { i ->
                (0.until(size)).map { j ->
                    get(i, size - j - 1)
                }
            })

    private fun flipHorizontally() = FractalPixelGrid(pixels.map { it.reversed() })

    fun allRotationsAndFlips() =
            listOf(this, flipHorizontally()).flatMap {
                val r1 = it.rotateClockwise()
                val r2 = r1.rotateClockwise()
                val r3 = r2.rotateClockwise()
                listOf(it, r1, r2, r3)
            }.toSet()

    fun gridChunked(chunkSize: Int): List<List<FractalPixelGrid>> {
        if (size % chunkSize != 0) throw Undefined("Uneven chunksize ($chunkSize) for FractalPixelGrid of size $size")
        val parts = size / chunkSize

        return (0.until(parts)).map { y ->
            val yFrom = y * chunkSize
            (0.until(parts)).map { x ->
                val xFrom = x * chunkSize

                pixels.filterIndexed { sourceY, _ ->
                    sourceY in yFrom.until(yFrom + chunkSize)
                }.map {
                    it.filterIndexed { sourceX, _ ->
                        sourceX in xFrom.until(xFrom + chunkSize)
                    }
                }.let { FractalPixelGrid(it) }

            }
        }
    }

    fun enhance(rules: Collection<Pair<FractalPixelGrid, FractalPixelGrid>>) =
            rules
                    .find {
                        it.first in allRotationsAndFlips()
                    }?.second ?: throw Undefined("Didn't find an enhancement rule...")


    override fun toString(): String {
        return pixels.joinToString(separator = "/") {
            it.joinToString(separator = "")
        }
    }

    companion object {

        fun fromString(string: String) =
                FractalPixelGrid(string.split("/").map { it.map(FractalPixel.Companion::fromChar) })


    }

}

fun List<List<FractalPixelGrid>>.joinGrids(): FractalPixelGrid {
    //  Again, assume square size for all, and assume the FractalPixelGrids are all equal size.
    val gridSize = this[0][0].size
    val sizeTotal = size * gridSize

    return (0.until(sizeTotal)).map { y ->
        (0.until(sizeTotal)).map { x ->
            this[y / gridSize][x / gridSize].pixels[y % gridSize][x % gridSize]
        }
    }.let(::FractalPixelGrid)
}

class FractalArt(
        val enhancementRules: Collection<Pair<FractalPixelGrid, FractalPixelGrid>>) {

    var pixels = FractalPixelGrid.fromString(".#./..#/###")

    tailrec fun step(steps: Int = 1) {
        if (steps <= 0) return
        val size = pixels.size
        val chunked = when {
            size % 2 == 0 -> pixels.gridChunked(2)
            size % 3 == 0 -> pixels.gridChunked(3)
            else -> throw Undefined("Unexpected FractalArt size: $size")
        }
        pixels = chunked
                .map { it.map { it.enhance(enhancementRules) } }
                .joinGrids()
        step(steps - 1)
    }

}
