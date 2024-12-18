package data

typealias ElementAt<T> = Pair<T, Coords>

open class Matrix<T> : ArrayList<ArrayList<T>>() {

    companion object {
        fun buildCharMatrix(input: List<String>): Matrix<Char> {
            val matrix = Matrix<Char>()
            input.forEachIndexed { y, line ->
                matrix.add(ArrayList())
                line.forEachIndexed { _, char ->
                    matrix[y].add(char)
                }
            }
            return matrix
        }

        fun buildEmptyMatrix(size: Int): Matrix<Char> {
            val matrix = Matrix<Char>()
            for (y in 0 until size) {
                matrix.add(ArrayList())
                for (x in 0 until size) {
                    matrix[y].add('.')
                }
            }
            return matrix
        }
    }

    fun exists(coords: Coords): Boolean {
        return coords.x >= 0 && coords.y >= 0 && coords.x < this.size && coords.y < this[0].size
    }

    fun getAt(coords: Coords): T? {
        if(exists(coords)) {
            return this[coords.y][coords.x]
        }
        return null
    }

    fun putAt(coords: Coords, element: T) {
        if(exists(coords)) {
            this[coords.y][coords.x] = element
        }
    }

    fun findFirst(element: T) : Coords? {
        for(y in 0 until this.size) {
            for(x in 0 until this[y].size) {
                if(this[y][x] == element) {
                    return Coords(x, y)
                }
            }
        }
        return null
    }

    open fun getSiblings(start: Coords): List<Coords> {
        val siblings = mutableListOf<Coords>()
        Move.straightValues().forEach { direction ->
            val sibling = start.move(direction.to)
            this.getAt(sibling)?.let { siblings.add(sibling) }
        }
        return siblings
    }

    override fun clone(): Matrix<T> {
        val clonedMatrix = Matrix<T>()
        this.forEach { row ->
            clonedMatrix.add(ArrayList(row))
        }
        return clonedMatrix
    }

    fun replaceAt(coords: Coords, element: T) : Matrix<T> {
        val clone = clone()
        clone[coords.y][coords.x] = element
        return clone
    }

    fun findAll(element: T) : List<Coords> {
        val result = mutableListOf<Coords>()
        for(y in 0 until this.size) {
            for(x in 0 until this[y].size) {
                if(this[y][x] == element) {
                    result.add(Coords(x, y))
                }
            }
        }
        return result
    }

    fun findAllDifferent(element: T) : List<ElementAt<T>> {
        val result = mutableListOf<ElementAt<T>>()
        for(y in 0 until this.size) {
            for(x in 0 until this[y].size) {
                if(this[y][x] != element) {
                    result.add(ElementAt(this[y][x], Coords(x, y)))
                }
            }
        }
        return result
    }

    fun isAtTop(current: Coords, element: T): Boolean {
        return isAt(current, element, Move.UP)
    }
    fun isAtRight(current: Coords, element: T): Boolean {
        return isAt(current, element, Move.RIGHT)
    }
    fun isAtBottom(current: Coords, element: T): Boolean {
        return isAt(current, element, Move.DOWN)
    }
    fun isAtLeft(current: Coords, element: T): Boolean {
        return isAt(current, element, Move.LEFT)
    }

    fun isAt(current: Coords, element: T, move: Move): Boolean {
        val pair = when (move) {
            Move.UP -> Pair(current.move(Move.UP_RIGHT.to), current.move(Move.UP_LEFT.to))
            Move.DOWN -> Pair(current.move(Move.DOWN_RIGHT.to), current.move(Move.DOWN_LEFT.to))
            Move.LEFT -> Pair(current.move(Move.DOWN_LEFT.to), current.move(Move.UP_LEFT.to))
            Move.RIGHT -> Pair(current.move(Move.UP_RIGHT.to), current.move(Move.DOWN_RIGHT.to))
            else -> Pair(current, current)
        }
        return this.getAt(pair.first) == element && this.getAt(pair.second) == element
    }


}

enum class Move(val to: Coords) {
    UP(Coords(0, -1)),
    DOWN(Coords(0, 1)),
    LEFT(Coords(-1, 0)),
    RIGHT(Coords(1, 0)),
    UP_LEFT(Coords(-1, -1)),
    UP_RIGHT(Coords(1, -1)),
    DOWN_LEFT(Coords(-1, 1)),
    DOWN_RIGHT(Coords(1, 1)), ;

    companion object {
        fun straightValues(): List<Move> {
            return listOf(UP, DOWN, LEFT, RIGHT)
        }

        fun fromChar(c: Char): Move {
            return when (c) {
                '^' -> UP
                'v' -> DOWN
                '>' -> RIGHT
                '<' -> LEFT
                else -> error("Unexpected")
            }
        }
    }

    fun turnRight() : Move {
        return when(this) {
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
            UP -> RIGHT
            UP_RIGHT -> DOWN_RIGHT
            DOWN_RIGHT -> DOWN_LEFT
            DOWN_LEFT -> UP_LEFT
            UP_LEFT -> UP_RIGHT
        }
    }

    fun isVertical(): Boolean {
        return this == UP || this == DOWN
    }
}