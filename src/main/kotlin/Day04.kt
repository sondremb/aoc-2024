class Day04 : Day {
    override fun part1(input: List<String>): String {
        val wordToFind = "XMAS"
        val firstLetter = wordToFind[0]
        val directions = listOf(
            0 to 1,
            0 to -1,
            1 to 0,
            -1 to 0,
            1 to 1,
            1 to -1,
            -1 to 1,
            -1 to -1
        )
        var count = 0

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, letter ->
                if (letter != firstLetter) {
                    return@forEachIndexed
                }

                count += directions.count { (dx, dy) ->
                    for (k in 1..<wordToFind.length) {
                        val ni = i + k * dx
                        val nj = j + k * dy
                        if (ni !in input.indices || nj !in input[i].indices || input[ni][nj] != wordToFind[k]) {
                            return@count false
                        }
                    }
                    return@count true
                }
            }
        }
        return count.toString()
    }

    override fun part2(input: List<String>): String {
        val letters = "MMSS".toCharArray()
        val directions = listOf(
            listOf((-1 to -1), (-1 to 1), (1 to -1), (1 to 1)),
            listOf((-1 to -1), (1 to -1), (-1 to 1), (1 to 1)),
            listOf((1 to 1), (1 to -1), (-1 to -1), (-1 to 1)),
            listOf((1 to 1), (-1 to 1), (-1 to -1), (1 to -1))
        )
        var count = 0

        fun check(i: Int, j: Int, char: Char): Boolean {
            return i in input.indices && j in input[i].indices && input[i][j] == char
        }

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, letter ->
                if (letter != 'A') {
                    return@forEachIndexed
                }

                count += directions.count {
                    it.withIndex().all { dir ->
                        val (dx, dy) = dir.value
                        val (ni, nj) = i + dx to j + dy
                        check(ni, nj, letters[dir.index])
                    }
                }
            }
        }

        return count.toString()
    }
}