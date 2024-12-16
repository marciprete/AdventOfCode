package days

import data.*
import it.senape.aoc.utils.Day
import java.util.*


class Day16 : Day(2024, 16) {
    val wall = '#'
    val space = '.'
    override fun part1(input: List<String>): Any {
        val matrix = Matrix.buildCharMatrix(input)
        val direction = Move.RIGHT
        val startPosition = matrix.findFirst('S')!!


        val visited = mutableSetOf<Coords>()
        var queue: Queue<Coords> = LinkedList()
        queue.add(startPosition)
        visited.add(startPosition)
        while (queue.isNotEmpty()) {
            val pos = queue.poll()
            for (step in matrix.getSiblings(pos)) {
                if (!visited.contains(step)) {
                    visited.add(step)
                    queue.add(step)

                }
            }
        }








//        val spaces = matrix.findAll('.')
//        val end = matrix.findFirst('E')!!
//
//        var next = startPosition
//        val root = Vertex(0, startPosition)


        val graph = AdjacencyListGraph<Coords>()
        matrix.findAll('.').forEach { position ->
            if(isNode(position, matrix)) {
                graph.createVertex(position)
            }
        }
        val nodes = graph.getVertices().sortedBy { it.data }
        println(startPosition.getRelativeDirection(startPosition.move(Move.RIGHT.to)))

        printSiblings(startPosition, matrix)


        return 0
    }

    fun printSiblings(next: Coords, matrix: Matrix<Char>) {
        val siblings = matrix.getSiblings(next)
        if (siblings.isNotEmpty()) {
            siblings.forEach { sibling ->
                if(matrix.getAt(sibling) != wall) {
                    print("-> ${printSiblings(sibling, matrix)}")
                }
            }
        } else {
            println("")
        }
    }

    fun isNode(coords: Coords, matrix: Matrix<Char>): Boolean {
        val up = matrix.getAt(coords.move(Move.UP.to)).takeIf { it != wall }
        val right = matrix.getAt(coords.move(Move.RIGHT.to)).takeIf { it != wall }
        val bottom = matrix.getAt(coords.move(Move.DOWN.to)).takeIf { it != wall }
        val left = matrix.getAt(coords.move(Move.LEFT.to)).takeIf { it != wall }
        return (up != null && right != null) ||
                (right != null && bottom != null) ||
                (bottom != null && left != null) ||
                (left != null && up != null)
    }




    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() = Day.solve(Day16(), 7036, 0)