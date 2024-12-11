package days

import data.Coords
import data.Matrix
import data.Move
import data.MultiNode
import it.senape.aoc.utils.Day


class Day10 : Day(2024, 10) {
    override fun part1(input: List<String>): Any {
        val matrix = buildFromInput(input)
        val zeros = matrix.findAll(0)
        val map = zeros.map { current ->
            val graph = MultiNode(current)
            traverse(matrix, current, graph)
            graph.getAllLeafNodes().count { matrix.getAt(it.key) == 9 }
        }
        return map.sum()
    }

    private fun traverse(matrix: Matrix<Int>, current: Coords, node: MultiNode<Coords>) {
        for (direction in Move.straightValues()) {
            val next = current.move(direction.to)
            val nextValue = matrix.getAt(next)
            val currentValue = matrix.getAt(current)
            if(nextValue != null && (nextValue!! - currentValue!!) == 1) {
                val nextNode = MultiNode(next)
                traverse(matrix, next, nextNode)
                node.add(nextNode)
            }
        }
    }

    private fun buildFromInput(input: List<String>): Matrix<Int> {
        val matrix = Matrix<Int>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { _, char ->
                matrix[y].add(char.digitToInt())
            }
        }
        return matrix
    }

    override fun part2(input: List<String>): Any {
        val matrix = buildFromInput(input)
        val zeros = matrix.findAll(0)
        val map = zeros.map { current ->
            val graph = MultiNode(current)
            traverse(matrix, current, graph)
            graph.extractPaths().count { matrix.getAt(it.last()) == 9 }
        }
        return map.sum()
    }
}

fun main() = Day.solve(Day10(), 36, 81)