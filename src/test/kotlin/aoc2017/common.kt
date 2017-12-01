package aoc2017

import kotlin.test.Test


fun inputFile(fileName: String): String =
        ClassLoader
                .getSystemResource("aoc2017/$fileName")
                .readText()
                .trim()

fun <T> printSolution(
        inputFileName: String,
        fileToType: (String) -> T,
        transform: (T) -> Any) =
        println("$inputFileName: ${inputFile(inputFileName)
                .let(fileToType)
                .let(transform)}")

