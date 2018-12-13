package aoc2017

const val GROUP_OPEN = '{'
const val GROUP_CLOSE = '}'
const val GARBAGE_OPEN = '<'
const val GARBAGE_CLOSE = '>'
const val IGNORE_NEXT = '!'

private fun filterIgnored(stream: String): String {
    var head = 0
    val sb = StringBuilder()
    while (head < stream.length) {
        if (stream[head] == IGNORE_NEXT) head++
        else sb.append(stream[head])
        head++
    }
    return sb.toString()
}

fun process(stream: String) = processInternal(filterIgnored(stream))

/**
 * Clean any [IGNORE_NEXT] from input beforehand.
 *
 * This is really recursive!
 */
private fun processInternal(stream: String): List<StreamPart> {
    // Element's second is the next position to continue processing the stream.
    val (part, next) = when (stream.firstOrNull()) {
        null -> Text("") to 1
        GARBAGE_OPEN -> {
            val garbageEnd = 1 + stream.indexOf(GARBAGE_CLOSE)
            Garbage(stream.substring(0, garbageEnd)) to garbageEnd
        }
        GROUP_OPEN -> {
            // Read ahead to find the end of this group.
            var groupLevel = 0
            var inGarbage = false
            var head = 0
            do {
                val previousChar = if (head == 0) null else stream[head - 1]
                if (!inGarbage && previousChar != IGNORE_NEXT)
                    when (stream[head]) {
                        GROUP_OPEN -> groupLevel++
                        GROUP_CLOSE -> groupLevel--
                        GARBAGE_OPEN -> inGarbage = true
                    }
                if (stream[head] == GARBAGE_CLOSE) inGarbage = false
                head++
            } while (groupLevel > 0)
            val insideThisGroup = stream.substring(1, head - 1)
            Group(process(insideThisGroup)) to head
        }
        else -> {
            // Read ahead to find any starting thing.
            val end = listOf(
                    stream.indexOf(GROUP_OPEN),
                    stream.indexOf(GARBAGE_OPEN))
                    .filter { it != -1 }
                    .min() ?: stream.length
            Text(stream.substring(0, end)) to end
        }
    }
    val result = listOf(part)
    return if (next >= stream.length) result
    else result + process(stream.substring(next))
}

sealed class StreamPart

data class Garbage(val garbage: String) : StreamPart() {
    fun garbageCharCount() = garbage.length - 2
}

data class Group(val content: List<StreamPart>) : StreamPart() {

    fun scoreTotal(selfScore: Int = 1): Int =
            selfScore + content.sumBy {
                (it as? Group)
                        ?.scoreTotal(selfScore + 1) ?: 0
            }

    fun groupCount(): Int =
            1 + content.sumBy {
                (it as? Group)
                        ?.groupCount() ?: 0
            }

    fun garbageCharCount(): Int =
            content.sumBy {
                when (it) {
                    is Garbage -> it.garbageCharCount()
                    is Group -> it.garbageCharCount()
                    else -> 0
                }
            }

}

data class Text(val text: String) : StreamPart()
