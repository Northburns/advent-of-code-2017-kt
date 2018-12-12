package aoc2017

import aoc2017.common.Undefined

sealed class DuetInstruction {

    /**
     * @return false when waiting
     */
    abstract fun execute(r: DuetRegistry)

    data class Snd(val value: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) {
            when (r) {
                is DuetRegistry.DuetRegistryPart1 -> r.playingFrequency = value.fold(r)
                is DuetRegistry.DuetRegistryPart2 -> r.send(value.fold(r))
            }
        }
    }

    data class Set(val register: String, val value: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) = r.modify(register) { value.fold(r) }
    }

    data class Add(val register: String, val addBy: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) = r.modify(register) { it + addBy.fold(r) }
    }

    data class Mul(val register: String, val multiplyBy: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) = r.modify(register) { it * multiplyBy.fold(r) }
    }

    data class Mod(val register: String, val mod: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) = r.modify(register) { it % mod.fold(r) }
    }

    data class Rcv(val register: String) : DuetInstruction() {
        override fun execute(r: DuetRegistry) {
            when (r) {
                is DuetRegistry.DuetRegistryPart1 -> if (r[register] != 0L) r.recoverPlayingFrequency()
                is DuetRegistry.DuetRegistryPart2 -> r.receive(register)
            }
        }
    }

    data class Jgz(val value: DuetInstructionValue, val jumpOffset: DuetInstructionValue) : DuetInstruction() {
        override fun execute(r: DuetRegistry) {
            if (value.fold(r) > 0)
                r.jump(jumpOffset.fold(r) - 1) // -1 to negate the head's movement
        }
    }

    data class DuetInstructionValue(val register: String?, val value: Long?) {
        init {
            if ((register == null && value == null) || (register != null && value != null))
                throw Undefined("This is Either.")
        }

        fun fold(registry: DuetRegistry) = value ?: registry[register!!]

        companion object {
            fun fromstring(string: String) = string.toLongOrNull()
                    ?.let { DuetInstructionValue(null, it) }
                    ?: DuetInstructionValue(string, null)
        }

    }

    companion object {
        fun fromString(string: String): DuetInstruction {
            val parts = string.trim().split(" ")
            val instr = parts[0]
            val p1 = parts[1]
            val p2 = parts.getOrNull(2)
            return when (instr) {
                "snd" -> Snd(DuetInstructionValue.fromstring(p1))
                "set" -> Set(p1, DuetInstructionValue.fromstring(p2!!))
                "add" -> Add(p1, DuetInstructionValue.fromstring(p2!!))
                "mul" -> Mul(p1, DuetInstructionValue.fromstring(p2!!))
                "mod" -> Mod(p1, DuetInstructionValue.fromstring(p2!!))
                "rcv" -> Rcv(p1)
                "jgz" -> Jgz(DuetInstructionValue.fromstring(p1), DuetInstructionValue.fromstring(p2!!))
                else -> throw Undefined("Unkown instruction: $string")
            }
        }
    }
}

/**
 * A bit wierd class hierarchy utilizing... thing...
 */
sealed class DuetRegistry(val instructions: List<DuetInstruction>) {
    var head: Int = 0
    val registers = mutableMapOf<String, Long>()

    companion object {
        val defaultRegistryValue = 0L
    }

    operator fun get(register: String): Long = registers.computeIfAbsent(register) { defaultRegistryValue }

    fun modify(register: String, transform: (Long) -> Long) {
        registers.put(register, transform(this[register]))
    }

    fun jump(jumpOffset: Long) {
        head = Math.addExact(head, jumpOffset.toInt())
    }

    private fun isOutOfBounds() = head < 0 || head >= instructions.size

    fun stepUntilRecover() {
        while (!isOver()) step()
    }

    open fun isOver() = isOutOfBounds()

    fun step() = instructions[head++].execute(this)

    class DuetRegistryPart1(instructions: List<DuetInstruction>) : DuetRegistry(instructions) {

        var playingFrequency: Long? = null
        var recoveredFrequency: Long? = null

        override fun isOver(): Boolean {
            return super.isOver() || recoveredFrequency != null
        }

        fun recoverPlayingFrequency() {
            recoveredFrequency = playingFrequency
        }

    }

    class DuetRegistryPart2(
            instructions: List<DuetInstruction>,
            p: Long,
            val incoming: MutableList<Long>,
            val outgoing: MutableList<Long>) : DuetRegistry(instructions) {

        init {
            modify("p") { p }
        }

        fun send(value: Long) {
            outgoing.add(value)
            sendCount++
        }

        fun receive(register: String) {
            if (incoming.isEmpty()) {
                head-- // negate head's movement
                waiting = true
            } else {
                waiting = false
                modify(register) { incoming.removeAt(0) }
            }
        }

        var waiting: Boolean = false
        var sendCount: Long = 0
    }


}

class Duet(instructions: List<DuetInstruction>) {

    val incoming0 = mutableListOf<Long>()
    val incoming1 = mutableListOf<Long>()

    val r0 = DuetRegistry.DuetRegistryPart2(instructions, 0, incoming0, incoming1)
    val r1 = DuetRegistry.DuetRegistryPart2(instructions, 1, incoming1, incoming0)


    private fun isOver(): Boolean {
        if (r0.waiting && r1.waiting) return true // deadlock
        if (r0.waiting && r1.isOver()) return true
        if (r0.isOver() && r1.waiting) return true
        if (r0.isOver() && r1.isOver()) return true
        return false
    }

    fun stepThrough() {
        while (!isOver()) {
            stepBoth()
        }
    }

    fun stepBoth() {
        r0.step()
        r1.step()
    }

}