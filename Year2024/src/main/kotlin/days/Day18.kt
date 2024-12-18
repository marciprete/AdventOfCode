package days

import data.Coords
import data.Maze
import it.senape.aoc.utils.Day


class Day18 : Day(2024, 18) {
    val wall = '#'

    override fun part1(input: List<String>): Any {
        val computer = init(input)
        val startingNode = Coords(0, 0)
        val matrix = computer.maze
        val exit = Coords(matrix.lastIndex, matrix[0].lastIndex)
        val len = matrix.breadthFirst(startingNode)[exit]!!.toInt()
        return len
    }

    override fun part2(input: List<String>): Any {
        val computer = init(input)
        val startingCoords = Coords(0, 0)
        val bytes = computer.bytes
        val matrix = computer.maze
        val exit = Coords(matrix.lastIndex, matrix[0].lastIndex)
        var cuttingEdge = startingCoords
        for( i in matrix.findAll(wall).size until bytes.size) {
            val newByte = bytes[i]
            matrix.putAt(newByte, wall)
            val distance = matrix.breadthFirst(startingCoords)[exit]
            if (distance == null || distance == Double.POSITIVE_INFINITY) {
                cuttingEdge = newByte
                break
            }
        }
        println(cuttingEdge)
        return "${cuttingEdge.x},${cuttingEdge.y}"
    }


    data class Computer(val bytes: List<Coords>, val maze: Maze) {}
    fun init(input: List<String>) : Computer {
        val bytes = input.map { line ->
            val coords = line.split(",")
            Coords(coords[0].toInt(), coords[1].toInt())
        }.toList()
        var size = 7
        var count = 12
        if (bytes.size > 1024) {
            size = 71
            count = 1024
        }
        val matrix = Maze(size)
        for (y in 0 until count) {
            matrix.putAt(bytes[y], '#')
        }
        return Computer(bytes, matrix)
    }
}

fun main() = Day.solve(Day18(), 22, "6,1")