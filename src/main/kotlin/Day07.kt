class Day07 : Day {

    override fun part1(input: List<String>): String {
        val operators = listOf<(a: Long, b: Long) -> Long>(
            { a, b -> a + b },
            { a, b -> a * b },
        )
        return parseInput(input)
            .filter { isPossible(it, operators) }
            .sumOf { it.target }
            .toString()
    }

    override fun part2(input: List<String>): String {
        val operators = listOf<(a: Long, b: Long) -> Long>(
            { a, b -> a + b },
            { a, b -> a * b },
            { a, b -> "$a$b".toLong() }
        )
        return parseInput(input)
            .filter { isPossible(it, operators) }
            .sumOf { it.target }
            .toString()
    }

    private fun isPossible(input: Input, operators: List<(a: Long, b: Long) -> Long>): Boolean {
        var value = input.numbers.first()
        return isPossibleRec(input, value, 1, operators)
    }

    private fun isPossibleRec(
        input: Input,
        value: Long,
        index: Int,
        operators: List<(a: Long, b: Long) -> Long>
    ): Boolean {
        if (index == input.numbers.size) {
            return value == input.target
        }
        val number = input.numbers[index]
        return operators.any { isPossibleRec(input, it(value, number), index + 1, operators) }
    }

    private fun parseInput(input: List<String>): List<Input> {
        return input.map(::parseLine)
    }

    private fun parseLine(line: String): Input {
        val (target, numbers) = line.split(": ")
        return Input(
            target.toLong(),
            numbers.split(" ").map(String::toLong)
        )
    }



    private data class Input(val target: Long, val numbers: List<Long>)
}