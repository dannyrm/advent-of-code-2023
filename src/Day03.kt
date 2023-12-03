data class Entity(val value: String, val position: List<Point>)
data class Point(val x: Int, val y: Int)

fun main() {

    fun extract(line: String, regex: String, lineNumber: Int): List<Entity>? {
        val partNumberRegex = Regex(regex)
        val allParts = partNumberRegex.findAll(line)

        val partsList = mutableListOf<Entity>()

        allParts.forEach {
            partsList.add(Entity(it.value, it.range.map { Point(it, lineNumber) }))
        }

        return partsList.ifEmpty { null }
    }

    fun arePointsAdjacent(point1: Point, point2: Point) : Boolean {
        return  Point(point1.x+1, point1.y+1) == point2 ||
                Point(point1.x-1, point1.y) == point2 ||
                Point(point1.x+1, point1.y) == point2 ||
                Point(point1.x, point1.y-1) == point2 ||
                Point(point1.x, point1.y+1) == point2 ||
                Point(point1.x-1, point1.y-1) == point2 ||
                Point(point1.x-1, point1.y+1) == point2 ||
                Point(point1.x+1, point1.y-1) == point2
    }

    // Bit of a mess this bit. Assume that entity2 always only has one position
    fun areEntitiesAdjacent(entity1: Entity, entity2: Entity): Boolean {
        return entity1.position.map {
            arePointsAdjacent(it, entity2.position[0])
        }.any { it }
    }

    fun part1(input: List<String>): Int {
        val parts = input.mapIndexed { index, s ->
            extract(s,"\\d+", index)
        }.filterNotNull().flatten()

        val symbols = input.mapIndexed { index, s ->
            extract(s,"[^.\\d]", index)
        }.filterNotNull().flatten()

        val adjacentParts = mutableListOf<Entity>()

        for (part in parts) {
            for (symbol in symbols) {
                if (areEntitiesAdjacent(part, symbol)) {
                    adjacentParts.add(part)
                    break
                }
            }
        }

        return adjacentParts.sumOf { it.value.toInt() }
    }

    fun part2(input: List<String>): Int {
        val parts = input.mapIndexed { index, s ->
            extract(s,"\\d+", index)
        }.filterNotNull().flatten()

        val symbols = input.mapIndexed { index, s ->
            extract(s,"[^.\\d]", index)
        }.filterNotNull().flatten()

        val adjacentSymbols = mutableMapOf<Entity, MutableList<Int>>()

        for (symbol in symbols) {
            adjacentSymbols.put(symbol, mutableListOf())
            for (part in parts) {
                if (areEntitiesAdjacent(part, symbol)) {
                    adjacentSymbols[symbol]!!.add(part.value.toInt())
                }
            }
        }

        return adjacentSymbols.filterValues { it.size == 2 }.values.sumOf { it.reduce { acc, i -> acc * i } }
    }

    val testInputPart1 = readInput("Day03_test_part1")
    check(part1(testInputPart1) == 4361)

    val input = readInput("Day03")

    part1(input).println()

    val testInputPart2 = readInput("Day03_test_part2")
    check(part2(testInputPart2) == 467835)

    part2(input).println()
}
