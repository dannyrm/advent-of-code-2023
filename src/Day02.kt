import kotlin.math.max

data class Round(val red: Int, val green: Int, val blue: Int)
data class Game(val id: Int, val rounds: List<Round>)

fun main() {
    fun parseInput(line: String): Game {
        val match = Regex("Game (\\d+):(.+)").find(line)!!.groups
        val id = match[1]!!.value.toInt()
        val gameData = match[2]!!.value

        val roundStrings = gameData.split(";")
        val rounds = mutableListOf<Round>()

        for (roundString in roundStrings) {
            var numRed = 0
            var numGreen = 0
            var numBlue = 0

            val coloursFound = roundString.split(",")
            for (colourFound in coloursFound) {
                val matchColour = Regex("(\\d+) (red|green|blue)").find(colourFound)!!.groups
                val num = matchColour[1]!!.value.toInt()
                val colour = matchColour[2]!!.value

                when (colour) {
                    "red" -> numRed = num
                    "green" -> numGreen = num
                    "blue" -> numBlue = num
                }
            }

            rounds.add(Round(numRed, numGreen, numBlue))
        }

        return Game(id, rounds)
    }

    fun part1(input: List<String>): Int {
        val games = input.map {
            parseInput(it)
        }

        val possibleGames = games.map { it.id }.toMutableList()

        games.forEach {
            it.rounds.forEach {round ->
                if (round.red > 12 || round.green > 13 || round.blue > 14) {
                    possibleGames.remove(it.id)
                }
            }
        }

        return possibleGames.sum()
    }

    fun part2(input: List<String>): Int {
        val games = input.map {
            parseInput(it)
        }

        var power = 0

        games.forEach {
            var largestRed = 0
            var largestGreen = 0
            var largestBlue = 0

            it.rounds.forEach {round ->
                largestRed = max(largestRed, round.red)
                largestGreen = max(largestGreen, round.green)
                largestBlue = max(largestBlue, round.blue)
            }

            power += (largestRed * largestGreen * largestBlue)
        }

        return power
    }

    val testInputPart1 = readInput("Day02_test_part1")
    check(part1(testInputPart1) == 8)

    val input = readInput("Day02")

    part1(input).println()

    val testInputPart2 = readInput("Day02_test_part2")
    check(part2(testInputPart2) == 2286)

    part2(input).println()
}
