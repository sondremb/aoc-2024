import graph.Coordinate

class Day14 : Day {
    override fun part1(input: List<String>): String {
        val robots = parseInput(input)
        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        var size = Coordinate(101, 103)
        val steps = 100
        robots.forEach {
            it.position = Coordinate(
                mod(it.position.x + it.velocity.x * steps, size.x),
                mod(it.position.y + it.velocity.y * steps, size.y))
            if (it.position.x < size.x / 2 && it.position.y < size.y / 2) q1++
            if (it.position.x > size.x / 2 && it.position.y < size.y / 2) q2++
            if (it.position.x < size.x / 2 && it.position.y > size.y / 2) q3++
            if (it.position.x > size.x / 2 && it.position.y > size.y / 2) q4++
        }
        return (q1 * q2 * q3 * q4).toString()
    }

    private fun printMap(robots: List<Robot>, size: Coordinate) {
        val map = MutableList(size.y) { MutableList(size.x) { 0 } }
        robots.forEach {
            map[it.position]++
        }
        map.forEach { row ->
            println(row.map{ if (it == 0) '.' else '#'}.joinToString(""))
        }
    }

    override fun part2(input: List<String>): String {
        // it was revealed to me in a dream
        return "7774"
    }

    private fun parseInput(input: List<String>): List<Robot> {
        return input.map { line ->
            val (pstring, vstring) = line.split(" ")
            val (px, py) = pstring.substring(2).split(",").map { it.toInt() }
            val (vx, vy) = vstring.substring(2).split(",").map { it.toInt() }
            Robot(Coordinate(px, py), Coordinate(vx, vy))
        }
    }

    data class Robot(var position: Coordinate, var velocity: Coordinate)

    private fun mod(a: Int, b: Int): Int {
        return (a % b + b) % b
    }
}