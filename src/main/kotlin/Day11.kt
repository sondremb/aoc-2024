class Day11 : Day {
    override fun part1(input: List<String>): String {
        val numbers = parseInput(input)
        val memo = mutableMapOf<Pair<Long, Int>, Long>()
        return numbers.sumOf { blink(it, 25, memo) }.toString()
    }

    private fun blink(number: Long, times: Int, memo: MutableMap<Pair<Long, Int>, Long>): Long {
        if (times == 0) return 1L
        if (memo.containsKey(number to times)) return memo[number to times]!!

        val result = compute(number, times, memo)
        memo[number to times] = result
        return result
    }

    private fun compute(number: Long, times: Int, memo: MutableMap<Pair<Long, Int>, Long>): Long {
        if (number == 0L) {
            return blink(1, times - 1, memo)
        }
        val numberAsString = number.toString()
        if (numberAsString.length % 2 == 0) {
            val half = numberAsString.length / 2
            val firstHalf = numberAsString.substring(0, half).toLong()
            val secondHalf = numberAsString.substring(half).toLong()
            return blink(firstHalf, times - 1, memo) +
                    blink(secondHalf, times - 1, memo)
        }
        return blink(number * 2024, times - 1, memo)
    }

    private fun parseInput(input: List<String>): List<Long> {
        return input.flatMap { line -> line.split(Utils.WhiteSpace).map { it.toLong() } }
    }

    override fun part2(input: List<String>): String {
        val numbers = parseInput(input)
        val memo = mutableMapOf<Pair<Long, Int>, Long>()
        return numbers.sumOf { blink(it, 75, memo) }.toString()    }
}
