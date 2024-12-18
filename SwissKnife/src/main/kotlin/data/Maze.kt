package data

import java.util.*


class Maze(size: Int) : Matrix<Char>() {

    val wall = '#'

    init {
        for (y in 0 until size) {
            this.add(ArrayList())
            for (x in 0 until size) {
                this[y].add('.')
            }
        }
    }


    fun breadthFirst(root: Coords): Map<Coords, Double> {
        var queue: Queue<Coords> = LinkedList()
        var distances: MutableMap<Coords, Pair<Coords?, Double>> = mutableMapOf()

        this.findAll('.').forEach { vertex ->
            distances[vertex] = Pair(null, Double.POSITIVE_INFINITY)
        }

        distances[root] = Pair(root, 0.0)
        queue.add(root)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            this.getSiblings(current).forEach { edge ->
                if (distances[edge]?.second == Double.POSITIVE_INFINITY) {
                    distances[edge] = Pair(current, distances[current]!!.second + 1)
                    queue.add(edge)
                }
            }
        }
        return distances.map { (edge, p) -> edge to p.second }.toMap()
    }

    override fun getSiblings(coords: Coords): List<Coords> {
        return super.getSiblings(coords).filter { this.getAt(it) != wall }
    }
}