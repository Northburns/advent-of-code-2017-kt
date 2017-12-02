package aoc2017

class SpreadSheet(input: String) {

    val rows: List<SpreadSheet.Row> =
            input.trim()
                    .split('\n')
                    .map { Row(it) }

    val checksum = rows.sumBy { it.checksum }
    val divSum = rows.sumBy { it.divResult ?: 0 }


    class Row(input: String) {

        private val cells = input.trim()
                .split('\t')
                .map { Cell(it) }

        val checksum = cells
                .mapNotNull { it.value }
                .run {
                    // Assumed both min/max can be found!
                    max()!! - min()!!
                }

        /**
         * Divides the larger parametre by the smaller.
         *
         * Returns non-null only if there's no fractional part.
         */
        private fun evenDivide(a: Int, b: Int): Int? = when {
            a < b -> evenDivide(b, a)
            a % b == 0 -> a / b
            else -> null
        }

        val divResult: Int? = cells
                .asSequence()
                .mapNotNull { it.value }
                .mapIndexedNotNull { index, value ->
                    cells
                            .asSequence()
                            .filterIndexed { i, _ -> index != i }
                            .mapNotNull { it.value }
                            .mapNotNull { evenDivide(value, it) }
                            .firstOrNull()
                }
                .firstOrNull()


    }


    class Cell(input: String) {

        val value = input.trim()
                .run {
                    when {
                        !isNullOrBlank() -> toInt()
                        else -> null
                    }
                }

    }

}

