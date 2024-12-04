import kotlin.math.abs

class Day01 : Day {
    fun parseLines(lines: List<String>): Pair<List<Int>, List<Int>> {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        lines.forEach {
            if (it.isBlank()) return@forEach
            val (l, r) = it.split(Regex("\\s+")).map(String::toInt)
            left.add(l)
            right.add(r)
        }
        return Pair(left, right)
    }

    /*const val example = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """*/

    override fun part1(input: List<String>): String {
        val (left, right) = parseLines(input)
        val ans = left.sorted().zip(right.sorted()).sumOf { abs(it.first - it.second) }
        return ans.toString()
    }

    override fun part2(input: List<String>): String {
        val (left, right) = parseLines(input)
        val occurences = right.groupingBy { it }.eachCount()
        val ans = left.sumOf { it * (occurences[it] ?: 0) }
        return ans.toString()
    }
}


