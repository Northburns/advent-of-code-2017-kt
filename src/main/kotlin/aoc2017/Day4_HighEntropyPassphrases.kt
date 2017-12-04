package aoc2017

fun isValidPassphrase1(passphrase: String) = passphraseToWords(passphrase)
        .let { it.size == it.toSet().size }

private fun passphraseToWords(passphrase: String): List<String> {
    return passphrase
            .split(Regex("\\s+"))
            .map(String::trim)
}

fun isValidPassphrase2(passphrase: String): Boolean {
    if (!isValidPassphrase1(passphrase)) return false
    val anagramPairExists = passphraseToWords(passphrase).let { words ->
        words.filterIndexed { index, word ->
            words
                    .drop(index + 1)
                    .find { word.isAnagram(it) } != null
        }.firstOrNull() != null
    }
    return !anagramPairExists
}

fun String.isAnagram(other: String) = toCharCounts() == other.toCharCounts()

fun String.toCharCounts() = toCharArray()
        .groupBy { it }
        .mapValues { it.value.size }
