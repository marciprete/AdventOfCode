package it.senape.aoc.day01

import data.Matrix
import data.Move
import data.Coords
import it.senape.aoc.utils.Day

class Day04 : Day(2024, 4) {
    val sequence = listOf('X', 'M', 'A', 'S')

    override fun part1(input: List<String>): Any {
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { x, char ->
                matrix[y].add(char)
            }
        }
        return parse(matrix)
    }

    override fun part2(input: List<String>): Any {
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { x, char ->
                matrix[y].add(char)
            }
        }
        return xMasSearch(matrix)
    }

    fun parse(matrix: Matrix<Char>): Int {
        var total = 0
        for (y in 0 until matrix.size) {
            for (x in 0 until matrix[y].size) {
                if (matrix[y][x] == 'X') {
                    total += search(matrix, Coords(x, y), 0)
                }
            }
        }
        return total
    }

    fun xMasSearch(matrix: Matrix<Char>): Int {
        var total = 0
        for (y in 0 until matrix.size) {
            for (x in 0 until matrix[y].size) {
                if (matrix[y][x] == 'A') {
                    //search for the M: both top, or both right
                    if (matrix.isAtTop(Coords(x, y), 'M') && matrix.isAtBottom(Coords(x, y), 'S')) {
                        total++
                    } else if (matrix.isAtTop(Coords(x, y), 'S') && matrix.isAtBottom(Coords(x, y), 'M')) {
                        total++
                    } else if (matrix.isAtLeft(Coords(x, y), 'S') && matrix.isAtRight(Coords(x, y), 'M')) {
                        total++
                    } else if (matrix.isAtLeft(Coords(x, y), 'M') && matrix.isAtRight(Coords(x, y), 'S')) {
                        total++
                    }
                }
            }
        }
        return total
    }

    fun search(matrix: Matrix<Char>, pos: Coords, nextChar: Int): Int {
        val nc = nextChar + 1
        var counted = 0
        Move.entries.forEach { move ->
            val next = pos.move(move.to)
            if (matrix.exists(next)) {
                if(matrix.getAt(next) == sequence[nc]) {
                    counted += keepSearching(matrix, next, move, nc+1)
                }
            }
        }
        return counted
    }

    fun keepSearching(matrix: Matrix<Char>, pos: Coords, move: Move, nc: Int) : Int {
        val letter = sequence[nc]
        val next = pos.move(move.to)
        if (matrix.exists(next)) {
            if(matrix.getAt(next) == letter) {
                if (letter == sequence.last()) {
                    return 1
                } else {
                    return keepSearching(matrix, next, move, nc + 1)
                }
            }
        }
        return 0
    }
}




fun main() = Day.solve(Day04(), 18, 9)