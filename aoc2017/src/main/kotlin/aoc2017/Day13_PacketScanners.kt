package aoc2017

fun toFirewall(input: String) = input
        .lines()
        .map {
            val (depth, range) = it.split(":")
            depth.trim().toInt() to FirewallWithPacket.Layer(range.trim().toInt())
        }.toMap().let(::FirewallWithPacket)

fun smallestDelayToPassWithoutBeingCaught(firewallInput: String): Int? {
    // Full simulation feels too slow for the part 2's solution.
    // Find the solution by iterating only delays, and using MATH for rest.
    // Thanks subreddit posters for hints!
    val f = toFirewall(firewallInput)
    fun isSafe(delay: Int): Boolean {
        return f.layersByDepth.entries.any {
            val layerHitAtTime = it.key + delay
            val wavelength = 2 * it.value.range - 2
            layerHitAtTime % wavelength == 0
        }.not()
    }
    return generateSequence(1) { it + 1 }.find { isSafe(delay = it) }
}

class FirewallWithPacket(
        internal val layersByDepth: Map<Int, Layer>) {

    private val maxDepth = layersByDepth.maxBy { it.key }!!.key

    private val packetRangeBand = 0
    private var packetDepth = -1

    private var totalSeverity = 0
    var caught = false

    fun stepThrough(packetSendDelay: Int = 0): Pair<Boolean, Int> {
        repeat(packetSendDelay) { stepScanners() }
        while (packetDepth < maxDepth) step()
        return caught to totalSeverity
    }

    private fun step() {
        // Packet steps forward
        packetDepth++


        layersByDepth[packetDepth]?.let {
            // Caught?
            if (it.scanner == packetRangeBand) {
                caught = true
                val severity = packetDepth * it.range
                totalSeverity += severity
            }
        }

        // Scanners move
        stepScanners()
    }

    private fun stepScanners() {
        layersByDepth.values.forEach(Layer::step)
    }


    class Layer(val range: Int) {
        var scanner: Int = 0
        var direction: Int = 1

        fun step() {
            scanner += direction
            if (scanner < 0) {
                scanner = 1
                direction = 1
            } else if (scanner >= range) {
                scanner = range - 2
                direction = -1
            }
        }

    }

}