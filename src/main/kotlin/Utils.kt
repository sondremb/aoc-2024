class Utils {
    companion object {
        val WhiteSpace = Regex("\\s+")
    }
}

fun List<Int>.middle(): Int = this[this.size / 2]

fun List<Int>.isSortedBy(sortFunction: (Int) -> Int): Boolean = this
    .zipWithNext()
    .all { (a, b) -> sortFunction(a) < sortFunction(b) }