package aoc2018.day1

fun frequencyCalc(mods: List<Int>, starting: Int = 0): Int {
    return mods.foldRight(starting, Int::plus)
}

/**
 * No stop condition! Trusting the input :)
 */
fun frequencyCalc2(mods: List<Int>, starting: Int = 0): Int {
    val encountered = mutableSetOf(starting)
    var sum = starting
    while (true) {
        mods.forEach {
            sum += it
            if (!encountered.add(sum))
                return sum
        }
    }
}
