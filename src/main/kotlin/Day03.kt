class Day03 : Day {
    override fun part1(input: List<String>): String {
        return input.sumOf(::handleLine).toString()
    }

    fun handleLine(line: String): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        val matches = regex.findAll(line).toList()
        return matches.sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    override fun part2(input: List<String>): String {
        return input.joinToString("").let(::handleLine2).toString()
    }

    fun handleLine2(line: String): Int {
        val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
        val matches = regex.findAll(line).toList()
        val toggles = Regex("""do(n't)?\(\)""").findAll(line).toList()
        val allMatches = (matches + toggles).sortedBy { it.range.first }
        var enabled = true
        var result = 0
        for (match in allMatches) {
            when {
                match.groups[0]?.value == "do()" -> enabled = true
                match.groups[0]?.value == "don't()" -> enabled = false
                !enabled -> continue
                enabled -> result += match.groupValues[1].toInt() * match.groupValues[2].toInt()
            }
        }
        return result
    }
}