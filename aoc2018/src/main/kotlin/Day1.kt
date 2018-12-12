package aoc2018.day1

fun frequencyCalc(mods: List<Int>, starting: Int = 0): Int {
    return mods.foldRight(starting, Int::plus)
}
