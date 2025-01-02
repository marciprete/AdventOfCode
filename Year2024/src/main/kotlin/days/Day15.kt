package days

import data.Coords
import data.Matrix
import data.Move
import it.senape.aoc.utils.Day
import kotlin.collections.ArrayList


class Day15 : Day(2024, 15) {
    override fun part1(input: List<String>): Any {
        val process = processInput(input)
        val map = process.first
        val instructions = process.second
        val matrix = parse(map)
        push(matrix, instructions)
        return matrix.findAll('O').sumOf { c -> c.y * 100 + c.x }
    }

    private fun push(matrix: Matrix<Char>, instructions: List<String>) {
        var robot = matrix.findFirst('@')!!
        val directions = instructions.fold("") { acc, s -> acc + s }
        directions.forEach { char ->
            var next = robot.moveToCharAndGetPosition(char, false)
            if(matrix.getAt(next) == '.') {
                robot = swap(robot, next, matrix)
            } else {
                val direction = Move.fromChar(char)
                val freeSpace = getNextAvailable(next, direction, matrix)
                if (freeSpace != null) {
                    matrix.putAt(freeSpace, 'O')
                    robot = swap(robot, next, matrix)
                }
            }
        }
    }

    private fun processInput(input: List<String>): Pair<List<String>, List<String>> {
        val map = mutableListOf<String>()
        val instructions = mutableListOf<String>()

        var addingToMap = true
        for (row in input) {
            when {
                row.isEmpty() -> addingToMap = false
                addingToMap -> map.add(row)
                else -> instructions.add(row)
            }
        }

        return map to instructions
    }

    private fun swap(robot: Coords, next: Coords, matrix: Matrix<Char>): Coords {
        matrix.putAt(next, '@')
        matrix.putAt(robot, '.')
        return next
    }

    private fun parse(input: List<String>): Matrix<Char> {
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { x, char ->
                matrix[y].add(char)
            }
        }
        return matrix
    }

    override fun part2(input: List<String>): Any {
        val process = processInput(input)
        val map = process.first
        val instructions = process.second
        val matrix = parse(map)
        val expanded = expand(matrix)
        expanded.forEach(::println)
        push(expanded, instructions)
        expanded.forEach(::println)
        return 0
    }

    private fun expand(matrix: Matrix<Char>): Matrix<Char> {
        val expanded = Matrix<Char>()
        matrix.forEach { column ->
            val line = ArrayList<Char>()
            expanded.add(line)
            column.forEach { char ->
                when (char) {
                    '#', '.' -> {
                        line.add(char)
                        line.add(char)
                    }
                    'O' -> {
                        line.add('[')
                        line.add(']')
                    }
                    '@' -> {
                        line.add('@')
                        line.add('.')
                    }
                }
            }
        }
        return expanded
    }

    private fun getNextAvailable(from: Coords, direction: Move, matrix: Matrix<Char>) : Coords? {
        val boxes = listOf('O', '[', ']')
        var next = from
        var found = true
        do {
            val actual = matrix.getAt(next)
            if (actual != null && actual == '#') {
                found = false
                break
            }
            next = next.move(direction.to)
        } while(boxes.contains(matrix.getAt(next)) || matrix.getAt(next) == '#')
        return if (found) next else null
    }

}
//2028
fun main() = Day.solve(Day15(), 10092, 9021)