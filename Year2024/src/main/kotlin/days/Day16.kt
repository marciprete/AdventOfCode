package days

import data.*
import data.Maze.Companion.SPACE
import it.senape.aoc.utils.Day
import java.util.*

typealias WeightedEdge = Pair<Coords?, Double>

class Day16 : Day(2024, 16) {
    override fun part1(input: List<String>): Any {
        val maze = Maze(input)
        val startPosition = maze.findFirst('S')!!
        val endPosition = maze.findFirst('E')!!
        //731520 too high
        val dijkstra = dijkstra(maze, startPosition, endPosition)
        println(dijkstra)
        return dijkstra.toInt()
    }


    fun dijkstra(maze: Maze, start: Coords, end: Coords): Double {
        var distances: MutableMap<Coords, WeightedEdge> = mutableMapOf()
        var queue: LinkedList<Coords> = LinkedList()

        maze.findAll(SPACE).forEach { vertex ->
            //inizializzo tutte le distanze a infinito
            distances[vertex] = WeightedEdge(null, Double.POSITIVE_INFINITY)
            //aggiungo alla coda di esecuzione
        }
        distances[end] = WeightedEdge(null, Double.POSITIVE_INFINITY)
        queue.add(start)

        //prendo l'origine e setto la distanza a 0
        var current = start
        distances[current] = Pair(null, 0.0)
        var visited: MutableSet<Coords> = mutableSetOf()

        while (queue.isNotEmpty()) {
            current = queue.poll()
            visited.add(current)
            //per ogni percorso dal corrente calcolo la distanza minore
            val sortedSiblings = maze.getFilteredSiblings(current).sortedBy { getWeight(distances[current]!!.first, current, it) }
            sortedSiblings.forEach { sibling ->
                //verifico che non sia stato ancora attraversato
                if (!visited.contains(sibling)) {

                    var distance = distances[current]!!.second + Math.min(getWeight(distances[current]!!.first, current, sibling),
                        distances[sibling]!!.second)
                    if (distance < distances[sibling]!!.second) {
                        distances[sibling] = Pair(current, distance)
                    }
                    queue.push(sibling)
                }
            }
        }
        var node = end
        while (node != start) {
            node = distances[node]?.first!!
        }

        return distances[end]!!.second
    }



    private fun getWeight(prev: Coords?, current: Coords, next: Coords): Double {
        val incoming = prev?.getRelativeDirectionTo(current) ?: Move.RIGHT
        val outcoming = current.getRelativeDirectionTo(next)
        var weight = 1.0
        if (incoming != outcoming) {
            weight = 1001.0
        }
//        println("$prev -> $current -> $next --- $incoming -> $outcoming = $weight")
        return weight
    }

    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() = Day.solve(Day16(), 7036, 0)