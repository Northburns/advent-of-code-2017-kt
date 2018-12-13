package aoc2017

/*
  Yikes, look at all those loops :)

 */

data class Block(
        val location: Int)

/**
 * Indexes are zero based.
 */
data class Memory(
        private val blocks: Collection<Block>,
        private val bankCount: Int,
        val redisributeCounter: Int = 0) {

    private fun Int.nextBank() = (this + 1) % bankCount

    constructor(bankCounts: List<Int>) : this(bankCounts
            .mapIndexed { index, blockCount ->
                Array(blockCount) { Block(index) }.toList()
            }.flatten(), bankCounts.size)

    fun bankWithMostBlocks(): Int = blocks
            .groupBy { it.location }
            .maxWith(compareBy(
                    { it.value.size },
                    { -it.key }))!!
            .key

    /**
     * Creates a copy of self where memory has been redistributed.
     */
    fun redistribute(): Memory {

        val iMax = bankWithMostBlocks()

        val redistrBank = iterator {
            var i = iMax
            while (true) {
                i = i.nextBank()
                yield(i)
            }
        }
        val redistributedBlocks = blocks
                .map {
                    it.copy(location = when (it.location) {
                        iMax -> redistrBank.next()
                        else -> it.location
                    })
                }

        return copy(
                blocks = redistributedBlocks,
                redisributeCounter = redisributeCounter + 1)
    }

    /**
     * @return second is the index of the memory state that equals to the last.
     */
    fun redistributionHistoryUntilLoops(): Pair<List<Memory>, Int> {
        val history = mutableListOf(this to this.memoryBlockCounts())
        var i: Int
        do {
            val m = history.last().first.redistribute().let { it to it.memoryBlockCounts() }
            i = history.indexOfFirst { it.second == m.second }
            history.add(m)
        } while (i == -1)
        return history.map { it.first } to i
    }

    /**
     * Index matches memory bank index.
     */
    fun memoryBlockCounts() =
            Array(bankCount) { i ->
                blocks.count { it.location == i }
            }.toList()

}
