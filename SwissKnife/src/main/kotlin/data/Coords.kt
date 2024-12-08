package data

/**
 *
 * @author Michele Arciprete
 * created on 05/01/2024
 */
class Coords(val x: Int, val y: Int) {

    companion object {
        fun fromString(str: String): Coords {
            if (str.matches("[0-9]+,[0-9]+".toRegex())) {
                val split = str.split(",")
                return Coords(split[0].toInt(), split[1].toInt());
            }
            throw RuntimeException("Not a coordinate")
        }
    }

    /**
     * Get the direction from the char and returns a Coords with the new position from this
     */
    fun moveToCharAndGetPosition(char: Char): Coords {
        return when (char) {
            '^' -> Coords(this.x, this.y + 1)
            'v' -> Coords(this.x, this.y - 1)
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

