import graph.Coordinate

class Utils {
    companion object {
        val WhiteSpace = Regex("\\s+")
    }
}

fun List<Int>.middle(): Int = this[this.size / 2]

fun List<Int>.isSortedBy(sortFunction: (Int) -> Int): Boolean = this
    .zipWithNext()
    .all { (a, b) -> sortFunction(a) < sortFunction(b) }


operator fun <T>List<List<T>>.get(coord: Coordinate): T = this[coord.r][coord.c]
operator fun <T>List<MutableList<T>>.set(coord: Coordinate, value: T) {
    this[coord.r][coord.c] = value
}
fun <T>List<List<T>>.has(Coord: Coordinate): Boolean = Coord.r in this.indices && Coord.c in this[Coord.r].indices