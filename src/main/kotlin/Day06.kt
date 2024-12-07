import graph.Coordinate

class Day06 : Day {
    enum class Flag(val value: Int) {
        Empty(0),
        Up(1),
        Right(2),
        Down(4),
        Left(8),
        Wall(16),
    }

    val directionMap = mapOf(
        Coordinate.Up to Flag.Up,
        Coordinate.Right to Flag.Right,
        Coordinate.Down to Flag.Down,
        Coordinate.Left to Flag.Left
    )

    override fun part1(input: List<String>): String {
        var (grid, position) = parseInput(input)
        // start by moving up
        var direction = Coordinate.Up
        var directionFlag = directionMap[direction]!!
        var count = 1
        grid[position] = directionFlag.value
        while (true) {
            var newPosition = position + direction
            if (!grid.has(newPosition)) {
                break
            }
            while (grid[newPosition].hasFlag(Flag.Wall)) {
                direction = direction.turnRight()
                directionFlag = directionMap[direction]!!
                newPosition = position + direction
            }
            val value = grid[newPosition]
            if (value.hasFlag(Flag.Wall)) {
                throw IllegalArgumentException("Invalid state")
            } else if (value.hasFlag(directionFlag)) {
                break
            } else {
                if (value == Flag.Empty.value) {
                    count += 1
                }
                grid[newPosition] = value or directionFlag.value
                position = newPosition
            }
        }
        return count.toString()
    }

    private fun parseInput(input: List<String>): Pair<MutableList<MutableList<Int>>, Coordinate> {
        var position = input.withIndex()
            .find { (_, row) -> row.contains('^') }
            ?.let { (r, row) -> Coordinate(row.indexOf('^'), r) }
            ?: throw IllegalArgumentException("No starting position found")
        val grid = input
            .map {
                it.toCharArray().map { char ->
                    when (char) {
                        '#' -> Flag.Wall.value
                        else -> Flag.Empty.value
                    }
                }.toMutableList()
            }.toMutableList()
        return Pair(grid, position)
    }

    override fun part2(input: List<String>): String {
        val (grid, startPosition) = parseInput(input)
        val originalGrid = grid.map { it.toMutableList() }.toMutableList()
        var position = startPosition
        // start by moving up
        var direction = Coordinate.Up
        var directionFlag = directionMap[direction]!!
        var count = 0
        grid[position] = directionFlag.value
        val hasBeenChecked = grid.map { it.map { it.hasFlag(Flag.Wall) }.toMutableList() }.toMutableList()
        while (true) {
            var newPosition = position + direction
            while (grid.has(newPosition) && grid[newPosition].hasFlag(Flag.Wall)) {
                direction = direction.turnRight()
                directionFlag = directionMap[direction]!!
                newPosition = position + direction
            }
            if (!grid.has(newPosition)) {
                break
            }
            if (!hasBeenChecked[newPosition]) {
                val gridCopy = originalGrid.map { it.toMutableList() }.toMutableList()
                gridCopy[newPosition] = Flag.Wall.value
                hasBeenChecked[newPosition] = true
                if (hasLoop(gridCopy, startPosition)) {
                    count++
                }
            }
            val value = grid[newPosition]
            if (value.hasFlag(Flag.Wall)) {
                throw IllegalArgumentException("Invalid state")
            } else if (value.hasFlag(directionFlag)) {
                break
            } else {
                grid[newPosition] = value or directionFlag.value
                position = newPosition
            }
        }

        return count.toString()
    }

    private fun hasLoop(grid: MutableList<MutableList<Int>>, startPosition: Coordinate): Boolean {
        var direction = Coordinate.Up
        var directionFlag = directionMap[direction]!!
        var position = startPosition
        while (true) {
            var newPosition = position + direction
            while (grid.has(newPosition) && grid[newPosition].hasFlag(Flag.Wall)) {
                direction = direction.turnRight()
                directionFlag = directionMap[direction]!!
                newPosition = position + direction
            }
            if (!grid.has(newPosition)) {
                return false
            }
            val value = grid[newPosition]
            if (value.hasFlag(Flag.Wall)) {
                throw IllegalArgumentException("Invalid state")
            } else if (value.hasFlag(directionFlag)) {
                return true
            } else {
                grid[newPosition] = value or directionFlag.value
                position = newPosition
            }
        }
    }

    fun Int.hasFlag(flag: Flag): Boolean {
        return ((this and flag.value) == flag.value)
    }
}

