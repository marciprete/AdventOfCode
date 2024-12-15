package data

/**
 *
 * @author Michele Arciprete
 * created on 05/01/2024
 */
class Coords(val x: Int, val y: Int) : Comparable<Coords> {

    companion object {
        fun fromString(str: String): Coords {
            if (str.matches("[0-9]+,[0-9]+".toRegex())) {
                val split = str.split(",")
                return Coords(split[0].toInt(), split[1].toInt())
            }
            throw RuntimeException("Not a coordinate")
        }
    }

    /**
     * Get the direction from the char and returns a Coords with the new position from this
     */
    fun moveToCharAndGetPosition(char: Char, euclidean: Boolean = true): Coords {
        val x = if (euclidean) 1 else -1
        return when (char) {
            '^' -> Coords(this.x, this.y + x)
            'v' -> Coords(this.x, this.y - x)
            '>' -> Coords(this.x + 1, this.y)
            '<' -> Coords(this.x - 1, this.y)
            else -> error("Unexpected")
        }
    }

    fun move(to: Coords): Coords {
        return Coords(x + to.x, y + to.y)
    }

    fun distance(to: Coords): Coords {
        return Coords(to.x - this.x, to.y - this.y)
    }

    fun teleport(speed: Coords, time: Int, edge: Coords): Coords {
        val toX = x + time * if (speed.x > 0) speed.x else edge.x + speed.x
        val toY = y + time * if (speed.y > 0) speed.y else edge.y + speed.y
        val x = if (toX < edge.x) toX else toX.rem(edge.x)
        val y = if (toY < edge.y) toY else toY.rem(edge.y)
        return Coords(x, y)
    }

    override fun compareTo(other: Coords): Int {
        return if (y != other.y) {
            y.compareTo(other.y)
        } else {
            x.compareTo(other.x)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coords

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

