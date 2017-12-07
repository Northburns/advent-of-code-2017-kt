package aoc2017

import aoc2017.common.Undefined
import aoc2017.common.allEqual
import aoc2017.common.findOneNonEqual

fun parseProgramsFromInput(input: String) = input
        .lines()
        .map {
            val (name, weight, immediatelyAbove) = it
                    .split(" -> ")
                    .run {
                        val (name, weight) = first().split(" ")
                        Triple(
                                name,
                                weight.removeSurrounding("(", ")"),
                                if (size == 1) null else get(1).trim().split(", "))
                    }
            Program(
                    name,
                    weight.toLong(),
                    immediatelyAbove?.toSet() ?: emptySet())
        }

data class Program(
        val name: String,
        val weight: Long,
        val immediatelyAbove: Set<String>)


class Tower(
        programs: Collection<Program>) {

    constructor(input: String) : this(parseProgramsFromInput(input))

    private val programsParent: Map<String, String>
    val rootProgram: String


    /**
     * For random access
     */
    val programsByName = programs.associateBy { it.name }


    private val discs: List<Disc>

    init {

        programsParent = programs
                .fold(mutableMapOf<String, String>()) { acc, p ->
                    p.immediatelyAbove.forEach {
                        if (it in acc) throw Undefined("$it already has parent in map.")
                        acc.put(it, p.name)
                    }
                    acc
                }

        rootProgram = programs
                .asSequence()
                .map { it.name }
                .find { it !in programsParent }
                ?: throw Undefined("Didn't find a program that didn't have a parent.")

        discs = programs
                .map {
                    Disc(
                            it.name,
                            it.immediatelyAbove.associateBy({ it }, { programsWeight(it) }))
                }
    }

    fun findUnbalancedDisks() =
            discs
                    .filter {
                        !it.totalWeightsByHeld.values.allEqual()
                    }

    fun findDeepestUnbalancedDisk() =
            findUnbalancedDisks()
                    .maxBy { programDepth(it.heldBy) }!!

    private tailrec fun programDepth(name: String, depth: Int = 0): Int {
        if (programsByName[name]!!.name == rootProgram) return depth
        return programDepth(programsParent[name]!!, depth + 1)
    }

    /**
     * Quite recursive.
     */
    private fun programsWeight(name: String): Long {
        return programsByName[name]!!.run {
            weight + immediatelyAbove.map { programsWeight(it) }.sum()
        }
    }

    inner class Disc(
            val heldBy: String,
            val totalWeightsByHeld: Map<String, Long>) {

        /**
         * @return null if weights ok. Otherwise
         * the single "wrong weight" program, and weight adjustment.
         */
        fun validateWeights(): Pair<Program, Long>? {
            val differingWeight = totalWeightsByHeld.values.findOneNonEqual() ?: return null
            val name = totalWeightsByHeld
                    .filterValues { it == differingWeight }
                    .keys
                    .first()
            val targetWeight = totalWeightsByHeld
                    .filterValues { it != differingWeight }
                    .values
                    .first()
            return programsByName[name]!! to (targetWeight - differingWeight)

        }

    }


}



