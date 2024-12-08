import graph.Coordinate

class Day08 : Day {
    override fun part1(input: List<String>): String {
        var grid = input.map { row -> row.map { false }.toMutableList() }

        var stations = parseInput(input)
        var total = 0
        for ((char, coords) in stations) {
            coords.forEach { a ->
                coords.forEach { b ->
                    if (a != b) {
                        val diff = a - b
                        if (grid.has(a + diff) && !grid[a + diff]) {
                            grid[a + diff] = true
                            total += 1
                        }
                    }
                }
            }
        }

        return total.toString()
    }

    private fun parseInput(input: List<String>): Map<Char, List<Coordinate>> {
        return input
            .flatMapIndexed { r, row ->
                row.mapIndexed { c, char -> char to Coordinate(c, r) }
            }
            .groupBy({ it.first }, { it.second })
            .minus('.')
    }

    override fun part2(input: List<String>): String {
        var grid = input.map { row -> row.map { false }.toMutableList() }

        var stations = parseInput(input)
        var total = 0
        for ((char, coords) in stations) {
            coords.forEach { a ->
                coords.forEach { b ->
                    if (a != b) {
                        val diff = a - b
                        var position = a
                        while (grid.has(position)) {
                            if (!grid[position]) {
                                grid[position] = true
                                total += 1
                            }
                            position += diff
                        }
                    }
                }
            }

        }
        return total.toString()
    }
}
