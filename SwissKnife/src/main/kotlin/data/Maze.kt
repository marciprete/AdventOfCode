package data

import java.util.*
import kotlin.math.min


class Maze(size: Int) : Matrix<Char>() {

    companion object {
        const val WALL = '#'
        const val SPACE = '.'
    }

    init {
        for (y in 0 until size) {
            this.add(ArrayList())
            for (x in 0 until size) {
                this[y].add('.')
            }
        }
    }

    constructor(input: List<String>) : this(input.size) {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                this[y][x] = char
            }
        }
    }


    fun breadthFirst(root: Coords): Map<Coords, Double> {
        var queue: Queue<Coords> = LinkedList()
        var distances: MutableMap<Coords, Pair<Coords?, Double>> = mutableMapOf()

        this.findAll(SPACE).forEach { vertex ->
            distances[vertex] = Pair(null, Double.POSITIVE_INFINITY)
        }
        this.findFirst('E')?.let {
            distances[it] = Pair(null, Double.POSITIVE_INFINITY)
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

    fun dijkstra(from: Coords, to: Coords): Double {
        var distances: MutableMap<Coords, Pair<Coords?, Double>> = mutableMapOf()
        var queue: Queue<Coords> = LinkedList()

        findAll(SPACE).forEach { vertex ->
            //inizializzo tutte le distanze a infinito
            distances[vertex] = Pair(null, Double.POSITIVE_INFINITY)
            //aggiungo alla coda di esecuzione
        }
        distances[to] = Pair(null, Double.POSITIVE_INFINITY)
        queue.add(from)

        //prendo l'origine e setto la distanza a 0
        var current = from
        distances[current] = Pair(null, 0.0)
        var visited: MutableSet<Coords> = mutableSetOf()

        while (queue.isNotEmpty()) {
            current = queue.poll()
            visited.add(current)
            //per ogni percorso dal corrente calcolo la distanza minore
            getSiblings(current).forEach { edge ->
                //verifico che non sia stato ancora attraversato
                if (!visited.contains(edge)) {
                    var distance = distances[current]!!.second + distances[edge]!!.second
                    if (distance < distances[edge]!!.second) {
                        distances[edge] = Pair(current, distance)
                    }
                    queue.add(edge)
                }
            }
        }
        var node = to
        while (node != from) {
            node = distances[node]?.first!!
        }

        return distances[to]!!.second
    }

    override fun getSiblings(coords: Coords): List<Coords> {
        return super.getSiblings(coords).filter { this.getAt(it) != WALL }
    }
}