package aoc2017

import aoc2017.common.Undefined


class KnotHash(length: Int, val input: List<Int>) {

    /**
     * Part 2 constructor
     */
    constructor(asciiInput: String) : this(
            256,
            asciiInput
                    .map { it.toInt() } + standardInputSuffix)

    val string = generateSequence(0) {
        (it + 1).takeIf { it < length }
    }.toList().toIntArray()

    var position = 0
    var skipSize = 0

    var head = 0
    fun step() {
        val length = input[head++]

        val knot = string.circularSubArray(position, length)
        val twist = knot.reversedArray()
        string.circularOverwrite(position, twist)

        position = (position + length + skipSize) % string.size
        skipSize++
    }

    private companion object {

        private val standardInputSuffix = listOf(17, 31, 73, 47, 23)

        fun IntArray.print(message: String = "") {
            println("$message ${toList()}")
        }

        fun IntArray.circularSubArray(startIndex: Int, length: Int): IntArray {
            if (length > size) throw Undefined("Subarray larger than original? Undefined.")

            val part1 = sliceArray(startIndex.until(Math.min(startIndex + length, size)))
            return when (length) {
                part1.size -> part1
                else -> part1 + sliceArray(0.until(length - part1.size))
            }
        }

        fun IntArray.circularOverwrite(startIndex: Int, values: IntArray) {
            values.forEachIndexed { i, t ->
                this[(i + startIndex) % size] = values[i]
            }
        }


    }

    fun stepToResultPart1(): Int {
        while (head < input.size) step()
        return result()
    }

    fun result(): Int {
        if (head < input.size) throw Undefined("Not stepped through yet!")
        return string[0] * string[1]
    }

    fun stepToResultPart2(): String {
        stepToResult64Times()
        return denseHash().joinToString(separator = "") {
            it.toString(16).padStart(2, '0')
        }
    }

    private fun stepToResult64Times() {
        repeat(64) {
            head = 0
            stepToResultPart1()
        }
    }

    private fun denseHash() = string.toList().chunked(16) { it.reduce(Int::xor) }


}