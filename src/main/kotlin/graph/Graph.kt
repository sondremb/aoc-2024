package graph

class Graph<T> {
    private val nodes = mutableMapOf<T, Node<T>>()
    private val edges = mutableListOf<Edge<T>>()

    fun hasNode(value: T): Boolean {
        return nodes.containsKey(value)
    }

    fun subgraph(fromNodes: List<T>): Graph<T> {
        val subGraph = Graph<T>()
        fromNodes.forEach {
            subGraph.addNode(it)
            val node = nodes[it] ?: return@forEach
            node.edges.forEach { edge ->
                if (fromNodes.contains(edge.to.value)) {
                    subGraph.addEdge(edge.from.value, edge.to.value)
                }
            }
        }
        return subGraph
    }

    fun getOrAddNode(value: T): Node<T> {
        return nodes.getOrPut(value) { Node(value) }
    }

    fun addNode(value: T) {
        nodes.putIfAbsent(value, Node(value))
    }

    fun addEdge(from: T, to: T) {
        val fromNode = getOrAddNode(from)
        val toNode = getOrAddNode(to)
        val edge = Edge(fromNode, toNode)

        fromNode.edges.add(edge)
        edges.add(edge)
    }

    fun getNodes(): List<Node<T>> {
        return nodes.values.toList()
    }

    fun topologicalSort(): List<Node<T>> {
        val result = mutableListOf<Node<T>>()
        val unvisited = nodes.values.toMutableSet()

        fun visit(node: Node<T>) {
            if (!unvisited.contains(node)) {
                return
            }

            node.edges.forEach { edge ->
                visit(edge.to)
            }

            result.add(node)
            unvisited.remove(node)
        }

        while (unvisited.isNotEmpty()) {
            visit(unvisited.first())
        }

        return result.reversed()
    }
}

data class Node<T>(val value: T) {
    val edges = mutableListOf<Edge<T>>()
}

data class Edge<T>(val from: Node<T>, val to: Node<T>, val weight: Int = 1);