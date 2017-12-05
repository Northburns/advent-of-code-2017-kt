package aoc2017

class Trampolines(
        val jumps: IntArray,
        var head: Int = 0,
        private val offsetTransform: (Int) -> Int = { it + 1 }) {

    var steps = 0
        private set(value) {
            field = value
        }

    fun step() {
        steps++
        val offset = jumps[head]
        jumps[head] = offsetTransform(offset)
        head += offset
    }

    fun isOutside() = head < 0 || head >= jumps.size

    fun stepUntilOutside() {
        while (!isOutside())
            step()
    }

}

fun createWierderTrampoline(jumps: IntArray) = Trampolines(jumps) {
    it + if (it >= 3) -1 else 1
}