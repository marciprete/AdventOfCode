package data

enum class Direction(val xDelta: Int, val yDelta: Int) {

    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    companion object {
        fun initial(): Direction = UP
    }
}