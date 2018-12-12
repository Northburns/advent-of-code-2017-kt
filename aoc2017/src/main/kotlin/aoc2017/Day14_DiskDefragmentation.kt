package aoc2017

import aoc2017.common.Coord2d
import aoc2017.common.toBinaryArray

class Disk(input: String) {

    private val rows = (0..127).map { row ->
        Row(StandardKnotHash("$input-$row").hash)
    }

    private class Row(hashInput: String) {
        val squares = hashInput.flatMap { it.toString().toInt(16).toBinaryArray(4) }
    }


    fun countSquares(predicate: (Boolean) -> Boolean = { it }) = rows.sumBy { it.squares.count(predicate) }

    fun value(column: Int, row: Int) = rows[row].squares[column]

    fun countRegions(): Int {
        /*
            Overlay matches the Disk grid. Values:
            * null = overlay square has not been accessed yet
            * 0 = Disk's square is false, no group
            * 1+ = Group number
        */
        val overlay = Array<Array<Int?>>(128) { arrayOfNulls(128) }

        var nextGroup = 1

        (0..127).forEach { row ->
            val overlayRow = overlay[row]
            (0..127).forEach { column ->
                if (overlayRow[column] == null) {
                    if (!value(column, row))
                    // false = Not part of any group
                        overlayRow[column] = 0
                    else {
                        // mark the whole group!
                        val g = nextGroup++
                        tailrec fun markTheGroup(squares: Collection<Coord2d>) {
                            val next = squares.flatMap { s ->
                                overlay[s.y][s.x] = g
                                s.orthogonalNeighbours()
                                        .filter { it.x in 0..127 && it.y in 0..127 }
                                        .filter { value(it.x, it.y) } // Keep within the group
                                        .filter { overlay[it.y][it.x] == null } // Find only new squares
                            }
                            if (next.isNotEmpty()) markTheGroup(next)
                        }
                        markTheGroup(listOf(Coord2d(column, row)))
                    }
                }
            }
        }

        return nextGroup - 1
    }

}


