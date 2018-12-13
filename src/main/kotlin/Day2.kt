package aoc2018


fun day2findboxes(list: List<String>): String {
    val x1 = list
            .flatMap { l1 ->
                list
                        .filter { l1 != it }
                        .map { setOf(l1, it) }
            }.toSet().map { it.toList().run { first() to last() } }
    val x2 = x1.associate { it to stringDiffCharCount(it.first, it.second) }
    val x3 = x2.minBy { it.value }!!.key
    return stringsCommonChars(x3.first, x3.second)
}

private fun stringsCommonChars(s1: String, s2: String): String {
    if (s1.length != s2.length)
        throw IllegalArgumentException("Strings of different length.")
    return s1.toCharArray()
            .zip(s2.toCharArray())
            .filter { (a,b) -> a == b }
            .map { it.first }
            .joinToString(separator = "")
}

private fun stringDiffCharCount(s1: String, s2: String): Int {
    if (s1.length != s2.length)
        throw IllegalArgumentException("Strings of different length.")
    return s1.toCharArray()
            .zip(s2.toCharArray())
            .count { (a, b) -> a != b }
}

fun day2Checksum(list: List<String>): Int {
    return list.count(::hasExactlyTwoOfAnyLetter) *
            list.count(::hasExactlyThreeOfAnyLetter)
}

private fun hasExactlyTwoOfAnyLetter(s: String) =
        hasExactlyNumberOfLetters(s, 2)

private fun hasExactlyThreeOfAnyLetter(s: String) =
        hasExactlyNumberOfLetters(s, 3)

private fun hasExactlyNumberOfLetters(s: String, n: Int): Boolean {
    return s
            .toCharArray()
            .groupBy { it }
            .mapValues { it.value.size }
            .values
            .contains(n)
}
