package aoc2017

class TuringMachine<S, V>(
        val defaultV: V,
        val defaultS: S,
        stateRules: Collection<StateRule<S, V>>) {


    var state = defaultS
    var tape: MutableMap<Long, V> = mutableMapOf()
    var cursor: Long = 0
    val rules = stateRules.associateBy { it.state }.apply { require(this.size == stateRules.size) }

    inline fun rw(transform: (V) -> V = { it }) =
            transform(read()).also { tape.put(cursor, it) }

    fun read() = tape.computeIfAbsent(cursor) { defaultV }

    fun move(amount: Long) {
        cursor += amount
    }

    tailrec fun step(steps: Int = 1) {
        if (steps <= 0) return

        rules[state]!!.actions[read()]!!.let {
            rw(it.write)
            move(it.move)
            state = it.nextState
        }

        step(steps - 1)
    }

    fun countValues(filter: (V) -> Boolean): Int = tape.values.count(filter)


}

data class StateRule<S, V>(
        val state: S,
        val actions: Map<V, StateAction<S, V>>)

data class StateAction<S, V>(
        val write: (V) -> V,
        val move: Long,
        val nextState: S)