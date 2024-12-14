import graph.Coordinate
import kotlin.math.abs

class Day12 : Day {
    override fun part1(input: List<String>): String {
        val map = parseInput(input)
        val visited = MutableList(map.size) { MutableList(map[0].size) { false } }

        return map.withIndex().sumOf { (r, row) ->
            row.withIndex().sumOf { (c, char) ->
                val coord = Coordinate(c, r)
                if (visited[coord]) 0 else check(coord, char, map, visited)
            }
        }.toString()
    }

    fun check(coord: Coordinate, char: Char, map: List<List<Char>>, visited: MutableList<MutableList<Boolean>>): Int {
        var area = 0
        var perimeter = 0
        val sides = mutableSetOf<Pair<Int, Coordinate>>()
        val stack = mutableListOf(coord)
        while (stack.isNotEmpty()) {
            val current = stack.removeAt(0)
            if (visited[current]) continue
            visited[current] = true
            area++
            val directions = listOf(Coordinate.Up, Coordinate.Down, Coordinate.Left, Coordinate.Right)
            directions.forEach { dir ->
                val neighbor = current + dir
                if (!map.has(neighbor) || map[neighbor] != char) {
                    perimeter++
                    val relevantCoordinate = abs(current.x * dir.x + current.y * dir.y)
                    sides.add(Pair(relevantCoordinate, dir))
                } else if (!visited[neighbor]) {
                    stack.add(neighbor)
                }
            }
        }
        return area * sides.size
    }

    fun check2(coord: Coordinate, char: Char, map: List<List<Char>>, visited: MutableList<MutableList<Boolean>>): Int {
        var area = 0
        val sides = mutableSetOf<Pair<Int, Coordinate>>()
        val stack = mutableListOf(coord)
        while (stack.isNotEmpty()) {
            val current = stack.removeAt(0)
            if (visited[current]) continue
            visited[current] = true
            area++
            val directions = listOf(Coordinate.Up, Coordinate.Down, Coordinate.Left, Coordinate.Right)
            directions.forEach { dir ->
                val neighbor = current + dir
                if (!map.has(neighbor) || map[neighbor] != char) {
                    val relevantCoordinate = abs(current.x * dir.x + current.y * dir.y)
                    sides.add(Pair(relevantCoordinate, dir))
                } else if (!visited[neighbor]) {
                    stack.add(neighbor)
                }
            }
        }
        return area * sides.size
    }

    private fun parseInput(input: List<String>): List<List<Char>> {
        return input.map { it.toList() }
    }

    override fun part2(input: List<String>): String {
        val map = parseInput(input)
        val visited = MutableList(map.size) { MutableList(map[0].size) { false } }


        return map.withIndex().sumOf { (r, row) ->
            row.withIndex().sumOf { (c, char) ->
                val coord = Coordinate(c, r)
                if (visited[coord]) 0 else check2(coord, char, map, visited)
            }
        }.toString()
    }
}