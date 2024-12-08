package data

typealias ElementAt<T> = Pair<T, Coords>

class Matrix<T> : ArrayList<ArrayList<T>>() {

    fun exists(coords: Coords): Boolean {
        return coords.x >= 0 && coords.y >= 0 && coords.x < this.size && coords.y < this[0].size
    }

    fun getAt(coords: Coords): T? {
        if(exists(coords)) {
            return this[coords.y][coords.x]
        }
        return null
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
    DOWN_RIGHT(Coords(1, 1)),
}