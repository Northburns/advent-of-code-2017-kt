package aoc2017

import aoc.common.computeIfAbsentK
import kotlin.math.max


enum class Compare(
        private val string: String,
        val p: (MutableRegister, Int) -> Boolean) {
    LT("<", { r, i -> r.value < i }),
    GT(">", { r, i -> r.value > i }),
    LE("<=", { r, i -> r.value <= i }),
    GE(">=", { r, i -> r.value >= i }),
    EQ("==", { r, i -> r.value == i }),
    NOT("!=", { r, i -> r.value != i });

    companion object {
        fun fromString(string: String) = values().find { it.string == string }!!
    }
}

enum class Operation(
        private val string: String,
        val a: (MutableRegister, Int) -> Unit) {
    INC("inc", { r, i -> r.value += i }),
    DEC("dec", { r, i -> r.value -= i });

    companion object {
        fun fromString(string: String) = values().find { it.string == string }!!
    }
}

data class Condition(
        private val register: String,
        private val compare: Compare,
        private val value: Int) {
    fun test(registry: Registry) = compare.p(registry.getRegister(register), value)


}

data class Instruction(
        private val register: String,
        private val operation: Operation,
        private val value: Int,
        private val `if`: Condition) {

    fun executeOn(registry: Registry) {
        if (`if`.test(registry)) operation.a(registry.getRegister(register), value)
    }

    companion object {
        fun fromString(string: String): Instruction = string
                .split(" if ")
                .let { (instructionPart, conditionPart) ->
                    val condition = conditionPart
                            .split(" ")
                            .let { (register, compare, value) ->
                                Condition(
                                        register,
                                        Compare.fromString(compare),
                                        value.toInt())
                            }
                    instructionPart
                            .split(" ")
                            .let { (register, operation, value) ->
                                Instruction(
                                        register,
                                        Operation.fromString(operation),
                                        value.toInt(),
                                        condition)
                            }

                }
    }
}

class Registry {

    internal val registers = mutableMapOf<String, MutableRegister>()

    private var instructions: List<Instruction> = emptyList()
    private var head: Int = 0

    private var highWaterMark = 0

    fun reset(input: List<Instruction>) {
        registers.clear()
        instructions = input
        head = 0
        highWaterMark = Int.MIN_VALUE
    }

    fun step() {
        instructions[head].executeOn(this)
        head++
        highWaterMark = max(highWaterMark, largestValue())
    }

    fun isDone() = head >= instructions.size

    fun stepThrough() {
        while (!isDone()) step()
    }

    internal fun getRegister(s: String) = registers.computeIfAbsentK(s) { MutableRegister() }
    operator fun get(s: String) = getRegister(s).value
    fun largestValue() = registers.values.maxBy { it.value }!!.value
    fun largestValueEver() = highWaterMark

}

data class MutableRegister(var value: Int = 0)
