import graph.LCoordinate
import kotlin.math.round

class Day13 : Day {
    override fun part1(input: List<String>): String {
        val machines = parseInput(input)
        return machines.sumOf { solve(it) }.toString()
    }

    override fun part2(input: List<String>): String {
        val machines = parseInput(input)
        machines.forEach { it.p += 10000000000000L }
        return machines.sumOf { solve(it) }.toString()
    }

    fun solve(machine: Machine): Long {
        val b = machine.getB()
        val a = machine.getAFromB(b)
        if (machine.verify(a, b)) {
            return 3L * a + b
        }
        return 0L
    }


    private fun parseInput(input: List<String>): List<Machine> {
        val iterator = input.iterator()
        val machines = mutableListOf<Machine>()
        while (iterator.hasNext()) {
            val aLine = iterator.next()
            val ax = aLine.substringAfter("X+").substringBefore(", Y+").toLong()
            val ay = aLine.substringAfter("Y+").toLong()
            val bLine = iterator.next()
            val bx = bLine.substringAfter("X+").substringBefore(", Y+").toLong()
            val by = bLine.substringAfter("Y+").toLong()
            val prizeLine = iterator.next()
            val px = prizeLine.substringAfter("X=").substringBefore(", Y=").toLong()
            val py = prizeLine.substringAfter("Y=").toLong()
            machines.add(Machine(LCoordinate(ax, ay), LCoordinate(bx, by), LCoordinate(px, py)))
            if (iterator.hasNext()) iterator.next()
        }
        return machines
    }


    data class Machine(var a: LCoordinate, var b: LCoordinate, var p: LCoordinate) {
        fun getAFromB(bVal: Long): Long = ((p.x - bVal * b.x) / a.x)

        fun getB() =
            round((p.y.toDouble() - p.x.toDouble() * a.y.toDouble() / a.x.toDouble()) / (b.y.toDouble() - b.x.toDouble() * a.y.toDouble() / a.x.toDouble())).toLong()

        fun value(aValue: Long, bValue: Long): LCoordinate = a * aValue + b * bValue
        fun verify(aValue: Long, bValue: Long): Boolean = value(aValue, bValue) == p
    }
}