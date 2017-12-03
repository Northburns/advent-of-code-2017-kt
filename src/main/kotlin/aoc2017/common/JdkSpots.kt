package aoc2017.common

/*
  I have this added challenge of 'noJdk'. So, I end up re-implementing all really useful JDK methods here.

  Remind me again, why I added this rule for myself :)
 */

fun <K, V> MutableMap<K, V>.computeIfAbsentK(
        key: K,
        mappingFunction: (K) -> V)
        = getOrPut(key) { mappingFunction(key) }