import java.io.File
import java.text.DecimalFormat
import kotlin.math.roundToLong
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    if(args.size != 1) {
        runLatest()
    } else if(args.first() == "all") {
        runAll()
    } else {
        run(args.first().toInt())
    }
}

fun run(day: Int) {
    println("\n======== Day $day ========")

    val daySolution = solutions[day]

    if (daySolution == null) {
        println("Day $day is not implemented yet.")
        return
    }

    val input = File("src/main/resources/day${day.toString().padStart(2, '0')}.txt").readLines()
    val expectedResult = expectedResults[day]

    val (solutionPart1, part1Time) = timeIt { daySolution.part1(input) }
    val (solutionPart2, part2Time) = timeIt { daySolution.part2(input) }

    if (expectedResult == null) {
        println("Could not find any expected results")

        println("Part 1: $solutionPart1 - ${part1Time}ms")
        println("Part 2: $solutionPart2 - ${part2Time}ms")
    } else {
        val part1AsExpected = solutionPart1 == expectedResult.first
        val part2AsExpected = solutionPart2 == expectedResult.second

        if (part1AsExpected && part2AsExpected) {
            println("✅ OK! Both parts are working as expected")
            println("Part 1 took $part1Time")
            println("Part 2 took $part2Time")
            return
        }

        println("❌ Not OK...")

        if (part1AsExpected) {
            println("Part 1 is OK")
            println("Part 1 took $part1Time")
        } else {
            println("\nPart 1 is wrong")
            println("Expected results: \t${expectedResult.first}")
            println("Actual results: \t$solutionPart1")
            println("Part 1 took $part1Time")
        }

        if (part2AsExpected) {
            println("Part 2 is OK")
            println("Part 2 took $part2Time")
        } else {
            println("\nPart 2 is wrong")
            println("Expected results: \t${expectedResult.second}")
            println("Actual results: \t$solutionPart2")
            println("Part 2 took $part2Time")
        }
    }

}

/**
 * Returns the time it took to run the function as a formatted string.
 * If the time is less than 1 second, it will do a more scientific timing, where we run it several times,
 * to account for JIT compilation, and other performance impacting factors.
 */
private fun <T> timeIt(f: () -> T) : Pair<T, String> {
    var res: T
    val initialTotalTime = measureNanoTime { res = f() }

    // Longer than 2sec
    if(initialTotalTime > 2e9) {
        return res to "${formatTime(initialTotalTime)} (1 sample)"
    }

    val samples = (2e9 / initialTotalTime).roundToLong()

    val times = (0..samples).map { measureNanoTime { f() } }
    val averageTime = times.average().roundToLong()
    val deltaTime = averageTime - times.min()
    return res to "${formatTime(averageTime)} +- ${formatTime(deltaTime)} (${times.size} samples)"
}

private fun formatTime(timeNs: Long) : String {
    val df = DecimalFormat("#.##")
    return if(timeNs >= 1e9) {
        "${df.format(timeNs.toDouble() / 1e9)}s"
    } else if(timeNs >= 1e5) {
        "${df.format(timeNs.toDouble() / 1e6)}ms"
    } else if(timeNs >= 1e2) {
        "${df.format(timeNs.toDouble() / 1e3)}μs"
    } else {
        "${timeNs}ns"
    }
}


fun runLatest() {
    solutions.maxByOrNull { it.key }?.let {
        run(it.key)
    }
}

fun runAll() {
    solutions.forEach {
        run(it.key)
    }
}

val solutions = mapOf(
    1 to Day01(),
    2 to Day02(),
    3 to Day03(),
    4 to Day04(),
    5 to Day05(),
    6 to Day06(),
    7 to Day07(),
    8 to Day08(),
    9 to Day09(),
    10 to Day10(),
    11 to Day11(),
    12 to Day12(),
    13 to Day13(),
    14 to Day14(),
    15 to Day15(),
//    16 to Day16(),
//    17 to Day17(),
//    18 to Day18(),
//    19 to Day19(),
//    20 to Day20(),
//    21 to Day21(),
//    22 to Day22(),
//    23 to Day23(),
//    24 to Day24(),
//    25 to Day25(),
)

val expectedResults = mapOf(
    1 to Pair("1722302", "20373490"),
    2 to Pair("421", "476"),
    3 to Pair("187194524", "127092535"),
    4 to Pair("2591", "1880"),
    5 to Pair("5747", "5502"),
    6 to Pair("5208", "1972"),
    7 to Pair("1038838357795", "254136560217241"),
    8 to Pair("261", "898"),
    9 to Pair("6310675819476", "6335972980679"),
    10 to Pair("548", "1252"),
    11 to Pair("224529", "266820198587914"),
    12 to Pair("1473276", "236"),
    13 to Pair("27105", "101726882250942"),
    14 to Pair("12", "7774"),
    15 to Pair("1492518", "0"),
//    16 to Pair("0", "0"),
//    17 to Pair("0", "0"),
//    18 to Pair("0", "0"),
//    19 to Pair("0", "0"),
//    20 to Pair("0", "0"),
//    21 to Pair("0", "0"),
//    22 to Pair("0", "0"),
//    23 to Pair("0", "0"),
//    24 to Pair("0", "0"),
//    25 to Pair("0", "0"),
)