import graph.Coordinate

private const val wall = '#'
private const val empty = '.'
private const val box = 'O'
private const val robot = '@'
private const val boxLeft = '['
private const val boxRight = ']'

class Day15 : Day {

    override fun part1(input: List<String>): String {
        val (map, instructions) = parseInput(input)
        var robotPos = findRobot(map)
        instructions.forEach { instruction ->
            val direction = getDirection(instruction)
            val nextPos = robotPos + direction
            if (!map.has(nextPos) || map[nextPos] == wall) return@forEach
            if (map[nextPos] == empty) {
                map[robotPos] = empty
                map[nextPos] = robot
                robotPos = nextPos
            } else {
                val emptyPos = findFirstEmpty(map, robotPos, direction)
                if (emptyPos != null) {
                    map[robotPos] = empty
                    map[nextPos] = robot
                    map[emptyPos] = box
                    robotPos = nextPos
                }
            }
        }
        return calculateScore(map).toString()
    }

    private fun printMap(map: List<List<Char>>) {
        map.forEach { row ->
            println(row.joinToString(""))
        }
    }

    private fun calculateScore(map: List<List<Char>>): Long {
        return map.withIndex().sumOf { (r, row) ->
            row.withIndex().sumOf { (c, char) ->
                if (char == box) 100L * r + c else 0
            }
        }
    }

    private fun findFirstEmpty(map: List<List<Char>>, position: Coordinate, direction: Coordinate): Coordinate? {
        var current = position + direction
        while (map.has(current)) {
            if (map[current] == empty) return current
            if (map[current] == wall) return null
            current += direction
        }
        return null
    }

    private fun getDirection(char: Char): Coordinate = when (char) {
        '^' -> Coordinate.Up
        'v' -> Coordinate.Down
        '<' -> Coordinate.Left
        '>' -> Coordinate.Right
        else -> throw IllegalArgumentException("Invalid instruction")
    }

    private fun parseInput(input: List<String>): Pair<MutableList<MutableList<Char>>, CharArray> {
        val map = mutableListOf<MutableList<Char>>()
        val iterator = input.iterator()
        var line = iterator.next()
        do {
            map.add(line.toMutableList())
            line = iterator.next()
        } while (line.isNotEmpty())
        var instructions = iterator.next().toCharArray()
        while (iterator.hasNext()) {
            line = iterator.next()
            instructions += line.toCharArray()
        }
        return (map to instructions)
    }

    private fun findRobot(map: List<List<Char>>): Coordinate {
        map.withIndex().forEach { (r, row) ->
            row.withIndex().forEach { (c, char) ->
                if (char == robot) return Coordinate(c, r)
            }
        }
        throw IllegalArgumentException("Robot not found")
    }

    private fun adjustMap(map: List<List<Char>>): List<MutableList<Char>> {
        return map.map { row ->
            row.flatMap { char ->
                when (char) {
                    box -> listOf(boxLeft, boxRight)
                    robot -> listOf(robot, empty)
                    else -> listOf(char, char)
                }
            }.toMutableList()
        }
    }

    private fun canMove(map: List<List<Char>>, pos: Coordinate, direction: Coordinate): Boolean {
        var current = pos + direction
        while (map.has(current)) {
            if (map[current] == wall) return false
            if (map[current] == box) return false
            if (map[current] == empty) return true
            current += direction
        }
        return false2
    }

    override fun part2(input: List<String>): String {
        val (rawMap, instructions) = parseInput(input)
        val map = adjustMap(rawMap)
        var robotPos = findRobot(map)
        printMap(map)
        instructions.forEach { instruction ->
            val direction = getDirection(instruction)
            val nextPos = robotPos + direction
            if (!map.has(nextPos) || map[nextPos] == wall) return@forEach
            if (map[nextPos] == empty) {
                map[robotPos] = empty
                map[nextPos] = robot
                robotPos = nextPos
            } else {
                val emptyPos = findFirstEmpty(map, robotPos, direction)
                if (emptyPos != null) {
                    map[robotPos] = empty
                    map[nextPos] = robot
                    map[emptyPos] = box
                    robotPos = nextPos
                }
            }
        }
        return ""
    }
}