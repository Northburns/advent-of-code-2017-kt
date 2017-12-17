package aoc2017

class Spinlock(val stepsPerInsert: Int) {
    var buffer = mutableListOf(0)
    var head: Int = 0
    var nextValue = 1
    fun insert2017() = repeat(2017) { insert() }
    fun insert() {
        head = (head + stepsPerInsert) % buffer.size + 1
        buffer.add(head, nextValue++)
    }

    /**
     * Doesn't respect the circularity of the buffer, instead fails if going over either end. YAGNI.
     */
    fun toStringSub(beforeHead: Int, afterHead: Int) =
            buffer.subList(head - beforeHead, head + afterHead + 1).bufferToString(beforeHead)

    override fun toString() = buffer.bufferToString(head)

    private fun List<Int>.bufferToString(highlightIndex: Int) = mapIndexed { index, value -> index to value }
            .joinToString("") { (index, value) ->
                if (index == highlightIndex) "($value)" else " $value "
            }.trim()

}

/**
 * Like [Spinlock] but tries to be faster by not having to allocate memory during inserts.
 *
 * Can't report everything the ordinary can.
 *
 * @param watchIndex Whenever value in this index would get set, its saved to [savedValue]
 */
class AngrySpinlock(val stepsPerInsert: Int, val watchIndex: Int) {
    var head: Int = 0
    var nextValue = 1
    var bufferSize = 1

    var savedValue: Int? = null
    fun insert2017() = repeat(2017) { insert() }
    fun insert() {
        head = (head + stepsPerInsert) % bufferSize + 1
        if (head == watchIndex)
            savedValue = nextValue
        bufferSize++
        nextValue++
    }

}