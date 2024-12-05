import graph.Graph

class Day05 : Day {
    override fun part1(input: List<String>): String {
        val graph = Graph<Int>()
        var isChecking = false
        var total = 0
        for (line in input) {
            if (line.isEmpty()) {
                isChecking = true
                continue
            }
            if (!isChecking) {
                val (before, after) = line.split("|").map { it.trim().toInt() }
                graph.addEdge(before, after)
            } else {
                val numbers = line.split(",").map { it.trim().toInt() }
                val subgraph = graph.subgraph(numbers)
                val sortIndex = subgraph
                    .topologicalSort()
                    .withIndex()
                    .associate { it.value.value to it.index }
                if (isSorted(sortIndex, numbers)) {
                    total += numbers.middle()
                }
            }
        }
        return total.toString()
    }

    fun isSorted(sortIndex: Map<Int, Int>, numbers: List<Int>): Boolean {
        var prevIndex = -1
        numbers.forEach {
            val index = sortIndex[it]!!
            when {
                index < prevIndex -> return false
                else -> prevIndex = index
            }
        }
        return true
    }

    override fun part2(input: List<String>): String {
        var isChecking = false
        var total = 0
        val graph = Graph<Int>()
        input.forEach { line ->
            if (line.isEmpty()) {
                isChecking = true
                return@forEach
            }
            if (!isChecking) {
                val (before, after) = line.split("|").map { it.trim().toInt() }
                graph.addEdge(before, after)
            } else {
                val numbers = line.split(",").map { it.trim().toInt() }
                val subgraph = graph.subgraph(numbers)
                val sortIndex = subgraph.topologicalSort().withIndex().associate { it.value.value to it.index }
                if (!isSorted(sortIndex, numbers)) {
                    total += numbers.sortedBy { sortIndex[it] }.middle()
                }
            }
        }
        return total.toString()
    }
}