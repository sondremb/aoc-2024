class Day02 : Day {
    override fun part1(input: List<String>): String {
        return input.map(::parseReport).count(::isSafe).toString()
    }

    fun parseReport(report: String): List<Int> = report.split(Utils.WhiteSpace).map(String::toInt).toList()

    fun isSafe(levels: List<Int>): Boolean {
        val (first, second) = levels
        val diffs = when {
            second > first -> mutableListOf(1, 2, 3)
            second < first -> mutableListOf(-1, -2, -3)
            else -> return false
        }
        // check if report is strictly increasing or decreasing
        var prev = first
        for (level in levels.drop(1)) {
            if (diffs.contains(level - prev)) {
                prev = level
            } else {
                return false
            }
        }
        return true
    }

    fun isSafeDampened(levels: List<Int>) = levels.indices.any { isSafeWithout(levels, it) }

    fun isSafeWithout(levels: List<Int>, index: Int): Boolean {
        val newLevels = levels.toMutableList().apply { removeAt(index) }
        return isSafe(newLevels)
    }

    override fun part2(input: List<String>): String {
        val reports = input.map(::parseReport);
        return reports.count(::isSafeDampened).toString()
    }
}