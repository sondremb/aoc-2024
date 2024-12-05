import graph.Graph

class Day05 : Day {
    override fun part1(input: List<String>): String {
        val (graph, updates) = parseInput(input)
        var total = 0
        updates.forEach { pages ->
            val subgraph = graph.subgraph(pages)
            val sortIndex = subgraph
                .topologicalSort()
                .withIndex()
                .associate { it.value.value to it.index }
            if (pages.isSortedBy { sortIndex[it]!! }) {
                total += pages.middle()
            }
        }
        return total.toString()
    }

    fun parseInput(input: List<String>): Pair<Graph<Int>, List<List<Int>>> {
        val graph = Graph<Int>()
        val updates = mutableListOf<List<Int>>()
        var isChecking = false
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
                updates.add(numbers)
            }
        }
        return Pair(graph, updates)
    }

    override fun part2(input: List<String>): String {
        val (graph, updates) = parseInput(input)
        var total = 0
        updates.forEach { pages ->
            val subgraph = graph.subgraph(pages)
            val sortIndex = subgraph
                .topologicalSort()
                .withIndex()
                .associate { it.value.value to it.index }
            if (!pages.isSortedBy { sortIndex[it]!! }) {
                total += pages.sortedBy { sortIndex[it] }.middle()
            }
        }
        return total.toString()
    }
}