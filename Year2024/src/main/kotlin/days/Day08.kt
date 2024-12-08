package days

import data.Coords
import data.ElementAt
import data.Matrix
import data.Move
import it.senape.aoc.utils.Day


class Day08 : Day(2024, 8) {
    override fun part1(input: List<String>): Any {
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { _, char ->
                matrix[y].add(char)
            }
        }
        val antennas = matrix.findAllDifferent('.')
        val antinodes = mutableSetOf<Coords>()
        antennas.forEach { antenna ->
            antinodes.addAll(placeAntinodes(antenna, matrix))
        }

        println(antinodes)
        println(antinodes.size)
        return 0
    }

    private fun placeAntinodes(antenna: ElementAt<Char>, matrix: Matrix<Char>): Set<Coords> {
        var antinodes = mutableSetOf<Coords>()
        for (move in Move.values()) {
            var steps = 0
            var next = antenna.second
            do {
                next = next.move(move.to)
                steps++
                if (matrix.getAt(next) == antenna.first) {
                    for (i in 0 until steps) {
                        next = next.move(move.to)
                    }
                    if(matrix.exists(next)) {
//                        matrix.replaceAt(next, '#')
                        antinodes.add(next)
                    }

                }
            } while (matrix.exists(next))
        }
        return antinodes
    }

    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() = Day.solve(Day08(), 14, 0)