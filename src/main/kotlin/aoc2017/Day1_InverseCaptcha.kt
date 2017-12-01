package aoc2017

fun reverseCaptchaSum(
        input: String,
        phase: Int = 1): Int =
        input
                .filterIndexed { i, c ->
                    c == input[(i + phase) % input.length]
                }
                .map { it.toString().toInt() }
                .sum()

fun reverseCaptchaSumHalfway(input: String): Int {
    if (input.length % 2 != 0)
        throw IllegalArgumentException("Undefined for odd length input.")
    val phase = input.length / 2

    return reverseCaptchaSum(input, phase)
}