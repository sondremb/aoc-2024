class Utils {
    companion object {
        val WhiteSpace = Regex("\\s+")
    }
}

fun List<Int>.middle(): Int = this[this.size / 2]