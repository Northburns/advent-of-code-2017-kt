package aoc2017

import aoc2017.common.Undefined
import kotlin.math.sqrt

sealed class CcInstruction {

    abstract fun execute(r: CcRegistry)

    data class Sub(val register: String, val value: CcInstructionValue) : CcInstruction() {
        override fun execute(r: CcRegistry) = r.modify(register) { it - value.fold(r) }
    }

    data class Set(val register: String, val value: CcInstructionValue) : CcInstruction() {
        override fun execute(r: CcRegistry) = r.modify(register) { value.fold(r) }
    }

    data class Mul(val register: String, val multiplyBy: CcInstructionValue) : CcInstruction() {
        override fun execute(r: CcRegistry) = r.modify(register) { it * multiplyBy.fold(r) }
    }

    data class Jnz(val value: CcInstructionValue, val jumpOffset: CcInstructionValue) : CcInstruction() {
        override fun execute(r: CcRegistry) {
            if (value.fold(r) != 0L)
                r.head += jumpOffset.fold(r).toInt() - 1 // -1 to negate the head's movement
        }
    }

    data class CcInstructionValue(val register: String?, val value: Long?) {
        init {
            if ((register == null && value == null) || (register != null && value != null))
                throw Undefined("This is Either.")
        }

        fun fold(registry: CcRegistry) = value ?: registry.registers[register]!!

        companion object {
            fun fromstring(string: String) = string.toLongOrNull()
                    ?.let { CcInstructionValue(null, it) }
                    ?: CcInstructionValue(string, null)
        }

        override fun toString() = when {
            register != null -> "r[$register]"
            value != null -> "$value"
            else -> throw Undefined()
        }
    }


    companion object {
        fun fromString(string: String): CcInstruction {
            val parts = string.trim().split(" ")
            val instr = parts[0]
            val p1 = parts[1]
            val p2 = parts[2]
            return when (instr) {
                "set" -> Set(p1, CcInstructionValue.fromstring(p2))
                "sub" -> Sub(p1, CcInstructionValue.fromstring(p2))
                "mul" -> Mul(p1, CcInstructionValue.fromstring(p2))
                "jnz" -> Jnz(CcInstructionValue.fromstring(p1), CcInstructionValue.fromstring(p2))
                else -> throw Undefined("Unkown instruction: $string")
            }
        }
    }
}

class CcRegistry(val program: List<CcInstruction>) {
    val registers = "abcdefgh".map { it.toString() to 0L }.toMap().toMutableMap()
    var head = 0

    var mulCount = 0L

    fun step() {
        val oldhead = head
        program[head++].let {
            it.execute(this)
            if (it is CcInstruction.Mul)
                mulCount++
            println("$registers, head= $oldhead -> $head, instr= $it")
        }
    }

    fun modify(register: String, transform: (Long) -> Long) {
        registers.put(register, transform(registers.get(register)!!))
    }

    fun stepThrough() {
        while (head in 0.until(program.size))
            step()
    }

}

/**
 * Optimized by hand to Kotlin, oh my!
 *
 * Of course this doesn't show the input was transformed to this...
 */
fun optimizedProgram(): Int {
    // Program constants (a=1 is baked into them)
    val min = 108400L
    val step = 17L
    val max = min + 1000L * step

    fun Long.isComposite() =
            (2..sqrt(this.toDouble()).toLong())
                    .any { this % it == 0L }

    // Program
    return (min..max step step).count { it.isComposite() }
}

