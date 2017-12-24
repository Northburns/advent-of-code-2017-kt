package aoc2017

import aoc2017.common.Undefined

data class BridgeComponent(val typeA: Int, val typeB: Int) {

    fun connectable(to: Int) = to == typeA || to == typeB

    companion object {

        fun fromString(string: String): BridgeComponent {
            val (a, b) = string.trim().split("/").map { it.trim().toInt() }
            return BridgeComponent(a, b)
        }

    }

    fun getOtherEnd(previousConnectionType: Int) = when (previousConnectionType) {
        typeA -> typeB
        typeB -> typeA
        else -> throw Undefined("$previousConnectionType not part of $this")
    }
}

data class Bridge(
        val components: List<BridgeComponent>) {

    companion object {
        val EMPTY = Bridge(emptyList())
        fun fromString(string: String) = Bridge(string
                .trim()
                .split("-")
                .map(BridgeComponent.Companion::fromString))
    }

    val strength: Int by lazy { components.sumBy { it.typeA + it.typeB } }
    val length = components.size

    val end by lazy {
        components.fold(0) { previousConnectionType, component ->
            component.getOtherEnd(previousConnectionType)
        }
    }

    operator fun plus(component: BridgeComponent) = Bridge(components + component)
}

fun findValidBridges(
        components: Collection<BridgeComponent>,
        baseBridge: Bridge = Bridge.EMPTY): Set<Bridge> {

    return components
            .filter { it.connectable(baseBridge.end) }
            .flatMap {
                val newBridge = baseBridge + it
                findValidBridges(components - it, newBridge) + newBridge
            }
            .toSet()
}
