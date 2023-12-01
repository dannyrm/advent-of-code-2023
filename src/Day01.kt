fun main() {
    fun lineToCalibrationValue(line: String): Int {
        val digitsOnly = line.filter { Character.isDigit(it) }

        return "${digitsOnly.first()}${digitsOnly.last()}".toInt()
    }

    fun convertWordsToDigits2(line: String): String {
        val numberStrings = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")

        val matchList : MutableList<Pair<Int, String>> = mutableListOf()

        numberStrings.forEach { s ->
            var lastMatchIndex = 0

            do {
                val match = line.findAnyOf(listOf(s), lastMatchIndex)

                if (match != null) {
                    matchList.add(match)
                    lastMatchIndex = match.first+1
                }
            } while (match != null)
        }

        var result = ""

        matchList.sortBy { it.first }

        matchList.forEach {
            result += it.second.toIntOrNull() ?: (numberStrings.indexOf(it.second) + 1)
        }

        return result
    }

    fun part1(input: List<String>): Int {
        var total = 0

        for (line in input) {
            total += lineToCalibrationValue(line)
        }

        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0

        for (line in input) {
            val digitsOnly = convertWordsToDigits2(line)
            total += "${digitsOnly.first()}${digitsOnly.last()}".toInt()
        }

        return total
    }

    val testInputPart1 = readInput("Day01_test_part1")
    check(part1(testInputPart1) == 142)

    val input = readInput("Day01")

    part1(input).println()

    val testInputPart2 = readInput("Day01_test_part2")
    check(part2(testInputPart2) == 324)

    part2(input).println()
}
