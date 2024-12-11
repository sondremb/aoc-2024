import graph.Coordinate

class Day10 : Day {
    override fun part1(input: List<String>): String {
        val map = parseInput(input)
        var queue = mutableSetOf<Coordinate>()
        val scores = MutableList(map.size) { MutableList(map[0].size) { mutableSetOf<Coordinate>() } }

        map.forEachIndexed { r, row ->
            row.forEachIndexed { c, cell ->
                if (cell == 9) {
                    val coord = Coordinate(c, r)
                    coord.neighbors
                        .filter { map.has(it) && map[it] == 8 }
                        .forEach { neighbor ->
                            scores[neighbor].add(coord)
                            queue.add(neighbor)
                        }
                }
            }
        }

        for (number in 7 downTo 0) {
            val nextQueue = mutableSetOf<Coordinate>()
            queue.forEach { coord ->
                coord.neighbors
                    .filter { map.has(it) && map[it] == number }
                    .forEach { neighbor ->
                        scores[neighbor] += scores[coord]
                        nextQueue.add(neighbor)
                    }
            }
            queue = nextQueue
        }
        return queue.sumOf { scores[it].size }.toString()
    }

    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { it.map(Char::digitToInt) }
    }

    override fun part2(input: List<String>): String {
        val map = parseInput(input)
        var total = 0
        map.forEachIndexed { r, row ->
            row.forEachIndexed { c, cell ->
                if (cell == 0) {
                    total += trailRec(map, Coordinate(c, r))
                }
            }
        }
        return total.toString()
    }

    private fun trailRec(map: List<List<Int>>, coordinate: Coordinate): Int {
        if (!map.has(coordinate)) {
            return 0
        }
        val value = map[coordinate]
        if (value == 9) {
            return 1
        }
        return coordinate.neighbors
            .filter { map.has(it) && map[it] == value + 1}
            .sumOf { trailRec(map, it) }
    }
}