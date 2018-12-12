package aoc2017

import aoc.common.Undefined

class Generator(
        private val name: String,
        initialValue: Long,
        private val rules: Rules,
        val rememberValues: Boolean = false) {

    enum class Rules(val valueOk: (String, Long) -> Boolean) {
        PART1({ _, _ -> true }),
        PART2({ name, value ->
            when (name) {
                "A" -> value % 4 == 0L
                "B" -> value % 8 == 0L
                else -> throw Undefined("")
            }
        })
    }

    companion object {
        private val factors = mapOf(
                "A" to 16807L,
                "B" to 48271L)
        private val div = 2147483647L

        fun operation(name: String, previousValue: Long, rules: Rules): Long {
            var v = previousValue
            do {
                v = v * factors[name]!! % div
            } while (!rules.valueOk(name, v))
            return v
        }
    }

    var value: Long = operation(name, initialValue, rules)

    val rememberedValues = mutableListOf(value)

    fun step(): Long {
        value = operation(name, value, rules)
        if (rememberValues)
            rememberedValues.add(value)
        return value
    }

}

class Judge(val gA: Generator, val gB: Generator) {

    companion object {
        val mask16bit = (1.shl(16) - 1).toLong()
    }

    fun step40MillionTimes() = stepNTimes(40_000_000L)
    fun step5MillionTimes() = stepNTimes(5_000_000L)

    fun stepNTimes(n: Long): Long {
        var count = 0L
        (0L..n).forEach {
            val a = gA.step().and(mask16bit)
            val b = gB.step().and(mask16bit)
            if (a == b) count++
        }
        return count
    }
}