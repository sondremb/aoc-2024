package graph

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Origin = Coordinate(0, 0)
        val Up = Coordinate(0, -1)
        val Down = Coordinate(0, 1)
        val Left = Coordinate(-1, 0)
        val Right = Coordinate(1, 0)
    }

    val r = y
    val c = x

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
    operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Coordinate(x * scalar, y * scalar)
    operator fun div(scalar: Int) = Coordinate(x / scalar, y / scalar)
    val neighbors get() = listOf(Up, Down, Left, Right).map { this + it }
    fun turnRight() = Coordinate(-y, x)
    fun turnLeft() = Coordinate(y, -x)
}

data class LCoordinate(val x: Long, val y: Long) {
    companion object {
        val Origin = LCoordinate(0, 0)
        val Up = LCoordinate(0, -1)
        val Down = LCoordinate(0, 1)
        val Left = LCoordinate(-1, 0)
        val Right = LCoordinate(1, 0)
    }

    val r = y
    val c = x

    operator fun plus(other: LCoordinate) = LCoordinate(x + other.x, y + other.y)
    operator fun plus(scalar: Long) = LCoordinate(x + scalar, y + scalar)
    operator fun minus(other: LCoordinate) = LCoordinate(x - other.x, y - other.y)
    operator fun times(scalar: Int) = LCoordinate(x * scalar, y * scalar)
    operator fun times(scalar: Long) = LCoordinate(x * scalar, y * scalar)
    operator fun div(scalar: Int) = LCoordinate(x / scalar, y / scalar)
    val neighbors get() = listOf(Up, Down, Left, Right).map { this + it }
    fun turnRight() = LCoordinate(-y, x)
    fun turnLeft() = LCoordinate(y, -x)
}